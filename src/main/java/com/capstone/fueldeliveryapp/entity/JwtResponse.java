package com.capstone.fueldeliveryapp.entity;

public class JwtResponse {
    private UserAuth userAuth;
    private String jwtToken;

    public JwtResponse(UserAuth userAuth, String jwtToken) {
        this.userAuth = userAuth;
        this.jwtToken = jwtToken;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
