package com.capstone.fueldeliveryapp.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("fuel")
public class Fuel {
    @Id
    private String fuelId;
    private String fuelType;
    private Number fuelStock;
    private String fuelStockUnit;
    private Supplier fuelSupplier;
    private Number basePriceHyd;
    private Number basePriceBlr;
    private Number basePriceBhu;

    public Fuel() {}

    public Fuel(String fuelId, String fuelType, Number fuelStock, String fuelStockUnit, Supplier fuelSupplier, Number basePriceHyd, Number basePriceBlr, Number basePriceBhu) {
        this.fuelId = fuelId;
        this.fuelType = fuelType;
        this.fuelStock = fuelStock;
        this.fuelStockUnit = fuelStockUnit;
        this.fuelSupplier = fuelSupplier;
        this.basePriceHyd = basePriceHyd;
        this.basePriceBlr = basePriceBlr;
        this.basePriceBhu = basePriceBhu;
    }

    @Override
    public String toString() {
        return "Fuel{" +
                "fuelId='" + fuelId + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", fuelStock=" + fuelStock +
                ", fuelStockUnit='" + fuelStockUnit + '\'' +
                ", fuelSupplier=" + fuelSupplier +
                ", basePriceHyd=" + basePriceHyd +
                ", basePriceBlr=" + basePriceBlr +
                ", basePriceBhu=" + basePriceBhu +
                '}';
    }

    public String getFuelId() {
        return fuelId;
    }

    public void setFuelId(String fuelId) {
        this.fuelId = fuelId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Number getFuelStock() {
        return fuelStock;
    }

    public void setFuelStock(Number fuelStock) {
        this.fuelStock = fuelStock;
    }

    public String getFuelStockUnit() {
        return fuelStockUnit;
    }

    public void setFuelStockUnit(String fuelStockUnit) {
        this.fuelStockUnit = fuelStockUnit;
    }

    public Supplier getFuelSupplier() {
        return fuelSupplier;
    }

    public void setFuelSupplier(Supplier fuelSupplier) {
        this.fuelSupplier = fuelSupplier;
    }

    public Number getBasePriceHyd() {
        return basePriceHyd;
    }

    public void setBasePriceHyd(Number basePriceHyd) {
        this.basePriceHyd = basePriceHyd;
    }

    public Number getBasePriceBlr() {
        return basePriceBlr;
    }

    public void setBasePriceBlr(Number basePriceBlr) {
        this.basePriceBlr = basePriceBlr;
    }

    public Number getBasePriceBhu() {
        return basePriceBhu;
    }

    public void setBasePriceBhu(Number basePriceBhu) {
        this.basePriceBhu = basePriceBhu;
    }
}
