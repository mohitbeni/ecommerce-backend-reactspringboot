package com.ecomm.controller;

import com.ecomm.exception.OrderException;
import com.ecomm.exception.UserException;
import com.ecomm.model.Address;
import com.ecomm.model.Orders;
import com.ecomm.model.User;
import com.ecomm.service.OrderService;
import com.ecomm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService,UserService userService) {
        this.orderService=orderService;
        this.userService=userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrderHandler(@RequestBody Address spippingAddress,
                                                     @RequestHeader("Authorization")String jwt) throws UserException {

        User user=userService.findUserProfileByJwt(jwt);
        Orders order =orderService.createOrder(user, spippingAddress);

        return new ResponseEntity<Orders>(order,HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity< List<Orders>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        List<Orders> orders=orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Orders> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        Orders orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

}
