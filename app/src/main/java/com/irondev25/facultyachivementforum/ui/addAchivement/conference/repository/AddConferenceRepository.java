package com.irondev25.facultyachivementforum.ui.addAchivement.conference.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddConferenceRepository implements Callback<ConferenceObject> {
    private static final String API_URL = GlobalVars.API_URL;
    private static final String TAG = "AddConferenceRepository";
    private MutableLiveData<ConferenceObject> liveData;
    private ApiInterface apiInterface;

    public AddConferenceRepository() {
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

    public void createProfileConference(String token,
                                        String conferenceName,
                                        String paperTitle,
                                        String conferenceDate,
                                        String conferenceType,
                                        MultipartBody.Part certificate) {
        String auth = "JWT "+token;
        RequestBody conferenceNameRequestBody =
                RequestBody.create(MultipartBody.FORM,conferenceName);
        RequestBody paperTitleRequestBody =
                RequestBody.create(MultipartBody.FORM,paperTitle);
        RequestBody conferenceDateRequestBody =
                RequestBody.create(MultipartBody.FORM,conferenceDate);
        RequestBody conferenceTypeRequestBody =
                RequestBody.create(MultipartBody.FORM,conferenceType);
        apiInterface.createProfileConference(auth,conferenceNameRequestBody,paperTitleRequestBody,
                conferenceDateRequestBody,certificate,conferenceTypeRequestBody).enqueue(this);
    }

    public LiveData<ConferenceObject> getLiveData() {
        return liveData;
    }

    @Override
    public void onResponse(Call<ConferenceObject> call, Response<ConferenceObject> response) {
        if(response.body()!=null) {
            liveData.postValue(response.body());
        }
        else{
            ConferenceObject conference = new ConferenceObject();
            conference.setError(new Throwable("HTTPResponse: " + response.code()));
            liveData.postValue(conference);
        }
    }

    @Override
    public void onFailure(Call<ConferenceObject> call, Throwable t) {
        ConferenceObject conference = new ConferenceObject();
        conference.setError(t);
        liveData.postValue(conference);
    }
}
