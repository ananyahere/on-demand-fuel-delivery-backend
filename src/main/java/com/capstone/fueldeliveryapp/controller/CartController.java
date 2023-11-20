package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.Cart;
import com.capstone.fueldeliveryapp.entity.FuelItem;
import com.capstone.fueldeliveryapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    // To get cart for a user
    @GetMapping("/{userId}")
    public ResponseEntity getCartForUser(@PathVariable String userId) {
        Cart cart = cartService.getCartForUser(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    // To add a fuel item to the cart
    @PostMapping("/{userId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable String userId, @RequestBody FuelItem fuelItem) {
        Cart cart = cartService.addItemToCart(userId, fuelItem);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    // To remove a fuel item from the cart
    @DeleteMapping("/{userId}/{fuelItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable String userId, @PathVariable String fuelItemId) {
        Cart cart = cartService.removeItemFromCart(userId, fuelItemId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    // To check out single-item
    // To check out cart
    // To clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable String userId) {
        Cart cart = cartService.clearCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(ErrorResponse exc){
        // create a ErrorResponse
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exc){
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException exc){
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
