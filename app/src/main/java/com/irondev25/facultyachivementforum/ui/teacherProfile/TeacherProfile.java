package com.irondev25.facultyachivementforum.ui.teacherProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.irondev25.facultyachivementforum.PreferenceVariables;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.login.LoginActivity;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.ProfileAward;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.ProfileConference;

import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.home.HomeFragment;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.ProfileJournal;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.ProfileWorkshop;
import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.viewModel.BasicProfileViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProfileUpdate.OnFragmentInteractionListener{
    private BasicProfileViewModel viewModel;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView navUsername;
    private CircleImageView imageView;

//    private List<AwardObject> awards;
//    private List<ConferenceObject> conferences;
//    private List<JournalObject> journals;
//    private List<WorkshopObject> workshops;

    private static final String TAG = "TeacherProfile";

    private String username,token,password,profile,profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        setPreferenceVariable();
        fetchData();

        toolbar = findViewById(R.id.profile_toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_hamberger_menu);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.teacher_profile_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.nav_header_username);
        imageView = headerView.findViewById(R.id.nav_header_profile_pic);
        navUsername.setText(username);
        drawerLayout = findViewById(R.id.profile_activity);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        toolbar.setNavigationIcon(R.drawable.hamber_menu);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_hamberger_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState == null){
            getSupportActionBar().setTitle("My Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment, new HomeFragment(username)).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            finishAffinity();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                Log.d(TAG, "onNavigationItemSelected: nav_profile");
                navigationView.setCheckedItem(R.id.nav_profile);
                getSupportActionBar().setTitle("Edit Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment, new ProfileUpdate(token)).commit();
                break;
            case R.id.nav_home:
                navigationView.setCheckedItem(R.id.nav_home);
                getSupportActionBar().setTitle("My Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment, new HomeFragment(username)).commit();
                break;
            case R.id.nav_logout:
                clearPreverenceVariable();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
            case R.id.nav_awards_menu:
                navigationView.setCheckedItem(R.id.nav_awards_menu);
                getSupportActionBar().setTitle("My Awards");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment, new ProfileAward(token)).commit();
                break;
            case R.id.nav_conference_menu:
                navigationView.setCheckedItem(R.id.nav_conference_menu);
                getSupportActionBar().setTitle("My Conferences");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                        new ProfileConference(token)).commit();
                break;
            case R.id.nav_journal_menu:
                navigationView.setCheckedItem(R.id.nav_journal_menu);
                getSupportActionBar().setTitle("My Journals");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                        new ProfileJournal(token)).commit();
                break;
            case R.id.nav_workshop_menu:
                navigationView.setCheckedItem(R.id.nav_workshop_menu);
                getSupportActionBar().setTitle("My Workshop");
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                        new ProfileWorkshop(token)).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setPreferenceVariable(){
        SharedPreferences preferences = getSharedPreferences(PreferenceVariables.MyPREFERENCES, Context.MODE_PRIVATE);
        username = preferences.getString(PreferenceVariables.USERNAME,null);
        token = preferences.getString(PreferenceVariables.TOKEN,null);
        password = preferences.getString(PreferenceVariables.PASSWORD,null);
    }

    private void clearPreverenceVariable(){
        SharedPreferences preferences = getSharedPreferences(PreferenceVariables.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    private void fetchData(){
        viewModel = ViewModelProviders.of(this).get(BasicProfileViewModel.class);
        viewModel.init();
        viewModel.getBasicProfile(username);
        viewModel.getLiveData().observe(this, new Observer<BasicProfileObject>() {
            @Override
            public void onChanged(BasicProfileObject basicProfileObject) {
//                navUsername.setText(basicProfileObject.getUsername());
//                awards = basicProfileObject.getAwardSet();
//                conferences = basicProfileObject.getConferenceSets();
//                journals = basicProfileObject.getJournalSets();
//                workshops = basicProfileObject.getWorkshopSets();
                Glide.with(getApplicationContext()).load(basicProfileObject.getProfilePic()).into(imageView);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ProfileAward.ADD_AWARD) {
            Log.d(TAG, "onNavigationItemSelected: nav_award_menu");
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment, new ProfileAward(token)).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}