package com.irondev25.facultyachivementforum.ui.addAchivement.award.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAwardRepository implements Callback<AwardObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private static final String TAG = "AddAwardRepository";
    private MutableLiveData<AwardObject> liveData;
    private ApiInterface apiInterface;

    public AddAwardRepository() {
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

    public void createProfileAward(String token,
                                   String awardTitle, String awardDate,
                                   String awardDetail,
                                   MultipartBody.Part certificate) {
        String auth = "JWT "+token;
        RequestBody awardTitleRequestBody = RequestBody.create(MultipartBody.FORM,awardTitle);
        RequestBody awardDateRequestBody = RequestBody.create(MultipartBody.FORM,awardDate);
        RequestBody awardDetailRequestBody = RequestBody.create(MultipartBody.FORM,awardDetail);
        apiInterface.createProfileAward(auth,awardTitleRequestBody,
                awardDateRequestBody,awardDetailRequestBody,certificate).enqueue(this);
    }

    public LiveData<AwardObject> getLiveData(){
        return liveData;
    }

    @Override
    public void onResponse(Call<AwardObject> call, Response<AwardObject> response) {
        Log.d(TAG, "onResponse: got response" + response.code());
        if(response.body() != null) {
            liveData.postValue(response.body());
        }
        else{
            AwardObject award = new AwardObject();
            award.setError(new Throwable("HTTPResponse: " + response.code()));
            liveData.postValue(award);
        }
    }

    @Override
    public void onFailure(Call<AwardObject> call, Throwable t) {
        AwardObject award = new AwardObject();
        award.setError(t);
        liveData.postValue(award);
    }
}
