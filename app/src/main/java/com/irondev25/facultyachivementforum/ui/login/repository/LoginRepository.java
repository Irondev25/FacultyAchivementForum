package com.irondev25.facultyachivementforum.ui.login.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.login.pojo.TeacherSignIn;
import com.irondev25.facultyachivementforum.ui.login.pojo.TokenPojo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRepository implements GlobalVars {
    private static final String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<TokenPojo> tokenMutableLiveData;

    public LoginRepository(){
        tokenMutableLiveData = new MutableLiveData<>();

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

    public void getToken(@NonNull String username, @NonNull String password){

        apiInterface.loginRequest(new TeacherSignIn(username, password)).enqueue(new Callback<TokenPojo>() {
            @Override
            public void onResponse(Call<TokenPojo> call, Response<TokenPojo> response) {
                if(response.code() == 400){
                    tokenMutableLiveData.postValue(new TokenPojo(new Exception("Invalid username or password")));
                }
                if(response.body()!=null){
                    TokenPojo token = response.body();
                    token.setUsername(username);
                    token.setPassword(password);
                    tokenMutableLiveData.postValue(token);
                }
            }

            @Override
            public void onFailure(Call<TokenPojo> call, Throwable t) {
                tokenMutableLiveData.postValue(new TokenPojo(t));
            }
        });
    }

    public LiveData<TokenPojo> getTokenLiveData(){
        return tokenMutableLiveData;
    }
}
