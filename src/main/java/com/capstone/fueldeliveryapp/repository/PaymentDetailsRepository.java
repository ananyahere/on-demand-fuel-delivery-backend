package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.PaymentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentDetailsRepository extends MongoRepository<PaymentDetails, String>  {
}
