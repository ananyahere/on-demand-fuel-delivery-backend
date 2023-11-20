package com.capstone.fueldeliveryapp.repository;

import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.entity.UserAuth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthRepository extends MongoRepository<UserAuth, String> {
    @Query("{ 'role.roleName' : ?0 }")
    List<UserAuth> findByRoleName(String roleName);
}
