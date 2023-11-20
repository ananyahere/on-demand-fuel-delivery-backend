package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
}
