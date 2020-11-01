package com.irondev25.facultyachivementforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.irondev25.facultyachivementforum.ui.login.LoginActivity;
import com.irondev25.facultyachivementforum.ui.teacherProfile.TeacherProfile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalVars.genVals();
        SharedPreferences preferences = getSharedPreferences(PreferenceVariables.MyPREFERENCES, Context.MODE_PRIVATE);
        if(preferences.getString(PreferenceVariables.USERNAME,PreferenceVariables.ANON) != PreferenceVariables.ANON){
            Intent intent = new Intent(this, TeacherProfile.class);
            startActivity(intent);
        }
        else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}