package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo;

import com.google.gson.annotations.SerializedName;

public class AwardObject {
    @SerializedName("pid")
    Integer pid;
    @SerializedName("award_title")
    String awardTitle;
    @SerializedName("award_date")
    String awardDate;
    @SerializedName("award_details")
    String awardDetails;
    @SerializedName("certificate")
    String certificate;
    @SerializedName("url")
    String url;

    Throwable error;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getAwardTitle() {
        return awardTitle;
    }

    public void setAwardTitle(String awardTitle) {
        this.awardTitle = awardTitle;
    }

    public String getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(String awardDate) {
        this.awardDate = awardDate;
    }

    public String getAwardDetails() {
        return awardDetails;
    }

    public void setAwardDetails(String awardDetails) {
        this.awardDetails = awardDetails;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
