package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);
}
