package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit.repository.JournalEditRepository;

import okhttp3.MultipartBody;

public class JournalEditViewModel extends AndroidViewModel {
    private JournalEditRepository repository;
    private LiveData<JournalObject> liveData;

    public JournalEditViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new JournalEditRepository();
        liveData = repository.getLiveData();
    }

    public void updateProfileJournal(String token, String url,
                                     String journalName, String paperTitle,
                                     String journalDate, String journalType,
                                     String journalImpactFactor,
                                     MultipartBody.Part certificate) {
        repository.updateProfileJournal(token, url, journalName, paperTitle, journalDate, journalType, journalImpactFactor, certificate);
    }

    public LiveData<JournalObject> getLiveData() {
        return liveData;
    }
}
