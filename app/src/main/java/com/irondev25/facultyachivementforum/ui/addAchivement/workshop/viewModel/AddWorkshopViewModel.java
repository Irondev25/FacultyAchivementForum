package com.irondev25.facultyachivementforum.ui.addAchivement.workshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.addAchivement.workshop.repository.AddWorkshopRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;

import okhttp3.MultipartBody;

public class AddWorkshopViewModel extends AndroidViewModel {
    private AddWorkshopRepository repository;
    private LiveData<WorkshopObject> liveData;

    public AddWorkshopViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AddWorkshopRepository();
        liveData = repository.getLiveData();
    }

    public void createProfileWorkshop(String token,
                                      String workshopTopic, String workshopLocation,
                                      String workshopDate, String workshopType,
                                      MultipartBody.Part certificate) {
        repository.createProfileWorkshop(token, workshopTopic, workshopLocation, workshopDate, workshopType, certificate);
    }

    public LiveData<WorkshopObject> getLiveData() {
        return liveData;
    }
}
