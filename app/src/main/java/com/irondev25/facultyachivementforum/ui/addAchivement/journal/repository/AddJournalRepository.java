package com.irondev25.facultyachivementforum.ui.addAchivement.journal.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddJournalRepository implements Callback<JournalObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private static final String TAG = "AddJournalRepository";
    private MutableLiveData<JournalObject> liveData;
    private ApiInterface apiInterface;

    public AddJournalRepository() {
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

    public void createProfileJournal(String token, String journalTitle,
                                     String paperTitle, String journalDate,
                                     String journalType, String journalImpactFactor,
                                     MultipartBody.Part certificate) {
        String auth = "JWT "+token;
        RequestBody journalNameRequestBody = RequestBody.create(MultipartBody.FORM,journalTitle);
        RequestBody paperTitleRequestBody =  RequestBody.create(MultipartBody.FORM,paperTitle);
        RequestBody journalDateRequestBody =  RequestBody.create(MultipartBody.FORM,journalDate);
        RequestBody journalTypeRequestBody =  RequestBody.create(MultipartBody.FORM,journalType);
        RequestBody journalImactFactorRequestBody = RequestBody.create(MultipartBody.FORM,journalImpactFactor);
        apiInterface.createProfileJournal(auth,journalNameRequestBody,paperTitleRequestBody,
                journalDateRequestBody,journalTypeRequestBody,journalImactFactorRequestBody,
                certificate).enqueue(this);
    }

    public LiveData<JournalObject> getLiveData() {
        return liveData;
    }

    @Override
    public void onResponse(Call<JournalObject> call, Response<JournalObject> response) {
        if(response.code() == 200){
            if(response.body()!=null) {
                liveData.postValue(response.body());
            }
        }
        else{
            JournalObject journal = new JournalObject();
            journal.setError(new Throwable("HTTPResponse: "+response.code()));
            liveData.postValue(journal);
        }
    }

    @Override
    public void onFailure(Call<JournalObject> call, Throwable t) {
        JournalObject journal = new JournalObject();
        journal.setError(t);
        liveData.postValue(journal);
    }
}
