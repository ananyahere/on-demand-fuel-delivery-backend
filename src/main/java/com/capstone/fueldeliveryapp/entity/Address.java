package com.capstone.fueldeliveryapp.entity;

public class Address{
    private String addressId;
    private String type;
    private String receiver;
    private String location;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", type='" + type + '\'' +
                ", receiver='" + receiver + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
