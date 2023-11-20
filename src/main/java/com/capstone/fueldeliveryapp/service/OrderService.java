package com.capstone.fueldeliveryapp.service;


import com.capstone.fueldeliveryapp.entity.*;
import com.capstone.fueldeliveryapp.repository.FuelRepository;
import com.capstone.fueldeliveryapp.repository.OrderRepository;
import com.capstone.fueldeliveryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FuelRepository fuelRepository;

    // To create new order
    public Order placeOrder(Order order){
        System.out.println(order);
        // perform validation checks on the order object
        if(order == null){
            throw new IllegalArgumentException("Order cannot be null.");
        }
        if (order.getUserId() == null || order.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or empty.");
        }
        if (order.getDeliveryLoc() == null || order.getDeliveryLoc().getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("DeliveryLoc cannot be null or empty.");
        }
        if (order.getTotalAmount().doubleValue() <= 0.0) {
            throw new IllegalArgumentException("TotalAmount must be greater than zero.");
        }
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("OrderItems cannot be null or empty.");
        }

        for (FuelItem fuelItem : order.getOrderItems()) {
            if (fuelItem.getFuelQuantity().intValue() <= 0) {
                throw new IllegalArgumentException("FuelQuantity must be greater than zero.");
            }
            // update the fuel-stock for each fuelItem
            Fuel fuel = fuelRepository.findById(fuelItem.getFuelTypeId()).get();
            int newFuelStock = fuel.getFuelStock().intValue() - fuelItem.getFuelQuantity().intValue();
            fuel.setFuelStock(newFuelStock);
            System.out.println(fuel);
            fuelRepository.save(fuel);
        }
        order.setOrderStatus("Confirmed");
        String otp = generateOTP();
        order.setDeliveryOtp(otp);
        order.setOrderTime(new Date()); // set order time to current time
        return orderRepository.save(order);
    }

    // To retrieve the order history
    public List<Order> getOrderHistory() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) return Collections.emptyList();
        List<Order> orderWithItems = new ArrayList<>();
        for(Order order: orders){
            List<FuelItem> orderItems = order.getOrderItems();
            if(!orderItems.isEmpty()){
                List<FuelItemDetails> orderItemsWithDetails = new ArrayList<>();
                for(FuelItem item: orderItems){
                    // get fuel details by fuelId
                    String fuelId = item.getFuelTypeId();
                    Fuel fuel = fuelRepository.findById(fuelId).orElse(null);
                    FuelItemDetails itemWithDetails  = new FuelItemDetails(item.getFuelItemId(), item.getFuelTypeId(), (Integer) item.getFuelQuantity(), fuel, item.getFuelItemId());
                    orderItemsWithDetails.add(itemWithDetails);
                }
                order.setOrderItemsWithDetails(orderItemsWithDetails);
                order.setOrderItems(null);
            }
            orderWithItems.add(order);
        }
        return orderWithItems;
    }

    // To retrieve the order history of a specific user
    public List<Order> getOrderHistoryOfUser(String userId) throws NoSuchFieldException, IllegalAccessException{
        Optional<User> foundUser =  userRepository.findById(userId);
        if(foundUser.isEmpty()) return Collections.emptyList();
        List<Order> orders = orderRepository.findOrderByUserId(userId);
        if(orders.isEmpty()) return Collections.emptyList();
        List<Order> orderWithItems = new ArrayList<>();
        for(Order order: orders){
            List<FuelItem> orderItems = order.getOrderItems();
            if(!orderItems.isEmpty()){
               List<FuelItemDetails> orderItemsWithDetails = new ArrayList<>();
                for(FuelItem item: orderItems){
                    // get fuel details by fuelId
                    String fuelId = item.getFuelTypeId();
                    Fuel fuel = fuelRepository.findById(fuelId).orElse(null);
                    // create a new FuelItemDetails object with fuel details and add it to the order items with detail
                    FuelItemDetails itemWithDetails  = new FuelItemDetails(item.getFuelItemId(), item.getFuelTypeId(), (Integer) item.getFuelQuantity(), fuel, item.getFuelItemId());
                    orderItemsWithDetails.add(itemWithDetails);
                }
                order.setOrderItemsWithDetails(orderItemsWithDetails);
                order.setOrderItems(null);
            }
            orderWithItems.add(order);
        }
        return orderWithItems;
    }

    // To retrieve order details by Id
    public Order getOrderDetailsById(String orderId) throws NoSuchFieldException, IllegalAccessException{
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        List<FuelItem> orderItems = order.getOrderItems();
        List<FuelItemDetails> orderItemsWithDetails = new ArrayList<>();
        for(FuelItem fuelItem: orderItems){
            String fuelId = fuelItem.getFuelTypeId();
            Fuel fuel = fuelRepository.findById(fuelId).orElse(null);
            // add fuel details feild dynamically to fuel
//            Field field = FuelItem.class.getDeclaredField("fuelDetail");
//            field.setAccessible(true);
//            field.set(fuelItem, fuel);
            // create a new FuelItemDetails object with fuel details
            FuelItemDetails itemWithDetails  = new FuelItemDetails(fuelItem.getFuelItemId(), fuelItem.getFuelTypeId(), (Integer) fuelItem.getFuelQuantity(), fuel, fuelItem.getFuelUnit());
            orderItemsWithDetails.add(itemWithDetails);
        }
        order.setOrderItemsWithDetails(orderItemsWithDetails);
        order.setOrderItems(null);
        return order;
    }

    // To generate delivery OTP
    public String generateDeliveryOTP(String orderId) {
        System.out.println("in generateDeliveryOTP()");
        String otp = generateOTP();
        System.out.println(otp);
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        // set delivery OTP
        order.setDeliveryOtp(otp);
        orderRepository.save(order);
        return otp;
    }

    // To verify delivery OTP
    public Boolean verifyOTP(String orderId, String userOTP){
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        String savedOTP = order.getDeliveryOtp();
        System.out.println("savedOTP: "+savedOTP);
        return savedOTP.equals(userOTP);
    }

    // To update order status
    public Order updateOrderStatus(String orderId, String orderStatus) throws NoSuchFieldException, IllegalAccessException{
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        // update order status
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    // Helper method to generate a unique order ID (using UUID for simplicity)
    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }
    // Helper method to generate delivery otp
    private String generateOTP() {
        Random random = new Random();
        // Generate a random 6-digit OTP
        int otpValue = 100000 + random.nextInt(900000);
        // Convert it to a string and return
        return String.valueOf(otpValue);
    }
}
