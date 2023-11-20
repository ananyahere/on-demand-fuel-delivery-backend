package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.Order;
import com.capstone.fueldeliveryapp.entity.PaymentDetails;
import com.capstone.fueldeliveryapp.repository.OrderRepository;
import com.capstone.fueldeliveryapp.repository.PaymentDetailsRepository;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentDetailsService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails getPaymentDetails(String orderId, String paymentId)  throws RazorpayException {
        System.out.println("in getPaymentDetails");
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_YANFvEeCNTFaQ2", "jUd3lsdhOUcA4usJ29vpjvss");
        // get order details
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        System.out.println("orderId: "+orderId);
        System.out.println("paymentId: "+paymentId);
        // create payment details
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setOrderId(order.getOrderId());
        paymentDetails.setPaymentStatus("Payment Pending.");
        try{
            Payment payment = razorpayClient.payments.fetch(paymentId);
            System.out.println(payment);
            if(payment.get("status").equals("captures")){
                // update payment status
                paymentDetails.setPaymentStatus("Payment Successful");
                order.setOrderStatus("Payment-Received");
                orderRepository.save(order);
            }
        }catch(Exception e){
            System.out.println(e);
            throw new RazorpayException(e.getMessage());
        }
        return paymentDetailsRepository.save(paymentDetails);
    }

}
