package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.Fuel;
import com.capstone.fueldeliveryapp.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelService {
    @Autowired
    private FuelRepository fuelRepository;

    // Function to get all fuels
    public List<Fuel> getAllFuel() {
        return fuelRepository.findAll();
    }

    // Function to get a fuel by Id
    public Optional<Fuel> getFuelById(String fuelId) {
        return fuelRepository.findById(fuelId);
    }

    // Function to add a new fuel
    public Fuel addFuel(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    // Function to delete a fuel by Id
    public boolean deleteFuelById(String fuelId) {
        Optional<Fuel> optionalFuel = fuelRepository.findById(fuelId);
        if (optionalFuel.isPresent()) {
            fuelRepository.deleteById(fuelId);
            return true;
        } else {
            return false;
        }
    }

    // Function to search fuels by Type
    public List<Fuel> searchFuelByType(String fuelType) {
        return fuelRepository.findByFuelTypeIgnoreCase(fuelType);
    }

}
