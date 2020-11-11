package com.irondev25.facultyachivementforum.ui.addAchivement.conference.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.addAchivement.conference.repository.AddConferenceRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;

import okhttp3.MultipartBody;

public class AddConferenceViewModel extends AndroidViewModel {
    private AddConferenceRepository repository;
    private LiveData<ConferenceObject> liveData;

    public AddConferenceViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AddConferenceRepository();
        liveData = repository.getLiveData();
    }

    public void createProfileConference(String token,
                                        String conferenceName,
                                        String paperTitle,
                                        String conferenceDate,
                                        String conferenceType,
                                        MultipartBody.Part certificate) {
        repository.createProfileConference(token,conferenceName,paperTitle,
                conferenceDate,conferenceType,certificate);
    }

    public LiveData<ConferenceObject> getLiveData() {
        return liveData;
    }
}
