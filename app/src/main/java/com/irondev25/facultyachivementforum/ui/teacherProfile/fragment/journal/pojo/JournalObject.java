package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo;

import com.google.gson.annotations.SerializedName;

public class JournalObject {
    @SerializedName("pid")
    Integer pid;
    @SerializedName("journal_title")
    String journalTitle;
    @SerializedName("paper_title")
    String journalPaperTitle;
    @SerializedName("date")
    String journalDate;
    @SerializedName("journal_type")
    String journalType;
    @SerializedName("impact_factor")
    String journalImpactFactor;
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

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getJournalPaperTitle() {
        return journalPaperTitle;
    }

    public void setJournalPaperTitle(String journalPaperTitle) {
        this.journalPaperTitle = journalPaperTitle;
    }

    public String getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(String journalDate) {
        this.journalDate = journalDate;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    public String getJournalImpactFactor() {
        return journalImpactFactor;
    }

    public void setJournalImpactFactor(String journalImpactFactor) {
        this.journalImpactFactor = journalImpactFactor;
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
