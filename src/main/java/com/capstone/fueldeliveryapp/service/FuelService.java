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

    // Function to edit a fuel
    public Fuel editFuel(String fuelId, Fuel updatedFuel) {
        Optional<Fuel> fuelOptional = fuelRepository.findById(fuelId);
        if (fuelOptional.isEmpty()) {
            return null;
        }
        Fuel fuel = fuelOptional.get();
        // Update fields that were modified in the updatedFuel
        if (updatedFuel.getFuelType() != null) {
            fuel.setFuelType(updatedFuel.getFuelType());
        }
        if (updatedFuel.getFuelStock() != null) {
            fuel.setFuelStock(updatedFuel.getFuelStock());
        }
        if (updatedFuel.getFuelStockUnit() != null) {
            fuel.setFuelStockUnit(updatedFuel.getFuelStockUnit());
        }
        if (updatedFuel.getFuelSupplier() != null) {
            fuel.setFuelSupplier(updatedFuel.getFuelSupplier());
        }
        if (updatedFuel.getBasePriceHyd() != null) {
            fuel.setBasePriceHyd(updatedFuel.getBasePriceHyd());
        }
        if (updatedFuel.getBasePriceBlr() != null) {
            fuel.setBasePriceBlr(updatedFuel.getBasePriceBlr());
        }
        if (updatedFuel.getBasePriceBhu() != null) {
            fuel.setBasePriceBhu(updatedFuel.getBasePriceBhu());
        }
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
