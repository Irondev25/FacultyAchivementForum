package com.irondev25.facultyachivementforum.ui.teacherProfile.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicProfileRepository implements Callback<BasicProfileObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private MutableLiveData<BasicProfileObject> profileObjectMutableLiveData;
    private ApiInterface apiInterface;

    public BasicProfileRepository() {
        profileObjectMutableLiveData = new MutableLiveData<>();

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

    public void getProfile(String username){
        String url = "/api/teacher/"+username;
        apiInterface.getBasicProfile(url).enqueue(this);
    }

    public LiveData<BasicProfileObject> getLiveData(){
        return profileObjectMutableLiveData;
    }

    @Override
    public void onResponse(Call<BasicProfileObject> call, Response<BasicProfileObject> response) {
        if(response.body()!=null){
            profileObjectMutableLiveData.postValue(response.body());
        }
        else{
            BasicProfileObject profileObject = new BasicProfileObject();
            profileObject.setError(new Throwable("HTTPResponse: " + response.code()));
            profileObjectMutableLiveData.postValue(profileObject);
        }
    }

    @Override
    public void onFailure(Call<BasicProfileObject> call, Throwable t) {
        BasicProfileObject profileObject = new BasicProfileObject();
        profileObject.setError(t);
        profileObjectMutableLiveData.postValue(profileObject);
    }
}
