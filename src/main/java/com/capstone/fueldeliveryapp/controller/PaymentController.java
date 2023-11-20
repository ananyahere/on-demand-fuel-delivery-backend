package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.FuelItemDetails;
import com.capstone.fueldeliveryapp.entity.Order;
import com.capstone.fueldeliveryapp.entity.PaymentDetails;
import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.repository.OrderRepository;
import com.capstone.fueldeliveryapp.repository.PaymentDetailsRepository;
import com.capstone.fueldeliveryapp.service.OrderService;
import com.capstone.fueldeliveryapp.service.PaymentDetailsService;
import com.capstone.fueldeliveryapp.service.UserService;
import com.razorpay.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @PostMapping("/{orderId}/paymentLink")
    public ResponseEntity<?> createPaymentLink(@PathVariable String orderId) throws RazorpayException {
        // get-order details
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(!foundOrder.isPresent()) return null;
        Order order = foundOrder.get();
        try{
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_YANFvEeCNTFaQ2", "jUd3lsdhOUcA4usJ29vpjvss");
            JSONObject paymentLinkReq = new JSONObject();
            Double totalAmount = order.getTotalAmount();
            paymentLinkReq.put("amount", totalAmount*100); // totalAmount(in rupees) * 100 = in paise
            paymentLinkReq.put("currency", "INR");
            JSONObject customerDetails = new JSONObject();
            customerDetails.put("name","ananya");
            customerDetails.put("contact","+916378443464");
            customerDetails.put("email", "ananyasubodh8@gmail.com");
            paymentLinkReq.put("customer", customerDetails);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkReq.put("notify", notify);
            paymentLinkReq.put("callback_url", "http://localhost:4200/payment-success?order_id"+ order.getOrderId()); // where to redirect user after successful payment
            paymentLinkReq.put("callback_method", "get");
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkReq);
            String paymentLinkId = paymentLink.get("id");
            String paymentLinkUrl = paymentLink.get("short_url");
            com.capstone.fueldeliveryapp.entity.PaymentLink createdPaymentLink = new com.capstone.fueldeliveryapp.entity.PaymentLink(paymentLinkUrl, paymentLinkId);
            order.setOrderStatus("Payment-Received");
            orderRepository.save(order);
            return new ResponseEntity<>(createdPaymentLink, HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

//    @PostMapping({"/{orderId}/invoice"})
//    public ResponseEntity<?> createInvoice(@PathVariable String orderId) throws RazorpayException{
//        // get-order details
//        Optional<Order> foundOrder = orderRepository.findById(orderId);
//        if(!foundOrder.isPresent()) return null;
//        Order order = foundOrder.get();
//        try{
//
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//            throw new RazorpayException(e.getMessage());
//        }
//    }

    @GetMapping("")
    public  ResponseEntity<?> getPaymentDetails(
            @RequestParam(name="payment_id") String paymentId,
            @RequestParam(name="order_id") String orderId)  throws RazorpayException {
        PaymentDetails paymentDetails = paymentDetailsService.getPaymentDetails(orderId, paymentId);
        System.out.println(paymentDetails);
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/customer")
    public ResponseEntity<?> createCustomer(@PathVariable String orderId) throws RazorpayException{
        try{
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_YANFvEeCNTFaQ2", "jUd3lsdhOUcA4usJ29vpjvss");
            // create Razorpay customer
            JSONObject customerRequest = new JSONObject();
            customerRequest.put("name", "ananya");
            customerRequest.put("email", "ananyasubodh8@gmail.com");
            customerRequest.put("contact", "+916378443464");
            Customer customer = razorpayClient.customers.create(customerRequest);
            System.out.println(customer);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
        return null;
    }

    @PostMapping("/{orderId}/invoice")
    public ResponseEntity<com.capstone.fueldeliveryapp.entity.Invoice> createInvoice(@PathVariable String orderId) throws RazorpayException, NoSuchFieldException, IllegalAccessException {
        System.out.println("createInvoice");
        // get-order details
        Order order = orderService.getOrderDetailsById(orderId);
        String cutomerId = order.getUserId();
        User customer = userService.getUserById(order.getUserId());
        try{
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_YANFvEeCNTFaQ2", "jUd3lsdhOUcA4usJ29vpjvss");
            // generated -cutomer-id - cust_N1nYrOeu9VAujp
            // create line items
            JSONArray lineItems = new JSONArray();
            List<FuelItemDetails> orderItemsWithDetails = order.getOrderItemsWithDetails();
            for (FuelItemDetails item: orderItemsWithDetails){
                double fuelAmount = item.getFuelQuantity().intValue() * item.getFuelDetail().getBasePriceHyd().intValue() * 100;
                String fuelName = item.getFuelDetail().getFuelId() + " - " + item.getFuelDetail().getFuelType();
                String fuelDesc = "x" + item.getFuelQuantity().toString() + " " +item.getFuelDetail().getFuelStockUnit();
                JSONObject lineItem = new JSONObject();
                lineItem.put("amount", fuelAmount);
                lineItem.put("quantity", item.getFuelQuantity().intValue());
                lineItem.put("name", fuelName.toString());
                lineItem.put("description", fuelDesc);
                lineItem.put("currency", "INR");
                lineItems.put(lineItem);
            }

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Date today = cal.getTime();
            long timestamp = today.getTime() / 1000; // convert milliseconds to seconds
            JSONObject request = new JSONObject();
            request.put("line_items", lineItems);
            request.put("date", timestamp);
            request.put("currency", "INR");
            String invoiceDescription = "Invoice for the order - "+ orderId;
            request.put("description", invoiceDescription);
            // get customer
            JSONObject customerDetails = new JSONObject();
            customerDetails.put("name", customer.getName());
            customerDetails.put("contact","+916378443464");
            customerDetails.put("email", "ananyasubodh8@gmail.com");
            JSONObject address = new JSONObject();
            address.put("line1", order.getDeliveryLoc().getReceiver());
            address.put("line2", order.getDeliveryLoc().getLocation());
            address.put("zipcode", "560068");
            address.put("country", "in");
            customerDetails.put("billing_address", address);
            customerDetails.put("shipping_address", address);
            request.put("customer", customerDetails);
            Invoice invoice = razorpayClient.invoices.create(request);
            // create invoice
            com.capstone.fueldeliveryapp.entity.Invoice createdInvoice = new com.capstone.fueldeliveryapp.entity.Invoice();
            createdInvoice.setInvoiceId(invoice.get("id"));
            createdInvoice.setInvoiceURL(invoice.get("short_url"));
            return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

}
