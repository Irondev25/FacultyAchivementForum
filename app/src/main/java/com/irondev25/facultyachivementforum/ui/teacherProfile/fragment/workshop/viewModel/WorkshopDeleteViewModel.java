package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.repository.WorkshopDeleteRepository;

import okhttp3.ResponseBody;

public class WorkshopDeleteViewModel extends AndroidViewModel {
    private WorkshopDeleteRepository repository;
    private LiveData<ResponseBody> liveData;


    public WorkshopDeleteViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new WorkshopDeleteRepository();
        liveData = repository.getLiveData();
    }

    public void deleteProfileWorkshop(String token, String url) {
        repository.deleteProfileWorkshop(token,url);
    }

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
}
