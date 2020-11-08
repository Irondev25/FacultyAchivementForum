package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit.repository.ConferenceEditRepository;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

public class ConferenceEditViewModel extends AndroidViewModel {
    private ConferenceEditRepository repository;
    private LiveData<ConferenceObject> liveData;


    public ConferenceEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new ConferenceEditRepository();
        liveData = repository.getLiveData();
    }

    public void updateProfileConference(String token, String url,
                                        String conferenceName, String paperTitle,
                                        String conferenceDate, MultipartBody.Part certificate,
                                        String conferenceType
                                        ) {
        repository.updateProfileConference(token, url, conferenceName, paperTitle, conferenceDate, conferenceType, certificate);
    }

    public LiveData<ConferenceObject> getLiveData() {
        return liveData;
    }
}
