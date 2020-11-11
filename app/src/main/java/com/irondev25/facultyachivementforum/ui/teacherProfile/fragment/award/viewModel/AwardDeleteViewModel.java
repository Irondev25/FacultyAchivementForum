package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.repository.AwardDeleteRepository;

import okhttp3.ResponseBody;

public class AwardDeleteViewModel extends AndroidViewModel {
    private AwardDeleteRepository repository;
    private LiveData<ResponseBody> liveData;

    public AwardDeleteViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AwardDeleteRepository();
        liveData = repository.getLiveData();
    }

    public void deleteProfileAward(String token, String url) {
        repository.deleteProfileAward(token, url);
    }

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
}
