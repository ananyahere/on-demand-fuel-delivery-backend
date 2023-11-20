package com.capstone.fueldeliveryapp.entity;

public class Vehicle{
    private String vehicleId;
    private String vehicleModel;
    private String vehicleColor;
    private String vehicleFuelType;
    private String vehicleCarType;

    public Vehicle(String vehicleId, String vehicleModel, String vehicleColor, String vehicleFuelType, String vehicleCarType) {
        this.vehicleId = vehicleId;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.vehicleFuelType = vehicleFuelType;
        this.vehicleCarType = vehicleCarType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleFuelType() {
        return vehicleFuelType;
    }

    public void setVehicleFuelType(String vehicleFuelType) {
        this.vehicleFuelType = vehicleFuelType;
    }

    public String getVehicleCarType() {
        return vehicleCarType;
    }

    public void setVehicleCarType(String vehicleCarType) {
        this.vehicleCarType = vehicleCarType;
    }
}
