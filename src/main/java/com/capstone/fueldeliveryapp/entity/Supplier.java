package com.capstone.fueldeliveryapp.entity;

public class Supplier{
    private String supplierName;
    private String supplierContactNo;

    public Supplier() {
    }

    public Supplier(String supplierName, String supplierContactNo) {
        this.supplierName = supplierName;
        this.supplierContactNo = supplierContactNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContactNo() {
        return supplierContactNo;
    }

    public void setSupplierContactNo(String supplierContactNo) {
        this.supplierContactNo = supplierContactNo;
    }
}
