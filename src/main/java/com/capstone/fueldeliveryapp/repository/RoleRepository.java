package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
}
