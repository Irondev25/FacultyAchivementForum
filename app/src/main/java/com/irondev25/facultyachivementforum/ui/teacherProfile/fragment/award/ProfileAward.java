package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.irondev25.facultyachivementforum.ui.addAchivement.award.AddAward;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.adapter.ProfileAwardAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.viewModel.AwardDeleteViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.viewModel.AwardViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit.ProfileAwardEdit;

import java.util.List;

import okhttp3.ResponseBody;

public class ProfileAward extends Fragment implements ProfileAwardAdapter.MyCardButtons {
    private static final String TAG = "ProfileAward";
    public static final int ADD_AWARD = 123;
    private static String FRAMETAG;


    private SwipeRefreshLayout swipeRefreshLayout;
    private AwardViewModel viewModel;
    private AwardDeleteViewModel deleteViewModel;
    private ProfileAwardAdapter adapter;
    private List<AwardObject> awards;

    ProgressDialog progressBar;

    String token;

    public ProfileAward(String token){
        this.token = token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FRAMETAG = this.getClass().getName();

        adapter = new ProfileAwardAdapter(this);

        viewModel = ViewModelProviders.of(this).get(AwardViewModel.class);
        deleteViewModel = ViewModelProviders.of(this).get(AwardDeleteViewModel.class);
        viewModel.init();
        deleteViewModel.init();
        viewModel.getProfileAwards(token);

        viewModel.getProfileAwardsLiveData().observe(this, new Observer<List<AwardObject>>() {
            @Override
            public void onChanged(List<AwardObject> awardObjects) {
                awards = awardObjects;
                adapter.setResult(awardObjects);
                progressBar.dismiss();
            }
        });

        deleteViewModel.getLiveData().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                Toast.makeText(getContext(), "Award Deleted", Toast.LENGTH_SHORT).show();
                viewModel.getProfileAwards(token);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_awards,container,false);
        swipeRefreshLayout = view.findViewById(R.id.award_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getProfileAwards(token);
                progressBar.show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        setProgressBar(view);
        progressBar.show();
        RecyclerView recyclerView = view.findViewById(R.id.tp_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddAward.class);
                intent.putExtra("token",token);
                startActivityForResult(intent,ADD_AWARD);
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
    public void onEditClickButton(AwardObject awardObject) {
        getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileAwardEdit(awardObject,token)).commit();
//        replaceFragment(this,awardObject);
    }

    @Override
    public void onDeleteClickButton(AwardObject awardObject) {
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
                deleteViewModel.deleteProfileAward(token,awardObject.getUrl());
                progressBar.show();
            }
        });
        alertDialog.setTitle("Delete Workshop");
        alertDialog.setMessage("Are you sure?");
        alertDialog.create().show();
    }

//    private void replaceFragment(Fragment fragment,AwardObject award){
//        String backStackName = FRAMETAG;
//
//        FragmentManager manager = fragment.getParentFragmentManager();
//        boolean fragmentPopped = manager.popBackStackImmediate(backStackName,0);
//
//        if(!fragmentPopped && manager.findFragmentByTag(FRAMETAG) == null) {
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.teacher_profile_fragment, new ProfileAwardEdit(award),);
//            ft.addToBackStack(backStackName);
//            ft.commit();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_AWARD) {
            viewModel.getProfileAwards(token);
        }
    }

    public void setProgressBar(View view) {
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }
}
