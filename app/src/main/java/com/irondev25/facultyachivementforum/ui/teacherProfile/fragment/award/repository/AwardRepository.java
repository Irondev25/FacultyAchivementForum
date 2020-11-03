package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AwardRepository {
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<List<AwardObject>> awardGetLiveObject;

    private static final String TAG = "AwardRepository";

    public AwardRepository() {
        awardGetLiveObject = new MutableLiveData<List<AwardObject>>();

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


    public void getProfileAwards(String token) {
        String auth = "JWT "+token;
        apiInterface.getProfileAwards(auth).enqueue(new Callback<List<AwardObject>>() {
            @Override
            public void onResponse(Call<List<AwardObject>> call, Response<List<AwardObject>> response) {
                if(response.body()!=null){
                    awardGetLiveObject.postValue(response.body());
                }
                else{
                    Log.d(TAG, "onFailure: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<AwardObject>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public LiveData<List<AwardObject>> getProfileAwardsLiveData() {
        return awardGetLiveObject;
    }
}
