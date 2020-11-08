package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit.repository.AwardEditRepository;

import okhttp3.MultipartBody;

public class AwardEditViewModel extends AndroidViewModel {
    private AwardEditRepository repository;
    private LiveData<AwardObject> liveData;

    public AwardEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AwardEditRepository();
        liveData = repository.getLiveData();
    }

    public void updateProfileAward(String token, String url, String awardTitle,
                                   String awardDate, String awardDetail,
                                   MultipartBody.Part certificate) {
        repository.updateProfileAward(token,url,awardTitle,awardDate,awardDetail,certificate);
    }

    public LiveData<AwardObject> getLiveData() {
        return liveData;
    }
}
