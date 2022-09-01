package com.sg.angulliamosque.models;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;

import java.util.Observable;

public class LoginRequest extends BaseObservable {
    String username;
    String password;
    String deviceToken;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public  boolean isValidDate(){
        return !TextUtils.isEmpty(getUsername().trim()) && TextUtils.isEmpty(getPassword().trim());

    }
}
