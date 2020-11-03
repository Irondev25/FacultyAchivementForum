package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo;

import com.google.gson.annotations.SerializedName;

public class WorkshopObject {
    @SerializedName("pid")
    Integer pid;
    @SerializedName("topic")
    String topic;
    @SerializedName("date")
    String date;
    @SerializedName("workshop_type")
    String workshopType;
    @SerializedName("location")
    String location;
    @SerializedName("certificate")
    String certificate;
    @SerializedName("url")
    String url;

    Throwable error;

    public void setError(Throwable error) {
        this.error = error;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkshopType() {
        return workshopType;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
