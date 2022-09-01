package com.sg.angulliamosque.models;

public class LoginResponse {
    private String role;
    private String token;
    private String username;
    private String deviceToken;

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
