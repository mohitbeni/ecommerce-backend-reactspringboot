package com.ecomm.service;

import com.ecomm.exception.OrderException;
import com.ecomm.model.*;
import com.ecomm.repository.AddressRepository;
import com.ecomm.repository.OrderItemRepository;
import com.ecomm.repository.OrderRepository;
import com.ecomm.repository.UserRepository;
import com.ecomm.user.domain.OrderStatus;
import com.ecomm.user.domain.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public Orders createOrder(User user, Address shippAddress) {

        shippAddress.setUser(user);
        Address address= addressRepository.save(shippAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart=cartService.findUserCart(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem item: cart.getCartItems()) {
            OrderItem orderItem=new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());


            OrderItem createdOrderItem=orderItemRepository.save(orderItem);

            orderItems.add(createdOrderItem);
        }


        Orders createdOrder=new Orders();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItems(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Orders savedOrder=orderRepository.save(createdOrder);

        for(OrderItem item:orderItems) {
            item.setOrders(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;

    }

    @Override
    public Orders placedOrder(Long orderId) throws OrderException {
        Orders order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Orders confirmedOrder(Long orderId) throws OrderException {
        Orders order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);


        return orderRepository.save(order);
    }

    @Override
    public Orders shippedOrder(Long orderId) throws OrderException {
        Orders order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Orders deliveredOrder(Long orderId) throws OrderException {
        Orders order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Orders cancelledOrder(Long orderId) throws OrderException {
        Orders order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Orders findOrderById(Long orderId) throws OrderException {
        Optional<Orders> opt=orderRepository.findById(orderId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new OrderException("order not exist with id "+orderId);
    }

    @Override
    public List<Orders> usersOrderHistory(Long userId) {
        List<Orders> orders=orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public List<Orders> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Orders order =findOrderById(orderId);

        orderRepository.deleteById(orderId);

    }

}
