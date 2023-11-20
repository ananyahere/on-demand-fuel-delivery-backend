package com.capstone.fueldeliveryapp.controller;

import com.capstone.fueldeliveryapp.entity.*;
import com.capstone.fueldeliveryapp.service.JwtService;
import com.capstone.fueldeliveryapp.service.UserAuthService;
import com.capstone.fueldeliveryapp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin  //  enable Cross-Origin Resource Sharing (CORS) for a specific web controller(s)
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers(){
        userAuthService.initRolesAndUsers();
    }

    @PostMapping({"/register"})
    public UserAuth registerUser(@RequestBody UserAuth userAuth){
        return userAuthService.registerUser(userAuth);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('admin')")
    public String forAdmin(){
        return "This URL is only for admins";
    }

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        return jwtService.createJwtToken(jwtRequest);
    }

    @GetMapping({"/catchphase"})
    public ResponseEntity<?>  getUserCatchPhase(@RequestParam(name="email") String username){
        String phase =  userAuthService.getUserCatchPhase(username);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("catchPhase", phase);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // Set user location
    @PostMapping({"/city"})
    public ResponseEntity<User> updateUserCity(@RequestParam(name="user_id") String userId, @RequestBody Map<String, String> requestBody){
        String userCity = requestBody.get("city");
        User user = userService.updateUserCity(userId, userCity);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
