package com.irondev25.facultyachivementforum.ui.signup.viewModel;


import android.app.Application;
import android.graphics.Bitmap;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.signup.pojo.TeacherSignUp;
import com.irondev25.facultyachivementforum.ui.signup.repository.SignUpRepository;

import okhttp3.MultipartBody;

public class SignUpViewModel extends AndroidViewModel {

    /*
    * private LoginRepository loginRepository;
    private LiveData<TokenPojo> tokenLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        loginRepository = new LoginRepository();
        tokenLiveData = loginRepository.getTokenLiveData();
    }

    public void loginRequest(@NonNull String username, @NonNull String password){
        loginRepository.getToken(username,password);
    }

    public LiveData<TokenPojo> getTokenLiveData(){
        return tokenLiveData;
    }
    * */

    private SignUpRepository signUpRepository;
    private LiveData<TeacherSignUp> teacherSignUpLiveData;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        signUpRepository = new SignUpRepository();
        teacherSignUpLiveData = signUpRepository.getSignUpResponse();
    }

    public void signUpRequest(@NonNull String username, @NonNull String email,
                              @NonNull String first_name, @NonNull String last_name,
                              @NonNull String sex, @NonNull Integer dept, @Nullable String password,
                              @NonNull String password2, MultipartBody.Part profilePic,String file_name){
        signUpRepository.createAccount(username, email, first_name, last_name, sex, dept, password, password2, profilePic,file_name);
    }

    public LiveData<TeacherSignUp> getSignUpResponse(){
        return  teacherSignUpLiveData;
    }
}

