package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.Role;
import com.capstone.fueldeliveryapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role){
        return roleRepository.save(role);
    }

    public Role findById(String roleId){
        Optional<Role> foundRole = roleRepository.findById(roleId);
        return foundRole.orElse(null);
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }
}
