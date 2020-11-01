package com.irondev25.facultyachivementforum.ui.browseAchivement.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherItem {
    @Expose
    @SerializedName("profile_pic")
    public String profilePic;

    @Expose
    @SerializedName("first_name")
    public String firstName;

    @Expose
    @SerializedName("last_name")
    public String lastName;

    @Expose
    @SerializedName("url")
    public String profileUrl;

    @Expose
    @SerializedName("username")
    public String username;

    public TeacherItem(String profilePic, String firstName, String lastName, String profileUrl, String username) {
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileUrl = profileUrl;
        this.username = username;
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

