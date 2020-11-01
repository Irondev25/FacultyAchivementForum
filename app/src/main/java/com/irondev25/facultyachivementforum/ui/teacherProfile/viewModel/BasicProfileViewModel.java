package com.irondev25.facultyachivementforum.ui.teacherProfile.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.repository.BasicProfileRepository;

public class BasicProfileViewModel extends AndroidViewModel {
    BasicProfileRepository repository;
    private LiveData<BasicProfileObject> liveData;

    public BasicProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        repository = new BasicProfileRepository();
        liveData = repository.getLiveData();
    }

    public void getBasicProfile(String username) {
        repository.getProfile(username);
    }

    public LiveData<BasicProfileObject> getLiveData() {
        return liveData;
    }
}
