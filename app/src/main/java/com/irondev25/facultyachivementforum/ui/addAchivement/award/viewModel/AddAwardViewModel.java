package com.irondev25.facultyachivementforum.ui.addAchivement.award.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.addAchivement.award.repository.AddAwardRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;

import okhttp3.MultipartBody;

public class AddAwardViewModel extends AndroidViewModel {
    private AddAwardRepository repository;
    private LiveData<AwardObject> liveData;

    public AddAwardViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AddAwardRepository();
        liveData = repository.getLiveData();
    }

    public void createProfileAward(String token,
                                   String awardTitle, String awardDate,
                                   String awardDetail,
                                   MultipartBody.Part certificate) {
        repository.createProfileAward(token, awardTitle, awardDate, awardDetail, certificate);
    }

    public LiveData<AwardObject> getLiveData() {
        return liveData;
    }
}
