package com.irondev25.facultyachivementforum.ui.teacherProfile.pojo;

import com.google.gson.annotations.SerializedName;

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
    List<AwardSet> awardSet;

    @SerializedName("conference_set")
    List<ConferenceSet> conferenceSets;
    @SerializedName("journal_set")
    List<JournalSet> journalSets;
    @SerializedName("workshop_set")
    List<WorkshopSet> workshopSets;

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

    public List<AwardSet> getAwardSet() {
        return awardSet;
    }

    public void setAwardSet(List<AwardSet> awardSet) {
        this.awardSet = awardSet;
    }

    public List<ConferenceSet> getConferenceSets() {
        return conferenceSets;
    }

    public void setConferenceSets(List<ConferenceSet> conferenceSets) {
        this.conferenceSets = conferenceSets;
    }

    public List<JournalSet> getJournalSets() {
        return journalSets;
    }

    public void setJournalSets(List<JournalSet> journalSets) {
        this.journalSets = journalSets;
    }

    public List<WorkshopSet> getWorkshopSets() {
        return workshopSets;
    }

    public void setWorkshopSets(List<WorkshopSet> workshopSets) {
        this.workshopSets = workshopSets;
    }

    class AwardSet {
        @SerializedName("award_title")
        String AwardTitle;

        public String getAwardTitle() {
            return AwardTitle;
        }

        public void setAwardTitle(String awardTitle) {
            AwardTitle = awardTitle;
        }
    }

    class ConferenceSet {
        @SerializedName("conference_title")
        String conferenceTitle;

        public String getConferenceTitle() {
            return conferenceTitle;
        }

        public void setConferenceTitle(String conferenceTitle) {
            this.conferenceTitle = conferenceTitle;
        }
    }

    class JournalSet {
        @SerializedName("journal_title")
        String journalTitle;

        public String getJournalTitle() {
            return journalTitle;
        }

        public void setJournalTitle(String journalTitle) {
            this.journalTitle = journalTitle;
        }
    }

    class WorkshopSet {
        @SerializedName("topic")
        String topic;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }
}
