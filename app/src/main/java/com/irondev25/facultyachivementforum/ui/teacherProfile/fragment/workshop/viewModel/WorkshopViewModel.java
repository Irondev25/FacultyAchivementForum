package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.repository.JournalRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.repository.WorkshopRepository;

import java.util.List;

import okhttp3.ResponseBody;

public class WorkshopViewModel extends AndroidViewModel {
    private WorkshopRepository repository;
    private LiveData<List<WorkshopObject>> workshopLiveData;
//    private LiveData<ResponseBody> workshopDeleteLiveData;

    public WorkshopViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new WorkshopRepository();
        workshopLiveData = repository.getProfileWorkshopLiveData();
    }

    public void getProfileWorkshop(String token) {
        repository.getProfileWorkshop(token);
    }


    public LiveData<List<WorkshopObject>> getProfileWorkshopLiveData() {
        return workshopLiveData;
    }
}
