package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.Fuel;
import com.capstone.fueldeliveryapp.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fuels")
public class FuelController {
    @Autowired
    private FuelService fuelService;

    @GetMapping("")
    public ResponseEntity<List<Fuel>> getAllFuels() {
        List<Fuel> fuels = fuelService.getAllFuel();
        return new ResponseEntity<>(fuels, HttpStatus.OK);
    }

    @GetMapping("/{fuelId}")
    public ResponseEntity<Fuel> getFuelById(@PathVariable String fuelId) {
        Optional<Fuel> optionalFuel = fuelService.getFuelById(fuelId);
        if (optionalFuel.isPresent()) {
            Fuel fuel = optionalFuel.get();
            return new ResponseEntity<>(fuel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Fuel> addFuel(@RequestBody Fuel fuel) {
        Fuel savedFuel = fuelService.addFuel(fuel);
        return new ResponseEntity<>(savedFuel, HttpStatus.CREATED);
    }

    @DeleteMapping("/{fuelId}")
    public ResponseEntity<Void> deleteFuelById(@PathVariable String fuelId) {
        boolean deleteStatus = fuelService.deleteFuelById(fuelId);
        if (deleteStatus) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // "/search?fuelType={fuelType}"
    @GetMapping("/search")
    public ResponseEntity<List<Fuel>> searchFuelByType(@RequestParam String fuelType) {
        List<Fuel> fuels = fuelService.searchFuelByType(fuelType);
        if (!fuels.isEmpty()) {
            return new ResponseEntity<>(fuels, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
