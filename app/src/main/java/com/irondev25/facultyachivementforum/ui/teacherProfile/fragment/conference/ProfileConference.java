package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.addAchivement.conference.AddConference;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.adapter.ProfileConferenceAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.viewModel.ConferenceDeleteViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.viewModel.ConferenceViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit.ProfileConferenceEdit;

import java.util.List;

import okhttp3.ResponseBody;

public class ProfileConference extends Fragment implements ProfileConferenceAdapter.MyCardButtons{
    private static final String TAG = "ProfileConference";
    private ConferenceViewModel viewModel;
    private ConferenceDeleteViewModel deleteViewModel;
    private ProfileConferenceAdapter adapter;
    private List<ConferenceObject> conferences;

    public static final int ADD_CONFERENCE = 124;

    String token;

    public ProfileConference(String token) {
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProfileConferenceAdapter(this);

        viewModel = ViewModelProviders.of(this).get(ConferenceViewModel.class);
        deleteViewModel = ViewModelProviders.of(this).get(ConferenceDeleteViewModel.class);
        viewModel.init();
        deleteViewModel.init();
        viewModel.getProfileConferences(token);
        viewModel.getProfileConferenceLiveData().observe(this, new Observer<List<ConferenceObject>>() {
            @Override
            public void onChanged(List<ConferenceObject> conferenceObjects) {
                adapter.setResult(conferenceObjects);
            }
        });

        deleteViewModel.getLiveData().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                Toast.makeText(getContext(), "Conference deleted", Toast.LENGTH_SHORT).show();
                viewModel.getProfileConferences(token);
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
                Intent intent = new Intent(getActivity(), AddConference.class);
                intent.putExtra("token",token);
                startActivityForResult(intent,ADD_CONFERENCE);
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
    public void onEditClickButton(ConferenceObject conference) {
        getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                new ProfileConferenceEdit(conference,token)).commit();
    }

    @Override
    public void onDeleteClickButton(ConferenceObject conference) {
        Log.d(TAG, "onDeleteClickButton: delete button clicked");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: No clicked");
            }
        });
        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteViewModel.deleteProfileConference(token,conference.getUrl());
            }
        });
        alertDialog.setTitle("Delete Workshop");
        alertDialog.setMessage("Are you sure?");
        alertDialog.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CONFERENCE) {
            viewModel.getProfileConferences(token);
        }
    }
}
