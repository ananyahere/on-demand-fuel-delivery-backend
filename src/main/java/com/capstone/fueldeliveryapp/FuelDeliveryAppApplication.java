package com.capstone.fueldeliveryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.capstone.fueldeliveryapp")
public class FuelDeliveryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelDeliveryAppApplication.class, args);
	}

}
