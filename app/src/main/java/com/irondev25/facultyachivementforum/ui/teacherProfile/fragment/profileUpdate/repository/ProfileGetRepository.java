package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileGetRepository implements Callback<ProfileObject> {
    private static final String TAG = "ProfileGetRepository";
    private static final String API_URL = GlobalVars.API_URL;
    private MutableLiveData<ProfileObject> profileObjectMutableLiveData;
    private ApiInterface apiInterface;

    public ProfileGetRepository(){
        profileObjectMutableLiveData  = new MutableLiveData<>();

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

    public void getProfile(String token){
        String auth = "JWT " + token;
        Log.d(TAG, auth);
        apiInterface.teacherProfileRetrive(auth).enqueue(this);
    }

    public LiveData<ProfileObject> getProfileMutableData() {
        return profileObjectMutableLiveData;
    }

    @Override
    public void onResponse(Call<ProfileObject> call, Response<ProfileObject> response) {
        if(response.body()!=null) {
            profileObjectMutableLiveData.postValue(response.body());
        }
        else{
            ProfileObject profileObject = new ProfileObject();
            profileObject.setT(new Throwable("HTTPResponse: " + response.code()));
            profileObjectMutableLiveData.postValue(profileObject);
        }
    }

    @Override
    public void onFailure(Call<ProfileObject> call, Throwable t) {
        ProfileObject profileObject = new ProfileObject();
        profileObject.setT(t);
        profileObjectMutableLiveData.postValue(profileObject);
    }
}
