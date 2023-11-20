package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.Address;
import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.entity.Vehicle;
import com.capstone.fueldeliveryapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Retrieve all users
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Retrieve single user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User>  getUserById(@PathVariable String userId) throws ResourceNotFoundException {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Retrieve all user addresses
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable String userId) throws ResourceNotFoundException {
        List<Address> addresses = userService.getAddresses(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }


    // Retrieve single user address by ID
    @GetMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable String userId, @PathVariable String addressId) throws ResourceNotFoundException {
        Address address = userService.getAddressById(userId, addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    // Save a new or existing user address
    @PostMapping("/{userId}/addresses")
    public ResponseEntity<User> saveAddress(@PathVariable String userId, @RequestBody Address address) throws ResourceNotFoundException {
        User user = userService.saveAddress(userId, address);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Retrieve all user vehicles
    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable String userId) throws ResourceNotFoundException {
        List<Vehicle> vehicles = userService.getVehicles(userId);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    // Retrieve single user vehicle by ID
    @GetMapping("/{userId}/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String userId, @PathVariable String vehicleId) throws ResourceNotFoundException {
        Vehicle vehicle = userService.getVehicleById(userId, vehicleId);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    // Save a new or existing user vehicle
    @PostMapping("/{userId}/vehicles")
    public ResponseEntity<User> saveVehicle(@PathVariable String userId, @RequestBody Vehicle vehicle) throws ResourceNotFoundException {
        User user = userService.saveVehicle(userId, vehicle);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Update name of existing user by ID
    @PutMapping("/{userId}/name")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody Map<String, String> requestBody) throws ResourceNotFoundException {
        String name = requestBody.get("name");
        User user = userService.updateUserName(userId, name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete an existing user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) throws ResourceNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
