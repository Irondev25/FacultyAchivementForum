package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.repository.JournalRepository;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkshopRepository {
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<List<WorkshopObject>> workshopGetLiveObject;
//    private MutableLiveData<ResponseBody> workshopDeleteLiveObject;

    private static final String TAG = "WorkshopRepository";

    public WorkshopRepository() {
        workshopGetLiveObject = new MutableLiveData<>();
//        workshopDeleteLiveObject = new MutableLiveData<>();

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

    public void getProfileWorkshop(String token) {
        String auth = "JWT "+token;

        apiInterface.getProfileWorkshop(auth).enqueue(new Callback<List<WorkshopObject>>() {
            @Override
            public void onResponse(Call<List<WorkshopObject>> call, Response<List<WorkshopObject>> response) {
                if(response.body()!=null){
                    workshopGetLiveObject.postValue(response.body());
                }
                else{
                    Log.d(TAG, "onFailure: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<WorkshopObject>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public LiveData<List<WorkshopObject>> getProfileWorkshopLiveData(){
        return workshopGetLiveObject;
    }
}
