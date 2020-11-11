package com.irondev25.facultyachivementforum.ui.addAchivement.workshop.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddWorkshopRepository implements Callback<WorkshopObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private static final String TAG = "AddWorkshopRepository";
    private MutableLiveData<WorkshopObject> liveData;
    private ApiInterface apiInterface;

    public AddWorkshopRepository() {
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

    public void createProfileWorkshop(String token,
                                      String workshopTopic, String workshopLocation,
                                      String workshopDate, String workshopType,
                                      MultipartBody.Part certificate) {
        String auth = "JWT "+token;
        RequestBody workshopTopicRequestBody = RequestBody.create(MultipartBody.FORM,workshopTopic);
        RequestBody workshopLocationRequestBody = RequestBody.create(MultipartBody.FORM,workshopLocation);
        RequestBody workshopDateRequestBody = RequestBody.create(MultipartBody.FORM,workshopDate);
        RequestBody workshopTypeRequestBody = RequestBody.create(MultipartBody.FORM,workshopType);
        apiInterface.createProfileWorkshop(auth,workshopTopicRequestBody,workshopDateRequestBody,workshopTypeRequestBody,workshopLocationRequestBody,certificate)
                .enqueue(this);
    }

    public LiveData<WorkshopObject> getLiveData() {
        return liveData;
    }

    @Override
    public void onResponse(Call<WorkshopObject> call, Response<WorkshopObject> response) {
        if(response.code() == 200){
            if(response.body()!=null) {
                liveData.postValue(response.body());
            }
        }
        else{
            WorkshopObject workshop = new WorkshopObject();
            workshop.setError(new Throwable("HTTPResponse: "+response.code()));
            liveData.postValue(workshop);
        }
    }

    @Override
    public void onFailure(Call<WorkshopObject> call, Throwable t) {
        WorkshopObject workshop = new WorkshopObject();
        workshop.setError(t);
        liveData.postValue(workshop);
    }
}
