package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo;

import com.google.gson.annotations.SerializedName;

public class ProfileObject {
    @SerializedName("profile_pic")
    String profile_pic = null;

    @SerializedName("username")
    String userName = null;

    @SerializedName("email")
    String email = null;

    @SerializedName("first_name")
    String firstName = null;

    @SerializedName("middle_name")
    String middleName = null;

    @SerializedName("last_name")
    String lastName = null;

    @SerializedName("dob")
    String dob = null;

    @SerializedName("doj")
    String doj = null;

    @SerializedName("mobile_num")
    String mobileNum = null;

    @SerializedName("sex")
    String sex = null;

    @SerializedName("department")
    int department;

    Throwable t = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }
}
