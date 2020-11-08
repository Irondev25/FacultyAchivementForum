package com.irondev25.facultyachivementforum.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.PreferenceVariables;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.browseAchivement.AchivementExplorer;
import com.irondev25.facultyachivementforum.ui.login.pojo.TokenPojo;
import com.irondev25.facultyachivementforum.ui.login.viewModel.LoginViewModel;
import com.irondev25.facultyachivementforum.ui.signup.SignupActivity;
import com.irondev25.facultyachivementforum.ui.teacherProfile.TeacherProfile;

public class LoginActivity extends AppCompatActivity implements PreferenceVariables {
    private LoginViewModel viewModel;
    public SharedPreferences sharedPreferences;

    private String passwordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getActionBar().setTitle("Login");

//        checkPermission();
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT >= 23){
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,0);
//            requestPermissions(permissions,0);
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,0);
        }


        initFields();

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.init();
        viewModel.getTokenLiveData().observe(this, new Observer<TokenPojo>() {
            @Override
            public void onChanged(TokenPojo token) {
                if(token.getToken() != null){
                    String currUser = sharedPreferences.getString(USERNAME,ANON);
                    if(currUser.equals(ANON)){
                        saveUser(token);
                        Toast.makeText(getApplicationContext(), token.getUsername() + " successfully logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), TeacherProfile.class));
                    }
                    else if(currUser.equals(token.getUsername())){
                        Toast.makeText(getApplicationContext(), currUser + " already logged in", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), currUser + " already logged in, logout first", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void initFields(){
        TextInputLayout usernameEditText = findViewById(R.id.username);
        TextInputLayout passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button publicBrowse = findViewById(R.id.browse_public);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameVal = usernameEditText.getEditText().getText().toString();
                passwordVal = passwordEditText.getEditText().getText().toString();
                if(usernameVal != null && passwordVal != null){
                    if(usernameVal.length() == 0 || passwordVal.length() == 0){
                        if(usernameVal.length() == 0){
                            usernameEditText.setError("Enter Username");
                        }
                        if(passwordVal.length() == 0){
                            passwordEditText.setError("Enter Password");
                        }
                    }
                    else{
                        performLogin(usernameVal,passwordVal);
                    }

                }
                else{
                    if(usernameVal == null){
                        usernameEditText.setError("Enter username");
                    }
                    if(passwordVal == null){
                        passwordEditText.setError("Enter password");
                    }
                }
            }
        });

        loginButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("PREFS", "onLongClick: "+sharedPreferences.getString(USERNAME,ANON));
                return true;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        publicBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AchivementExplorer.class));
            }
        });
    }

    void saveUser(TokenPojo tokenPojo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME,tokenPojo.getUsername());
        editor.putString(PASSWORD,passwordVal);
        editor.putString(TOKEN,tokenPojo.getToken());
        editor.apply();
    }

    void performLogin(@NonNull String username, @NonNull String password){
        viewModel.loginRequest(username,password);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        int perm = ContextCompat.checkSelfPermission(
                LoginActivity.this,
                permission);
        if (perm == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            LoginActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(LoginActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }
}