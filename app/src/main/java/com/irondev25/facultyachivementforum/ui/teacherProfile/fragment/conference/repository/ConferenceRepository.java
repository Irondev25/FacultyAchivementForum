package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConferenceRepository {
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<List<ConferenceObject>> conferenceGetLiveObject;

    private static final String TAG = "ConferenceRepository";

    public ConferenceRepository() {
        conferenceGetLiveObject = new MutableLiveData<>();

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

    public void getProfileConferences(String token) {
        String auth = "JWT "+token;
        apiInterface.getProfileConferences(auth).enqueue(new Callback<List<ConferenceObject>>() {
            @Override
            public void onResponse(Call<List<ConferenceObject>> call, Response<List<ConferenceObject>> response) {
                if(response.body() != null){
                    conferenceGetLiveObject.postValue(response.body());
                }
                else{
                    Log.d(TAG, "onResponse: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ConferenceObject>> call, Throwable t) {
                Log.d(TAG, "onResponse: "+t.getMessage());
            }
        });
    }

    public LiveData<List<ConferenceObject>> getProfileConferencesLiveData() {
        return conferenceGetLiveObject;
    }
}


