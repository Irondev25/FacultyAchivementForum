package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.repository.ProfileGetRepository;

public class ProfileGetViewModel extends AndroidViewModel {
    private ProfileGetRepository repository;
    private LiveData<ProfileObject> liveData;

    public ProfileGetViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        repository = new ProfileGetRepository();
        liveData = repository.getProfileMutableData();
    }

    public void getProfileData(String token) {
        repository.getProfile(token);
    }

    public LiveData<ProfileObject> getProfileLiveData(){
        return liveData;
    }
}
