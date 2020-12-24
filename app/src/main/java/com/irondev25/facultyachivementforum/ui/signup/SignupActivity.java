package com.irondev25.facultyachivementforum.ui.signup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.login.LoginActivity;
import com.irondev25.facultyachivementforum.ui.signup.pojo.TeacherSignUp;
import com.irondev25.facultyachivementforum.ui.signup.viewModel.SignUpViewModel;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignupActivity extends AppCompatActivity {
    private SignUpViewModel viewModel;
    private ProgressDialog progressDialog;

    CircleImageView profilePic;
    FloatingActionButton addPic;
    TextInputLayout usernameEditText;
    TextInputLayout emailEditText;
    TextInputLayout firstNameEditText;
    TextInputLayout lastNameEditText;
    TextInputLayout passwordEditText;
    TextInputLayout confirmPasswordEditText;
    AutoCompleteTextView genderOption;
    AutoCompleteTextView departmentOption;
    Button submitButton;

    MultipartBody.Part file = null;

    private HashMap<String, String> gender;
    private ArrayList<String> genderList;

    private HashMap<String,Integer> deptMap;
    private ArrayList<String> deptList;

    String username,email,firstname,lastname,password1,password2,sex=null,file_name;
    Integer dept=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setProgressBar();

        gender = GlobalVars.getGenderMap();
        genderList = GlobalVars.getGenderList();

        deptMap = GlobalVars.getDeptMap();
        deptList = GlobalVars.getDeptList();

        init();


        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        viewModel.init();
        viewModel.getSignUpResponse().observe(this, new Observer<TeacherSignUp>() {
            @Override
            public void onChanged(TeacherSignUp teacherSignUp) {
                progressDialog.dismiss();
                if(teacherSignUp!=null){
                    if(teacherSignUp.getIsError()){
                        Toast.makeText(getApplicationContext(), teacherSignUp.getT().getMessage(), Toast.LENGTH_SHORT).show();
                        if(teacherSignUp.getUsername()!=null){
//                            Toast.makeText(getContext(), "username in use", Toast.LENGTH_SHORT).show();
                            usernameEditText.setError(teacherSignUp.getUsername());
                        }
                        if(teacherSignUp.getEmail()!=null){
//                            Toast.makeText(getContext(), "email in use", Toast.LENGTH_SHORT).show();
                            emailEditText.setError(teacherSignUp.getEmail());
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Account Successfully created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in signup",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setupSpinners(){
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item, deptList);
        departmentOption.setAdapter(deptAdapter);
//        departmentOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                dept = deptMap.get(deptList.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        departmentOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dept = deptMap.get(deptList.get(position));
            }
        });

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,genderList);
        genderOption.setAdapter(genderAdapter);
//        genderOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sex = gender.get(genderList.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        genderOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sex = gender.get(genderList.get(position));
            }
        });
    }

    private void setupAddPicButton(){
        addPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("REST", "onClick: "+addPic);
                ImagePicker.Companion.with(SignupActivity.this)
                        .maxResultSize(480,480).start(3006);
            }
        });
    }

    private void setupButton(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(usernameEditText.getEditText().getText().toString())){
                    username = usernameEditText.getEditText().getText().toString();
                }
                else{
                    usernameEditText.setError("Invalid Username");
                    return;
                }
                String emailTemp = emailEditText.getEditText().getText().toString();
                if(!TextUtils.isEmpty(emailTemp)){
                    if(GlobalVars.validateEmail(emailTemp)){
                        email = emailEditText.getEditText().getText().toString();
                    }
                    else{
                        emailEditText.setError("Invalid Email");
                        return;
                    }
                }
                else{
                    emailEditText.setError("Invalid Email");
                    return;
                }
                if(!TextUtils.isEmpty(firstNameEditText.getEditText().getText().toString())){
                    firstname = firstNameEditText.getEditText().getText().toString();
                }
                else{
                    firstNameEditText.setError("Invalid First Name");
                    return;
                }

                if(!TextUtils.isEmpty(lastNameEditText.getEditText().getText().toString())){
                    lastname = lastNameEditText.getEditText().getText().toString();
                }
                else{
                    lastNameEditText.setError("Invalid Last Name");
                    return;
                }

                String pass1 = passwordEditText.getEditText().getText().toString();
                String pass2 = confirmPasswordEditText.getEditText().getText().toString();
                if(!TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2)){
                    if(pass1.equals(pass2)){
                        password1 = pass1;
                        password2 = pass2;
                    }
                    else{
                        confirmPasswordEditText.setError("Password doesn't match");
                        return;
                    }
                }
                else{
                    passwordEditText.setError("Invalid Password");
                    confirmPasswordEditText.setError("Invalid Password");
                    return;
                }

                if(sex == null){
                    Toast.makeText(getApplicationContext(), "Gender Not Selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(dept == null){
                    Toast.makeText(getApplicationContext(), "Department Not Selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUpRequest();
            }
        });
    }

    private void init(){
        profilePic = findViewById(R.id.profile_image);
        addPic = findViewById(R.id.pick_image);
        usernameEditText = findViewById(R.id.signup_username);
        emailEditText = findViewById(R.id.signup_email);
        firstNameEditText = findViewById(R.id.signup_first_name);
        lastNameEditText = findViewById(R.id.signup_last_name);
        passwordEditText = findViewById(R.id.signup_password);
        confirmPasswordEditText = findViewById(R.id.signup_confirm_password);
        genderOption = findViewById(R.id.signup_sex);
        departmentOption = findViewById(R.id.signup_department);
        submitButton = findViewById(R.id.signup_button);

        setupSpinners();
        setupAddPicButton();
        setupButton();
    }

    private void signUpRequest(){
        /*
         *
         * */
        progressDialog.show();
        file_name = "attachment; filename="+file_name;
        viewModel.signUpRequest(this.username,this.email,this.firstname,this.lastname,this.sex,this.dept,this.password1,this.password2,this.file,file_name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3006 && resultCode == Activity.RESULT_OK && data!=null){
            Log.d("REST", "onActivityResult: "+"got res from image picker");
            Uri fileUri = data.getData();
            File originalFile = ImagePicker.Companion.getFile(data);
            String filePath = ImagePicker.Companion.getFilePath(data);
            Path path = Paths.get(filePath);
            Path fileName = path.getFileName();
            file_name = fileName.toString();
            RequestBody filePart = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile);

            file = MultipartBody.Part.createFormData("profile_pic", originalFile.getName(), filePart);
            profilePic.setImageURI(fileUri);
        }
    }

    public void setProgressBar() {
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}