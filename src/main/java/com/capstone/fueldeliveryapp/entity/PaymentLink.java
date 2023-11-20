package com.capstone.fueldeliveryapp.entity;

public class PaymentLink {
    private String paymentLinkURL;
    private String paymentLinkID;

    public PaymentLink() {
    }

    public PaymentLink(String paymentLinkURL, String paymentLinkID) {
        this.paymentLinkURL = paymentLinkURL;
        this.paymentLinkID = paymentLinkID;
    }

    public String getPaymentLinkURL() {
        return paymentLinkURL;
    }

    public void setPaymentLinkURL(String paymentLinkURL) {
        this.paymentLinkURL = paymentLinkURL;
    }

    public String getPaymentLinkID() {
        return paymentLinkID;
    }

    public void setPaymentLinkID(String paymentLinkID) {
        this.paymentLinkID = paymentLinkID;
    }
}
