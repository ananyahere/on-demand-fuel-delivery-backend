package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.Fuel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelRepository extends MongoRepository<Fuel, String> {
    List<Fuel> findByFuelTypeIgnoreCase(String fuelType);
}
