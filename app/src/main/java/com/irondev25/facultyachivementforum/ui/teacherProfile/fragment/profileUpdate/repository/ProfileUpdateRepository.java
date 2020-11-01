package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.repository;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileUpdateRepository implements Callback<ProfileObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private MutableLiveData<ProfileObject> liveData;
    private ApiInterface apiInterface;

    public ProfileUpdateRepository(){
        liveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);
    }

    public void teacherProfileUpdate(String username, String email,String firstName, String middleName, String lastName,
                                     String dob, String doj, String sex, Integer department,
                                     String mobileNum,
                                     MultipartBody.Part profilePic,String token){
        String auth = "JWT "+token;
        RequestBody usernameRequestBody = RequestBody.create(MultipartBody.FORM,username);
        RequestBody emailRequestBody = RequestBody.create(MultipartBody.FORM,email);
        RequestBody firstNameRequestBody = RequestBody.create(MultipartBody.FORM,firstName);
        RequestBody middleNameRequestBody = RequestBody.create(MultipartBody.FORM,middleName);
        RequestBody lastNameRequestBody = RequestBody.create(MultipartBody.FORM,lastName);
        RequestBody dobRequestBody = RequestBody.create(MultipartBody.FORM,dob);
        RequestBody dojRequestBody = RequestBody.create(MultipartBody.FORM,doj);
        RequestBody sexRequestBody = RequestBody.create(MultipartBody.FORM,sex);
        RequestBody departmentRequestBody = RequestBody.create(MultipartBody.FORM,department.toString());
        RequestBody mobileNumberRequestBody = RequestBody.create(MultipartBody.FORM,mobileNum);
        apiInterface.teacherProfileUpdate(usernameRequestBody,emailRequestBody,firstNameRequestBody,
                middleNameRequestBody,lastNameRequestBody,dobRequestBody,dojRequestBody,sexRequestBody,
                departmentRequestBody,mobileNumberRequestBody,profilePic,auth
        ).enqueue(this);
    }

    public LiveData<ProfileObject> getLiveData() {
        return liveData;
    }

    @Override
    public void onResponse(Call<ProfileObject> call, Response<ProfileObject> response) {
        if(response.body()!=null){
            liveData.postValue(response.body());
        }
        else{
            ProfileObject profileObject = new ProfileObject();
            profileObject.setT(new Throwable("HTTPResponse: " + response.code()));
            liveData.postValue(profileObject);
        }
    }

    @Override
    public void onFailure(Call<ProfileObject> call, Throwable t) {
        ProfileObject profileObject = new ProfileObject();
        profileObject.setT(t);
        liveData.postValue(profileObject);
    }
}
