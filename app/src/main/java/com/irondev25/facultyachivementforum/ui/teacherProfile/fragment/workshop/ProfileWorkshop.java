package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop;

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
import com.irondev25.facultyachivementforum.ui.addAchivement.workshop.AddWorkshop;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.adapter.ProfileWorkshopAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.viewModel.WorkshopDeleteViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.viewModel.WorkshopViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshopEdit.ProfileWorkshopEdit;

import java.util.List;

import okhttp3.ResponseBody;

public class ProfileWorkshop extends Fragment implements ProfileWorkshopAdapter.MyCardButtons {
    private static final String TAG = "ProfileWorkshop";
    private static final int ADD_WORKSHOP = 126;
    private WorkshopViewModel viewModel;
    private WorkshopDeleteViewModel deleteViewModel;

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
        deleteViewModel = ViewModelProviders.of(this).get(WorkshopDeleteViewModel.class);

        viewModel.init();
        deleteViewModel.init();

        viewModel.getProfileWorkshop(token);
        viewModel.getProfileWorkshopLiveData().observe(this, new Observer<List<WorkshopObject>>() {
            @Override
            public void onChanged(List<WorkshopObject> workshopObjects) {
                workshops = workshopObjects;
                adapter.setResult(workshopObjects);
            }
        });

        deleteViewModel.getLiveData().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                    Toast.makeText(getContext(), "Workshop Deleted", Toast.LENGTH_SHORT).show();
                    viewModel.getProfileWorkshop(token);
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
                Intent intent = new Intent(getActivity(), AddWorkshop.class);
                intent.putExtra("token",token);
                startActivityForResult(intent,ADD_WORKSHOP);
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
    public void onEditClickButton(WorkshopObject workshop) {
        getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                new ProfileWorkshopEdit(workshop,token)).commit();
    }

    @Override
    public void onDeleteClickButton(WorkshopObject workshop) {
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
                deleteViewModel.deleteProfileWorkshop(token,workshop.getUrl());
            }
        });
        alertDialog.setTitle("Delete Workshop");
        alertDialog.setMessage("Are you sure?");
        alertDialog.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_WORKSHOP) {
            viewModel.getProfileWorkshop(token);
        }
    }
}
