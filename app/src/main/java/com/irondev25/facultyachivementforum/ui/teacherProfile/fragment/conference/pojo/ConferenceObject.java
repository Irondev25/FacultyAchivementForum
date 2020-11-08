package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo;

import com.google.gson.annotations.SerializedName;

public class ConferenceObject {
    @SerializedName("pid")
    Integer pid;
    @SerializedName("name")
    String conferenceName;
    @SerializedName("title")
    String conferencePaperTitle;
    @SerializedName("date")
    String conferenceDate;
    @SerializedName("certificate")
    String certificate;
    @SerializedName("conference_type")
    String conferenceType;
    @SerializedName("url")
    String url;

    Throwable error;

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getConferencePaperTitle() {
        return conferencePaperTitle;
    }

    public void setConferencePaperTitle(String conferencePaperTitle) {
        this.conferencePaperTitle = conferencePaperTitle;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
