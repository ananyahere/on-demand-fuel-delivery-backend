package com.capstone.fueldeliveryapp.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("order")
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {
    @Id
    private String orderId;
    private String userId;
    private Address deliveryLoc;
    private String orderStatus;
    private Double totalAmount;
    private Boolean isImmediate;
    private Date orderTime;
    private Date scheduledTime;
    private List<FuelItem> orderItems;
    private List<FuelItemDetails> orderItemsWithDetails;
    private String deliveryOtp;
    public Order() {}

    public Order(String orderId, String userId, Address deliveryLoc, String orderStatus, Double totalAmount, Boolean isImmediate, Date orderTime, Date scheduledTime, List<FuelItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.deliveryLoc = deliveryLoc;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.isImmediate = isImmediate;
        this.orderTime = orderTime;
        this.scheduledTime = scheduledTime;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryOtp() {
        return deliveryOtp;
    }

    public void setDeliveryOtp(String deliveryOtp) {
        this.deliveryOtp = deliveryOtp;
    }

    public String getUserId() {
        return userId;
    }  // returns mongodb-id

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Address getDeliveryLoc() {
        return deliveryLoc;
    }

    public void setDeliveryLoc(Address deliveryLoc) {
        this.deliveryLoc = deliveryLoc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getImmediate() {
        return isImmediate;
    }

    public void setImmediate(Boolean immediate) {
        isImmediate = immediate;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public List<FuelItem> getOrderItems() {
        return orderItems;
    }

    public List<FuelItemDetails> getOrderItemsWithDetails() { return orderItemsWithDetails; }

    public void setOrderItems(List<FuelItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderItemsWithDetails(List<FuelItemDetails> orderItemsWithDetails) { this.orderItemsWithDetails = orderItemsWithDetails; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", deliveryLoc=" + deliveryLoc +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", isImmediate=" + isImmediate +
                ", orderTime=" + orderTime +
                ", scheduledTime=" + scheduledTime +
                ", orderItems=" + orderItems +
                ", orderItemsWithDetails=" + orderItemsWithDetails +
                '}';
    }
}
