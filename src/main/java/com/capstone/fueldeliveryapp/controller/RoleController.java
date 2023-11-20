package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.Role;
import com.capstone.fueldeliveryapp.entity.UserAuth;
import com.capstone.fueldeliveryapp.service.RoleService;
import com.capstone.fueldeliveryapp.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class RoleController {
    private RoleService roleService;

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping({"/createRole"})
    public Role createRole(@RequestBody Role role){
        return roleService.createRole(role);
        // In request-body use the exact field name as used in entity
    }

    @GetMapping("/user/{username}/role")
    public Set<Role> getUserRole(@PathVariable String username) {
        UserAuth user = userAuthService.getUserByUsername(username);
        return user.getRole();
    }


}
