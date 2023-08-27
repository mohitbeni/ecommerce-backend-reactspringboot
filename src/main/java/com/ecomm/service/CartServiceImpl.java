package com.ecomm.service;

import com.ecomm.exception.ProductException;
import com.ecomm.model.Cart;
import com.ecomm.model.CartItem;
import com.ecomm.model.Product;
import com.ecomm.model.User;
import com.ecomm.repository.CartRepository;
import com.ecomm.request.AddItemRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;

    public CartServiceImpl(){}


    @Override
    public Cart createCart(User user) {
        Cart cart= new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemService.isCartItemExist(cart,product, req.getSize(), userId);

        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity()*product.getPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

//            cart.getCartItems().add(cartItem);

//            cart = cartRepository.save(cart);
            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        for(CartItem cartItem : cart.getCartItems()){
            totalPrice+=cartItem.getPrice();
            totalDiscountedPrice+= cartItem.getDiscountedPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
