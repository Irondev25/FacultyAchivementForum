package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.repository.ProfileUpdateRepository;

import okhttp3.MultipartBody;

public class ProfileUpdateViewModel extends AndroidViewModel {
    private ProfileUpdateRepository repository;
    private LiveData<ProfileObject> liveData;

    public ProfileUpdateViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        repository = new ProfileUpdateRepository();
        liveData = repository.getLiveData();
    }

    public void teacherProfileUpdate(String username, String email, String firstName, String middleName, String lastName,
                                     String dob, String doj, String sex, Integer department,
                                     String mobileNum,
                                     MultipartBody.Part profilePic, String token){
        repository.teacherProfileUpdate(
                username, email, firstName, middleName, lastName, dob, doj, sex,department, mobileNum, profilePic, token);
    }

    public LiveData<ProfileObject> getLiveData() {
        return liveData;
    }
}
