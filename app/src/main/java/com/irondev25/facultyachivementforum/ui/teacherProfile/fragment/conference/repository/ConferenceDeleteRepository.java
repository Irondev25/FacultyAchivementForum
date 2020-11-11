package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConferenceDeleteRepository {
    private static final String TAG = "ConferenceDeleteReposit";
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<ResponseBody> liveData;

    public ConferenceDeleteRepository() {
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

    public void deleteProfileConference(String token, String url) {
        String auth = "JWT "+token;
        apiInterface.deleteProfileConference(auth,url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 204) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: object delete failed");
            }
        });
    }

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
}
