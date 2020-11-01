package com.irondev25.facultyachivementforum.ui.login.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TeacherSignIn {
    @SerializedName("username")
    @NonNull
    public String username;

    @SerializedName("password")
    @NonNull
    public String password;

    public TeacherSignIn(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
