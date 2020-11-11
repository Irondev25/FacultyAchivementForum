package com.irondev25.facultyachivementforum.ui.addAchivement.journal.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.addAchivement.journal.repository.AddJournalRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;

import okhttp3.MultipartBody;

public class AddJournalViewModel extends AndroidViewModel {
    private AddJournalRepository repository;
    private LiveData<JournalObject> liveData;

    public AddJournalViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new AddJournalRepository();
        liveData = repository.getLiveData();
    }

    public void createProfileJournal(String token, String journalTitle,
                                     String paperTitle, String journalDate,
                                     String journalType, String journalImpactFactor,
                                     MultipartBody.Part certificate) {
        repository.createProfileJournal(token, journalTitle, paperTitle, journalDate, journalType, journalImpactFactor, certificate);
    }

    public LiveData<JournalObject> getLiveData() {
        return liveData;
    }
}
