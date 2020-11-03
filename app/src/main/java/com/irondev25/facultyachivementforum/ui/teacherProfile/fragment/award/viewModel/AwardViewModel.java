package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.repository.AwardRepository;

import java.util.List;

public class AwardViewModel extends AndroidViewModel {
    private AwardRepository repository;
    private LiveData<List<AwardObject>> awardListLiveData;

    public AwardViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AwardRepository();
        awardListLiveData = repository.getProfileAwardsLiveData();
    }

    public void getProfileAwards(String token) {
        repository.getProfileAwards(token);
    }

    public LiveData<List<AwardObject>> getProfileAwardsLiveData() {
        return awardListLiveData;
    }
}
