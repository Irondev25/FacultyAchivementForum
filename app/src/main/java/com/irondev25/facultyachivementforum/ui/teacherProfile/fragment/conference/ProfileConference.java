package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference;

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
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.adapter.ProfileConferenceAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.viewModel.ConferenceViewModel;

import java.util.List;

public class ProfileConference extends Fragment implements ProfileConferenceAdapter.MyCardButtons{
    private static final String TAG = "ProfileConference";
    private ConferenceViewModel viewModel;
    private ProfileConferenceAdapter adapter;
    private List<ConferenceObject> conferences;

    String token;

    public ProfileConference(String token) {
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProfileConferenceAdapter(this);

        viewModel = ViewModelProviders.of(this).get(ConferenceViewModel.class);
        viewModel.init();
        viewModel.getProfileConferences(token);
        viewModel.getProfileConferenceLiveData().observe(this, new Observer<List<ConferenceObject>>() {
            @Override
            public void onChanged(List<ConferenceObject> conferenceObjects) {
                adapter.setResult(conferenceObjects);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_conference,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.tp_conference_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_add_conference_button);
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
    public void onEditClickButton() {
        Log.d(TAG, "onEditClickButton: edit button clicked");
    }

    @Override
    public void onDeleteClickButton() {
        Log.d(TAG, "onDeleteClickButton: delete button clicked");
    }
}
