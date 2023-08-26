package com.ecomm.controller;

import com.ecomm.exception.OrderException;
import com.ecomm.model.Orders;
import com.ecomm.response.ApiResponse;
import com.ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Orders>> getAllOrdersHandler(){
        List<Orders> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Orders> confirmedOrderHandler(@PathVariable Long orderId,
                                                        @RequestHeader("Authorization") String jwt) throws OrderException{
        Orders order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Orders> shipOrderHandler(@PathVariable Long orderId,
                                                        @RequestHeader("Authorization") String jwt) throws OrderException{
        Orders order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Orders> deliverOrderHandler(@PathVariable Long orderId,
                                                      @RequestHeader("Authorization") String jwt) throws OrderException{
        Orders order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Orders> cancelOrderHandler(@PathVariable Long orderId,
                                                      @RequestHeader("Authorization") String jwt) throws OrderException{
        Orders order = orderService.cancelledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Order deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
    }
}
