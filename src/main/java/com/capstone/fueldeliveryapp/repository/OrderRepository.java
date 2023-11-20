package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrderByUserId(String userId);
}
