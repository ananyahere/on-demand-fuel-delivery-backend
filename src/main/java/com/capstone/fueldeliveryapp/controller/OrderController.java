package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.Order;
import com.capstone.fueldeliveryapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    // To create new order
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order){
        try{
            Order placedOrder = orderService.placeOrder(order);
            return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
        } catch (IllegalArgumentException iae){
            System.out.println(iae);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("")
    // To retrieve the order history
    public ResponseEntity<List<Order>> getOrderHistory(){
        List<Order> orders = orderService.getOrderHistory();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // To retrieve the order history of a specific user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Order>> getOrderHistoryOfUser(@PathVariable("userId") String userId){
        try{
            List<Order> orderHistory = orderService.getOrderHistoryOfUser(userId);
            return new ResponseEntity<>(orderHistory, HttpStatus.OK);
        } catch (NoSuchFieldException | IllegalAccessException nsfe){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To retrieve order details by Id
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetailsById(@PathVariable("orderId") String orderId){
        try{
            Order order = orderService.getOrderDetailsById(orderId);
            if(order == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoSuchFieldException | IllegalAccessException nsfe){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // To update order status
    @PostMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("orderId") String orderId,  @RequestBody Map<String, String> requestBody) throws NoSuchFieldException, IllegalAccessException {
        String orderStatus = requestBody.get("status");
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        System.out.println(updatedOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // To generate deliveryOTP
    @GetMapping("/{orderId}/otp")
    public ResponseEntity<?> getDeliveryOTP(@PathVariable("orderId") String orderId){
        System.out.println("here1");
        String otp = orderService.generateDeliveryOTP(orderId);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("deliveryOtp", otp);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // To verify deliveryOTP
    @PostMapping("/{orderId}/otp")
    public ResponseEntity<?> verifyDeliveryOTP(@PathVariable("orderId") String orderId, @RequestBody Map<String, String> requestBody){
        String userOTP  = requestBody.get("otp");
        Boolean isMatch = orderService.verifyOTP(orderId, userOTP);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", isMatch);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
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
