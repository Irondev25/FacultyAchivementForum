package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TeacherDetailPublic {
    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String middleName;

    @SerializedName("middle_name")
    public String lastName;

    @SerializedName("email")
    public String email;

    @SerializedName("mobile_num")
    public String mobileNum;

    @SerializedName("department")
    public String departmentName;

    @SerializedName("profile_pic")
    public String profilePic;

    @SerializedName("award_set")
    public List<Award> awardsList;

    @SerializedName("conference_set")
    public List<Conference> conferencesList;

    @SerializedName("journal_set")
    public List<Journal> journalsList;

    @SerializedName("workshop_set")
    public List<Workshop> workshopList;

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Award> getAwardsList() {
        return awardsList;
    }

    public void setAwardsList(List<Award> awardsList) {
        this.awardsList = awardsList;
    }

    public List<Conference> getConferencesList() {
        return conferencesList;
    }

    public void setConferencesList(List<Conference> conferencesList) {
        this.conferencesList = conferencesList;
    }

    public List<Journal> getJournalsList() {
        return journalsList;
    }

    public void setJournalsList(List<Journal> journalsList) {
        this.journalsList = journalsList;
    }

    public List<Workshop> getWorkshopList() {
        return workshopList;
    }

    public void setWorkshopList(List<Workshop> workshopList) {
        this.workshopList = workshopList;
    }

    public class Award{
        @SerializedName("award_title")
        String title;

        @SerializedName("award_date")
        String date;

        @SerializedName("award_details")
        String details;

        @SerializedName("certificate")
        String certificate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Date getDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(this.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }
    }

    public class Conference {
        //        "name": "How to be best teacher",
//                "title": "Best Teacher",
//                "date": "2020-10-26",
//                "certificate": "http://localhost:8000/media/user_70/conference/1603692695.2100325_icons8-conference-64.png",
//                "conference_type": "N"
        @SerializedName("name")
        String conferenceName;

        @SerializedName("title")
        String conferenceTitle;

        @SerializedName("date")
        String conferenceDate;

        @SerializedName("certificate")
        String conferenceCertificate;

        @SerializedName("conference_type")
        String conferenceType;

        public String getConferenceName() {
            return conferenceName;
        }

        public void setConferenceName(String conferenceName) {
            this.conferenceName = conferenceName;
        }

        public String getConferenceTitle() {
            return conferenceTitle;
        }

        public void setConferenceTitle(String conferenceTitle) {
            this.conferenceTitle = conferenceTitle;
        }

        public Date getConferenceDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(this.conferenceDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public void setConferenceDate(String conferenceDate) {
            this.conferenceDate = conferenceDate;
        }

        public String getConferenceCertificate() {
            return conferenceCertificate;
        }

        public void setConferenceCertificate(String conferenceCertificate) {
            this.conferenceCertificate = conferenceCertificate;
        }

        public String getConferenceType() {
            return conferenceType;
        }

        public void setConferenceType(String conferenceType) {
            this.conferenceType = conferenceType;
        }
    }

    public class Journal {
        //        "journal_title": "Charactaristics of Best Teacher",
//                "paper_title": "Charactaristics of Best Teacher",
//                "date": "2020-10-06",
//                "journal_type": "N",
//                "impact_factor": 2.3,
//                "certificate": "http://localhost:8000/media/user_70/journal/1603692749.0310674_icons8-journal-100.png"
        @SerializedName("journal_title")
        String journalTitle;

        @SerializedName("paper_title")
        String paperTitle;

        @SerializedName("date")
        String journalDate;

        @SerializedName("journal_type")
        String journalType;

        @SerializedName("impact_factor")
        String impactFactor;

        @SerializedName("certificate")
        String journalCertificate;

        public String getJournalTitle() {
            return journalTitle;
        }

        public void setJournalTitle(String journalTitle) {
            this.journalTitle = journalTitle;
        }

        public String getPaperTitle() {
            return paperTitle;
        }

        public void setPaperTitle(String paperTitle) {
            this.paperTitle = paperTitle;
        }

        public Date getJournalDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(this.journalDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
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

        public String getImpactFactor() {
            return impactFactor;
        }

        public void setImpactFactor(String impactFactor) {
            this.impactFactor = impactFactor;
        }

        public String getJournalCertificate() {
            return journalCertificate;
        }

        public void setJournalCertificate(String journalCertificate) {
            this.journalCertificate = journalCertificate;
        }
    }

    public class Workshop {
//        "topic": "How to be Best Teacher",
//                "date": "2020-10-12",
//                "workshop_type": "N",
//                "location": "BMSCE, Bengaluru",
//                "certificate": "http://localhost:8000/media/user_70/workshop/1603692786.5988593_icons8-google-classroom-96.png"

        @SerializedName("topic")
        String workshopTopic;

        @SerializedName("date")
        String workshopDate;

        @SerializedName("workshop_type")
        String workshopType;

        @SerializedName("location")
        String workshopLocation;

        @SerializedName("certificate")
        String workshopCertificate;

        public String getWorkshopTopic() {
            return workshopTopic;
        }

        public void setWorkshopTopic(String workshopTopic) {
            this.workshopTopic = workshopTopic;
        }

        public Date getWorkshopDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(this.workshopDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public void setWorkshopDate(String workshopDate) {
            this.workshopDate = workshopDate;
        }

        public String getWorkshopType() {
            return workshopType;
        }

        public void setWorkshopType(String workshopType) {
            this.workshopType = workshopType;
        }

        public String getWorkshopLocation() {
            return workshopLocation;
        }

        public void setWorkshopLocation(String workshopLocation) {
            this.workshopLocation = workshopLocation;
        }

        public String getWorkshopCertificate() {
            return workshopCertificate;
        }

        public void setWorkshopCertificate(String workshopCertificate) {
            this.workshopCertificate = workshopCertificate;
        }
    }
}

