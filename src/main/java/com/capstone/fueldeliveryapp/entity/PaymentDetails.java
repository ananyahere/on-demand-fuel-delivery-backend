package com.capstone.fueldeliveryapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment")
public class PaymentDetails {

    @Id
    private String paymentDetailId;
    private String paymentTransactionId;        // razorpay_payment_id
    private String orderId;
    private String paymentStatus;
    private String invoiceId;
    private String deliveryOTP;

    public PaymentDetails() {
    }

//    public PaymentDetails(String paymentDetailId, String paymentTransactionId, String orderId, String paymentStatus, String invoiceId) {
//        this.paymentDetailId = paymentDetailId;
//        this.paymentTransactionId = paymentTransactionId;
//        this.orderId = orderId;
//        this.paymentStatus = paymentStatus;
//        this.invoiceId = invoiceId;
//    }

    public String getDeliveryOTP() {
        return deliveryOTP;
    }

    public void setDeliveryOTP(String deliveryOTP) {
        this.deliveryOTP = deliveryOTP;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(String paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
                "paymentDetailId='" + paymentDetailId + '\'' +
                ", paymentTransactionId='" + paymentTransactionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                '}';
    }
}
