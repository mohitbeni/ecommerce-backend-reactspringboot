package com.ecomm.controller;

import com.ecomm.exception.CartItemException;
import com.ecomm.exception.ProductException;
import com.ecomm.exception.UserException;
import com.ecomm.model.Cart;
import com.ecomm.model.CartItem;
import com.ecomm.model.User;
import com.ecomm.request.AddItemRequest;
import com.ecomm.response.ApiResponse;
import com.ecomm.service.CartItemService;
import com.ecomm.service.CartService;
import com.ecomm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management",description = "find user cart, add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt)
                            throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(),req);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
