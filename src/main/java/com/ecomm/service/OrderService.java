package com.ecomm.service;

import com.ecomm.exception.OrderException;
import com.ecomm.exception.UserException;
import com.ecomm.model.Address;
import com.ecomm.model.Orders;
import com.ecomm.model.User;

import java.util.List;

public interface OrderService{
    public Orders createOrder(User user, Address shippingAddress);

    public Orders findOrderById(Long orderId) throws OrderException;

    public List<Orders> usersOrderHistory(Long userId) throws UserException;

    public Orders placedOrder(Long orderId) throws OrderException;

    public Orders confirmedOrder(Long orderId) throws OrderException;

    public Orders shippedOrder(Long orderId) throws OrderException;

    public Orders deliveredOrder(Long orderId) throws OrderException;

    public Orders cancelledOrder(Long orderId) throws OrderException;

    public List<Orders> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
