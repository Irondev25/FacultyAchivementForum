package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.AwardPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.ConferencePublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.JournalPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.WorkshopPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.viewModel.TeacherPublicDataViewModel;
import com.irondev25.facultyachivementforum.ui.signup.SignupActivity;

public class TeacherAchivements extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "TeacherAchivements";
    private ProgressDialog progressDialog;

    private TeacherPublicDataViewModel viewModel;
    public TeacherDetailPublic teacherDetailPublic;
    String url;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_achivements);
        setProgressBar();

        String fullName = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("profile_url");
//        getSupportActionBar().setTitle(fullName);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String new_url;
        if(url.matches("(.*)8000(.*)")){
            new_url = url.split("8000",2)[1];
        }
        else{
            new_url = "/" + url.split("/",4)[3];
        }

        bottomNavigationView = findViewById(R.id.public_teacher_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewModel = ViewModelProviders.of(this).get(TeacherPublicDataViewModel.class);
        viewModel.init();
        viewModel.getTeacherList(new_url);
        progressDialog.show();
        viewModel.getTeacherDetailPublicLiveData().observe(this, new Observer<TeacherDetailPublic>() {
            @Override
            public void onChanged(TeacherDetailPublic tdp) {
                progressDialog.dismiss();
                teacherDetailPublic = tdp;
                Log.d(TAG, "onChanged: "+teacherDetailPublic.getFirstName());
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_detail_frag_cont,new AwardPublic(teacherDetailPublic)).commit();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if(teacherDetailPublic == null) return false;
        switch (item.getItemId()){
            case R.id.nav_awards:
                selectedFragment = new AwardPublic(teacherDetailPublic);
                break;
            case R.id.nav_conference:
                selectedFragment = new ConferencePublic(teacherDetailPublic);
                break;
            case R.id.nav_journal:
                selectedFragment = new JournalPublic(teacherDetailPublic);
                break;
            case R.id.nav_workshop:
                selectedFragment = new WorkshopPublic(teacherDetailPublic);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.teacher_detail_frag_cont,selectedFragment).commit();
        return true;
    }

    public void setProgressBar() {
        progressDialog = new ProgressDialog(TeacherAchivements.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}