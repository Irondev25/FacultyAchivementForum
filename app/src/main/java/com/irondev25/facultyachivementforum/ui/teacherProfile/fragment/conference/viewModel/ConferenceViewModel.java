package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.repository.ConferenceRepository;

import java.util.List;

public class ConferenceViewModel extends AndroidViewModel {
    private ConferenceRepository repository;
    private LiveData<List<ConferenceObject>> conferenceListLiveData;

    public ConferenceViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new ConferenceRepository();
        conferenceListLiveData = repository.getProfileConferencesLiveData();
    }

    public void getProfileConferences(String token) {
        repository.getProfileConferences(token);
    }

    public LiveData<List<ConferenceObject>> getProfileConferenceLiveData() {
        return conferenceListLiveData;
    }
}
