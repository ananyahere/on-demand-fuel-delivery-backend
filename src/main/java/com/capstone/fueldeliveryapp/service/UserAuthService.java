package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.Role;
import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.entity.UserAuth;
import com.capstone.fueldeliveryapp.repository.RoleRepository;
import com.capstone.fueldeliveryapp.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    public UserAuth registerUser(UserAuth userAuth){
        Role role = roleService.findById("user");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userAuth.setRole(roles);
        userAuth.setUserPassword(getEncodedPassword(userAuth.getUserPassword()));
        // create & save user
        String userEmail = userAuth.getUserEmail();
        User user = userService.createUserWithEmail(userEmail);
        // get mongo-id of user
        String userId = user.getUserId();
        // set mongo-id to user-auth
        userAuth.setUserId(userId);
        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Credit Card");
        user.setName("Default-User");
        user.setPaymentMethods(paymentMethods);
        return userAuthRepository.save(userAuth);
    }

    public void initRolesAndUsers(){
        // create Admin role
        Role adminRole = new Role();
        adminRole.setRoleName("admin");
        adminRole.setRoleDescription("The Admin role for the application");
        roleService.save(adminRole);
        // create User role
        Role userRole = new Role();
        userRole.setRoleName("user");
        userRole.setRoleDescription("Default role of newly created user");
        roleService.save(userRole);

        // save admin
        UserAuth adminUser = new UserAuth();
        adminUser.setUsername("adminUN");
        Set<Role> adminUserRoles = new HashSet<>();
        adminUserRoles.add(adminRole);
        adminUser.setRole(adminUserRoles);
        // adminUser.setUserPassword("adminPass"); // must be encrypted
        adminUser.setUserPassword(getEncodedPassword("adminPass"));
        userAuthRepository.save(adminUser);
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    public UserAuth getUserByUsername(String username) {
        return userAuthRepository.findById(username).get();
    }

    public String getUserCatchPhase(String username) {
        UserAuth userAuth = getUserByUsername(username);
        return userAuth.getCatchPhase();
    }
}
