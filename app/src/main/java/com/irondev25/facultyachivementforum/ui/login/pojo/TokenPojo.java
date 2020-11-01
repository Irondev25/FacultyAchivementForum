package com.irondev25.facultyachivementforum.ui.login.pojo;

import com.google.gson.annotations.SerializedName;

public class TokenPojo {
    @SerializedName("token")
    String token;

    public Throwable t;

    String username;
    String password;

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

    public TokenPojo(Throwable t) {
        this.t = t;
        token = null;
        username = null;
        password = null;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}

