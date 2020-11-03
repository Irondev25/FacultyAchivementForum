package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.repository.JournalRepository;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepository repository;
    private LiveData<List<JournalObject>> journalListLiveData;

    public JournalViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new JournalRepository();
        journalListLiveData = repository.getProfileJournalLiveData();
    }

    public void getProfileJournal(String token){
        repository.getProfileJournal(token);
    }

    public LiveData<List<JournalObject>> getProfileJournalLiveData() {
        return journalListLiveData;
    }
}
