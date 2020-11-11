package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.repository.JournalDeleteRepository;

import okhttp3.ResponseBody;

public class JournalDeleteViewModel extends AndroidViewModel {
    private JournalDeleteRepository repository;
    private LiveData<ResponseBody> liveData;

    public JournalDeleteViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new JournalDeleteRepository();
        liveData = repository.getLiveData();
    }

    public void deleteProfileJournal(String token, String url) {
        repository.deleteProfileJournal(token,url);
    }

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
}
