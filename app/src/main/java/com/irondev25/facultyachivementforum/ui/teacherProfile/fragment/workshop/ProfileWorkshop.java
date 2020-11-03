package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop;

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
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.adapter.ProfileWorkshopAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.viewModel.WorkshopViewModel;

import java.util.List;

public class ProfileWorkshop extends Fragment implements ProfileWorkshopAdapter.MyCardButtons {
    private static final String TAG = "ProfileWorkshop";
    private WorkshopViewModel viewModel;
    private List<WorkshopObject> workshops;
    public ProfileWorkshopAdapter adapter;

    String token;

    public ProfileWorkshop(String token) {
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ProfileWorkshopAdapter(this);
        viewModel = ViewModelProviders.of(this).get(WorkshopViewModel.class);
        viewModel.init();
        viewModel.getProfileWorkshop(token);
        viewModel.getProfileWorkshopLiveData().observe(this, new Observer<List<WorkshopObject>>() {
            @Override
            public void onChanged(List<WorkshopObject> workshopObjects) {
                workshops = workshopObjects;
                adapter.setResult(workshopObjects);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_workshop,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.tp_workshop_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_workshop_add_button);
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
