package com.capstone.fueldeliveryapp.entity;

public class FuelItemDetails extends FuelItem{
    private Fuel fuelDetail;
    public FuelItemDetails(String fuelItemId, String fuelTypeId, int fuelQuantity, Fuel fuelDetail, String fuelUnit) {
        super(fuelItemId, fuelTypeId, fuelQuantity, fuelUnit);
        this.fuelDetail = fuelDetail;
    }
    public Fuel getFuelDetail() {
        return fuelDetail;
    }

    public void setFuelDetail(Fuel fuelDetail) {
        this.fuelDetail = fuelDetail;
    }
}
