package com.capstone.fueldeliveryapp.entity;

public class Invoice {
    private String invoiceID;
    private String invoiceURL;

    public Invoice() {
    }
    public Invoice(String invoiceId, String invoiceURL) {
        this.invoiceID = invoiceId;
        this.invoiceURL = invoiceURL;
    }

    public String getInvoiceId() {
        return invoiceID;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceID = invoiceId;
    }

    public String getInvoiceURL() {
        return invoiceURL;
    }

    public void setInvoiceURL(String invoiceURL) {
        this.invoiceURL = invoiceURL;
    }
}
