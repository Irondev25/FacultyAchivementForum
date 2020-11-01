package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherDetailPublicRepository  implements Callback<TeacherDetailPublic>{
    private static final String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;

    private MutableLiveData<TeacherDetailPublic> teacherDetailPublicMutableLiveData;

    public TeacherDetailPublicRepository(){
        teacherDetailPublicMutableLiveData = new MutableLiveData<>();

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

    public void getTeacherPublicDate(String url){
        apiInterface.teacherPublicDetail(url).enqueue(this);
    }

    public LiveData<TeacherDetailPublic> getTeacherPublicLiveDate(){
        return teacherDetailPublicMutableLiveData;
    }

    @Override
    public void onResponse(Call<TeacherDetailPublic> call, Response<TeacherDetailPublic> response) {
        if(response.body()!=null){
            teacherDetailPublicMutableLiveData.postValue(response.body());
        }
    }

    @Override
    public void onFailure(Call<TeacherDetailPublic> call, Throwable t) {
        teacherDetailPublicMutableLiveData.postValue(null);
    }
}
