package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.controller.ResourceNotFoundException;
import com.capstone.fueldeliveryapp.entity.Address;
import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.entity.Vehicle;
import com.capstone.fueldeliveryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve single user by ID
    public User getUserById(String userId) {
        Optional<User> foundUser =  userRepository.findById(userId);
        if(foundUser.isPresent())
            return foundUser.get();
        else
            throw new ResourceNotFoundException("User with id: "+ userId +" not found");
    }

    // Retrieve single user by email
    public User getUserByEmail(String email){
        Optional<User> foundUser = userRepository.findByEmail(email);
        if(foundUser.isPresent()) return foundUser.get();
        else throw new ResourceNotFoundException("User not found");
    }

    // Retrieve all user addresses
    public List<Address> getAddresses(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return user.getAddresses();
    }

    // Retrieve single user address by ID
    public Address getAddressById(String userId, String addressId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Address address = user.getAddresses().stream()
                .filter(a -> a.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));

        return address;
    }

    // Save a new or existing user address
    public User saveAddress(String userId, Address address) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        List<Address> addresses = user.getAddresses();
        // If the address object has an ID, find and replace the existing address with the new one
        if (addresses != null && address.getAddressId() != null && !address.getAddressId().isEmpty()) {
            addresses.add(address);
        }
        // Otherwise, generate a new ID and add the address to the list
        else {
            int addressSize = 0;
            if(addresses != null) addressSize = addresses.size();
            String addressId = address.getAddressId();
            if(addressId == null) addressId = String.valueOf(addressSize + 1);
            address.setAddressId(addressId);
            if(addresses == null) addresses = new ArrayList<>();
            addresses.add(address);
        }
        user.setAddresses(addresses);
        System.out.println(user);
        return userRepository.save(user);
    }

    // Retrieve all user vehicles
    public List<Vehicle> getVehicles(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return user.getVehicles();
    }

    // Retrieve single user vehicle by ID
    public Vehicle getVehicleById(String userId, String vehicleId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Vehicle vehicle = null;
        List<Vehicle> vehicles = user.getVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            if (v.getVehicleId().equals(vehicleId)) {
                vehicle = v;
                break;
            }
        }
        if (vehicle == null) {
            throw new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId);
        }
        return vehicle;
    }

    // Save a new or existing user vehicle
    public User saveVehicle(String userId, Vehicle vehicle) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        List<Vehicle> vehicles = user.getVehicles();

        // If the vehicle object has an ID, find and replace the existing vehicle with the new one
        if (vehicles != null && vehicle.getVehicleId() != null && !vehicle.getVehicleId().isEmpty()) {
            vehicles.add(vehicle);
        }
        // Otherwise, generate a new ID and add the vehicle to the list
        else {
            int vehicleSize = 0;
            if(vehicles != null) vehicleSize = vehicles.size();
            String vehicleId = vehicle.getVehicleId();
            if(vehicleId == null) vehicleId = String.valueOf(vehicleSize + 1);
            vehicle.setVehicleId(vehicleId);
            if(vehicles == null) vehicles = new ArrayList<>();
            vehicles.add(vehicle);
        }
        user.setVehicles(vehicles);
        return userRepository.save(user);
    }

    // Update an existing user by ID
    public User updateUser(String userId, User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        System.out.println("in updateUser service");
        if(userDetails.getName() != null) user.setName(userDetails.getName());
        if(userDetails.getEmail() != null)  user.setEmail(userDetails.getEmail());
        if(userDetails.getAddresses() != null) user.setAddresses(userDetails.getAddresses());
        if(userDetails.getVehicles() != null) user.setVehicles(userDetails.getVehicles());
        if(userDetails.getPaymentMethods() != null) user.setPaymentMethods(userDetails.getPaymentMethods());

        return userRepository.save(user);
    }

    // Update user city
    public User updateUserCity(String userId, String city){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setCity(city);
        return userRepository.save(user);
    }

    // Update user name
    public User updateUserName(String userId, String name){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setName(name);
        System.out.println(user);
        return userRepository.save(user);
    }

    // Delete an existing user by ID
    public void deleteUser(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        userRepository.delete(user);
    }

    // Create user with email
    public User createUserWithEmail(String email, List<String> paymentMethods, String name){
        User user = new User(email, paymentMethods, name);
        return userRepository.save(user);
    }

    public User updateAddress(String userId, String addressId, Address updatedAddress){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Address addressToUpdate = null;
        for(Address address: user.getAddresses()) {
            if(address.getAddressId().equals(addressId)){
                addressToUpdate = address;
                break;
            }
        }
        if (addressToUpdate == null) {
            throw new ResourceNotFoundException("Address not found with id: " + addressId);
        }
        if(updatedAddress.getLocation() != null) addressToUpdate.setLocation(updatedAddress.getLocation());
        if(updatedAddress.getReceiver() != null) addressToUpdate.setReceiver(updatedAddress.getReceiver());
        if(updatedAddress.getType() != null) addressToUpdate.setType(updatedAddress.getType());
        if(updatedAddress.getCity() != null) addressToUpdate.setCity(updatedAddress.getCity());
        if(updatedAddress.getPhone() != null) addressToUpdate.setPhone(updatedAddress.getPhone());

        return userRepository.save(user);
    }

}
