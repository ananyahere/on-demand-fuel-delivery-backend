package com.capstone.fueldeliveryapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("userauth")
public class UserAuth {
    @Id
    private String username;
    private String userPassword;
    private String userEmail;
    private String userId; // mongo-db id of user with same userEmail
    @DBRef
    private Set<Role> role; // stored the role(s) assigned to user. Use association to connect 2 tables
    private String catchPhase;

    public UserAuth() {
    }

    public UserAuth(String username, String userPassword, String userEmail, String userId, Set<Role> role, String catchPhase) {
        this.username = username;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userId = userId;
        this.role = role;
        this.catchPhase = catchPhase;
    }

    public String getCatchPhase() {
        return catchPhase;
    }

    public void setCatchPhase(String catchPhase) {
        this.catchPhase = catchPhase;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
