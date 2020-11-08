package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshopEdit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshopEdit.repository.WorkshopEditRepository;

import okhttp3.MultipartBody;

public class WorkshopEditViewModel extends AndroidViewModel {
    private WorkshopEditRepository repository;
    private LiveData<WorkshopObject> liveData;

    public WorkshopEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new WorkshopEditRepository();
        liveData = repository.getLiveData();
    }

    public void updateProfileWorkshop(String token, String url,
                                      String workshopTopic, String workshopLocation,
                                      String workshopDate, String workshopType,
                                      MultipartBody.Part certificate) {
        repository.updateProfileWorkshop(token, url, workshopTopic, workshopLocation, workshopDate, workshopType, certificate);
    }

    public LiveData<WorkshopObject> getLiveData() {
        return liveData;
    }
}
