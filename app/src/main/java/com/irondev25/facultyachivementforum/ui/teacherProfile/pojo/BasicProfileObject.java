package com.irondev25.facultyachivementforum.ui.teacherProfile.pojo;

import com.google.gson.annotations.SerializedName;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;

import java.util.List;

public class BasicProfileObject {
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("mobile_num")
    String mobileNum;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("middle_name")
    String middleName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("dob")
    String dob;
    @SerializedName("doj")
    String doj;
    @SerializedName("sex")
    String sex;
    @SerializedName("department")
    String department;
    @SerializedName("profile_pic")
    String profilePic;

    @SerializedName("award_set")
    List<AwardObject> awardSet;
    @SerializedName("conference_set")
    List<ConferenceObject> conferenceSets;
    @SerializedName("journal_set")
    List<JournalObject> journalSets;
    @SerializedName("workshop_set")
    List<WorkshopObject> workshopSets;

    Throwable error;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<AwardObject> getAwardSet() {
        return awardSet;
    }

    public void setAwardSet(List<AwardObject> awardSet) {
        this.awardSet = awardSet;
    }

    public List<ConferenceObject> getConferenceSets() {
        return conferenceSets;
    }

    public void setConferenceSets(List<ConferenceObject> conferenceSets) {
        this.conferenceSets = conferenceSets;
    }

    public List<JournalObject> getJournalSets() {
        return journalSets;
    }

    public void setJournalSets(List<JournalObject> journalSets) {
        this.journalSets = journalSets;
    }

    public List<WorkshopObject> getWorkshopSets() {
        return workshopSets;
    }

    public void setWorkshopSets(List<WorkshopObject> workshopSets) {
        this.workshopSets = workshopSets;
    }
}
