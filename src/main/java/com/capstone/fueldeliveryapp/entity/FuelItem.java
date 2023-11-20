package com.capstone.fueldeliveryapp.entity;

public class FuelItem {
    private String fuelItemId;
    private String fuelTypeId;
    private Integer fuelQuantity;
    private String fuelUnit;

    public FuelItem() {
    }

    public FuelItem(String fuelItemId, String fuelTypeId, Integer fuelQuantity, String fuelUnit) {
        this.fuelItemId = fuelItemId;
        this.fuelTypeId = fuelTypeId;
        this.fuelQuantity = fuelQuantity;
        this.fuelUnit = fuelUnit;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }

    public String getFuelItemId() {
        return fuelItemId;
    }

    public void setFuelItemId(String fuelItemId) {
        this.fuelItemId = fuelItemId;
    }

    public String getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(String fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Integer getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(Integer fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    @Override
    public String toString() {
        return "FuelItem{" +
                "fuelItemId='" + fuelItemId + '\'' +
                ", fuelTypeId='" + fuelTypeId + '\'' +
                ", fuelQuantity=" + fuelQuantity +
                '}';
    }
}
