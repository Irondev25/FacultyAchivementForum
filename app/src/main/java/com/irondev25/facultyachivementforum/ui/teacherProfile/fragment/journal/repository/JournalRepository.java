package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalRepository {
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<List<JournalObject>> journalGetLiveObject;

    private static final String TAG = "JournalRepository";

    public JournalRepository() {
        journalGetLiveObject = new MutableLiveData<>();

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

    public void getProfileJournal(String token) {
        String auth = "JWT "+ token;

        apiInterface.getProfileJournal(auth).enqueue(new Callback<List<JournalObject>>() {
            @Override
            public void onResponse(Call<List<JournalObject>> call, Response<List<JournalObject>> response) {
                if(response.body()!=null){
                    journalGetLiveObject.postValue(response.body());
                }
                else{
                    Log.d(TAG, "onFailure: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<JournalObject>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public LiveData<List<JournalObject>> getProfileJournalLiveData() {
        return journalGetLiveObject;
    }
}
