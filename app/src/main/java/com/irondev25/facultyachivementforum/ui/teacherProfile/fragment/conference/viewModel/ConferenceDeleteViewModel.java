package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.repository.ConferenceDeleteRepository;

import okhttp3.ResponseBody;

public class ConferenceDeleteViewModel extends AndroidViewModel {
    private ConferenceDeleteRepository repository;
    private LiveData<ResponseBody> liveData;

    public ConferenceDeleteViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new ConferenceDeleteRepository();
        liveData = repository.getLiveData();
    }

    public void deleteProfileConference(String token, String url) {
        repository.deleteProfileConference(token, url);
    }

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
}
