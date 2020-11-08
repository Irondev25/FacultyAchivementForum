package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.adapter.ProfileJournalAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.viewModel.JournalViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit.ProfileJournalEdit;

import java.util.List;

public class ProfileJournal extends Fragment implements ProfileJournalAdapter.MyCardButtons {
    private static final String TAG = "ProfileJournal";
    private JournalViewModel viewModel;
    private ProfileJournalAdapter adapter;
    private List<JournalObject> journals;

    String token;

    public ProfileJournal(String token) {
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProfileJournalAdapter(this);
        viewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        viewModel.init();
        viewModel.getProfileJournal(token);
        viewModel.getProfileJournalLiveData().observe(this, new Observer<List<JournalObject>>() {
            @Override
            public void onChanged(List<JournalObject> journalObjects) {
                journals = journalObjects;
                adapter.setResult(journalObjects);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_journal, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.tp_journal_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_journal_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: fab clicked");
            }
        });
        return view;
    }

    @Override
    public void onDownloadButtonClick(String url) {
        Intent browerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browerIntent);
    }

    @Override
    public void onEditClickButton(JournalObject journal) {
        getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                new ProfileJournalEdit(journal,token)).commit();
    }

    @Override
    public void onDeleteClickButton(JournalObject journal) {
        Log.d(TAG, "onDeleteClickButton: delete button clicked");
    }
}
