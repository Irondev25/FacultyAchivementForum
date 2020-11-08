package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.AwardPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.WorkshopPublic;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.adapter.ProfileAwardAdapter;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.viewModel.AwardViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit.ProfileAwardEdit;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.ProfileWorkshop;
import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;

import java.util.List;

public class ProfileAward extends Fragment implements ProfileAwardAdapter.MyCardButtons {
    private static final String TAG = "ProfileAward";
    private static String FRAMETAG;
    private AwardViewModel viewModel;
    private ProfileAwardAdapter adapter;
    private List<AwardObject> awards;

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
        viewModel.init();
        viewModel.getProfileAwards(token);

        viewModel.getProfileAwardsLiveData().observe(this, new Observer<List<AwardObject>>() {
            @Override
            public void onChanged(List<AwardObject> awardObjects) {
                awards = awardObjects;
                adapter.setResult(awardObjects);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_awards,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.tp_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.profile_add_button);
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
    public void onEditClickButton(AwardObject awardObject) {
        getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileAwardEdit(awardObject,token)).commit();
//        replaceFragment(this,awardObject);
    }

    @Override
    public void onDeleteClickButton(AwardObject awardObject) {
        Log.d(TAG, "onDeleteClickButton: delete button clicked");
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
}
