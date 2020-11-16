package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.addAchivement.journal.AddJournal;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.adapter.ProfileJournalAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.viewModel.JournalDeleteViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.viewModel.JournalViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit.ProfileJournalEdit;

import java.util.List;

import okhttp3.ResponseBody;

public class ProfileJournal extends Fragment implements ProfileJournalAdapter.MyCardButtons {
    private static final String TAG = "ProfileJournal";
    private static final int ADD_JOURNAL = 125;
    private JournalViewModel viewModel;
    private JournalDeleteViewModel deleteViewModel;
    private ProfileJournalAdapter adapter;
    private List<JournalObject> journals;

    ProgressDialog progressBar;

    String token;

    public ProfileJournal(String token) {
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProfileJournalAdapter(this);
        viewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        deleteViewModel = ViewModelProviders.of(this).get(JournalDeleteViewModel.class);
        viewModel.init();
        deleteViewModel.init();
        viewModel.getProfileJournal(token);
        viewModel.getProfileJournalLiveData().observe(this, new Observer<List<JournalObject>>() {
            @Override
            public void onChanged(List<JournalObject> journalObjects) {
                journals = journalObjects;
                adapter.setResult(journalObjects);
                progressBar.dismiss();
            }
        });

        deleteViewModel.getLiveData().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                Toast.makeText(getContext(), "Journal deleted", Toast.LENGTH_SHORT).show();
                viewModel.getProfileJournal(token);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_journal, container, false);
        setProgressBar(view);
        progressBar.show();

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.journal_referesh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getProfileJournal(token);
                progressBar.show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.tp_journal_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_journal_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddJournal.class);
                intent.putExtra("token",token);
                startActivityForResult(intent,ADD_JOURNAL);
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
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: No clicked");
            }
        });
        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteViewModel.deleteProfileJournal(token,journal.getUrl());
                progressBar.show();
            }
        });
        alertDialog.setTitle("Delete Workshop");
        alertDialog.setMessage("Are you sure?");
        alertDialog.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_JOURNAL) {
            viewModel.getProfileJournal(token);
        }
    }

    public void setProgressBar(View view) {
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }
}
