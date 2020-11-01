package com.irondev25.facultyachivementforum.ui.signup.repository;


import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.irondev25.facultyachivementforum.ApiInterface;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.ui.signup.pojo.TeacherSignUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpRepository {
    private String API_URL = GlobalVars.API_URL;
    private ApiInterface apiInterface;
    private MutableLiveData<TeacherSignUp>  teacherSignUpMutableLiveData;

    public SignUpRepository(){
        teacherSignUpMutableLiveData = new MutableLiveData<>();

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

    /*
    * public void getTeacherList(@Nullable Integer dept_id, @Nullable String fName){
        apiInterface.getTeacherList(dept_id,fName).enqueue(new Callback<List<TeacherItem>>() {
            @Override
            public void onResponse(Call<List<TeacherItem>> call, Response<List<TeacherItem>> response) {
                if(response.body()!=null){
                    teacherListLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TeacherItem>> call, Throwable t) {
                teacherListLiveData.postValue(null);
            }
        });
    }

    public LiveData<List<TeacherItem>> getTeacherListResponseLiveData(){
        return teacherListLiveData;
    }
    * */

    public void createAccount(@NonNull String username, @NonNull String email,
                              @NonNull String first_name, @NonNull String last_name,
                              @NonNull String sex, @NonNull Integer dept, @Nullable String password,
                              @NonNull String password2, MultipartBody.Part profilePic, String file_name){

        RequestBody usernameRequestBody = RequestBody.create(MultipartBody.FORM,username);
        RequestBody emailRequestBody = RequestBody.create(MultipartBody.FORM,email);
        RequestBody firstNameRequestBody = RequestBody.create(MultipartBody.FORM,first_name);
        RequestBody lastNameRequestBody = RequestBody.create(MultipartBody.FORM,last_name);
        RequestBody sexRequestBody = RequestBody.create(MultipartBody.FORM,sex);
        RequestBody deptRequstBody = RequestBody.create(MultipartBody.FORM,dept.toString());
        RequestBody password1RequestBody = RequestBody.create(MultipartBody.FORM,password);
        RequestBody password2RequestBody = RequestBody.create(MultipartBody.FORM,password2);


        /*apiInterface.signupRequest(username,email,first_name,null,last_name,null,null,sex,dept,profilePic, password, password2, file_name).enqueue(
                new Callback<TeacherSignUp>() {
                    @Override
                    public void onResponse(Call<TeacherSignUp> call, Response<TeacherSignUp> response) {
                        if(response.code() == 201 &&response.body()!=null){
                            teacherSignUpMutableLiveData.postValue(response.body());
                        }
                        else{
                            TeacherSignUp teacherSignUp = new TeacherSignUp(new Throwable("HTTP_ERROR: "+response.code()),true);
                            teacherSignUpMutableLiveData.postValue(teacherSignUp);

                        }
                    }

                    @Override
                    public void onFailure(Call<TeacherSignUp> call, Throwable t) {
                        teacherSignUpMutableLiveData.postValue(new TeacherSignUp(new Throwable(),true));
                    }
                }
        );*/
        apiInterface.signupRequest(
                usernameRequestBody,emailRequestBody,
                firstNameRequestBody,null,lastNameRequestBody,null,null,sexRequestBody,
                deptRequstBody,profilePic,password1RequestBody,password2RequestBody/*,file_name*/
        ).enqueue(
                new Callback<TeacherSignUp>() {
                    @Override
                    public void onResponse(Call<TeacherSignUp> call, Response<TeacherSignUp> response) {
                        if(response.body()!=null){
                            if(response.code() == 201){
                                TeacherSignUp teacherSignUp = response.body();
                                teacherSignUp.setIsError(false);
                                teacherSignUp.setT(null);
                                teacherSignUpMutableLiveData.postValue(response.body());
                                Log.d("REST", "get response of 201");
                            }
//                            else{
//                                TeacherSignUp teacherSignUp = response.body();
//                                teacherSignUp.setT(new Throwable("HTTP_ERROR: "+response.code()));
//                                teacherSignUp.setIsError(true);
//                                teacherSignUpMutableLiveData.postValue(teacherSignUp);
//                                Log.d("REST", "get response of "+response.code());
//                            }
                        }
                        else if(!response.isSuccessful()) {
                            StringBuilder error = new StringBuilder();
                            try {
                                BufferedReader bufferedReader = null;
                                if (response.errorBody() != null) {
                                    bufferedReader = new BufferedReader(new InputStreamReader(
                                            response.errorBody().byteStream()));

                                    String eLine = null;
                                    while ((eLine = bufferedReader.readLine()) != null) {
                                        error.append(eLine);
                                    }
                                    bufferedReader.close();
                                }

                            } catch (Exception e) {
                                error.append(e.getMessage());
                            }

                            Log.e("Error", error.toString());



                            TeacherSignUp teacherSignUp = new TeacherSignUp(new Throwable("HTTP_ERROR: "+response.code()), true);


                            try {
                                JSONObject jsonObj = new JSONObject(error.toString());
                                System.out.println(error.toString());
                                System.out.println("---------------------------");
                                System.out.println(jsonObj);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject jsonObj = new JSONObject(error.toString());
                                String username = jsonObj.getJSONArray("username").get(0).toString();

                                if (!username.isEmpty()) {
                                    teacherSignUp.setUsername(username);
                                }

                                teacherSignUpMutableLiveData.postValue(teacherSignUp);
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject jsonObj = new JSONObject(error.toString());
                                String email = jsonObj.getJSONArray("email").get(0).toString();

                                if (!email.isEmpty()) {
                                    teacherSignUp.setEmail(email);
                                }

                                teacherSignUpMutableLiveData.postValue(teacherSignUp);
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TeacherSignUp> call, Throwable t) {
                        Log.d("REST", "Response fail");
                        teacherSignUpMutableLiveData.postValue(new TeacherSignUp(new Throwable(),true));
                    }
                }
        );
    }

    public LiveData<TeacherSignUp> getSignUpResponse(){
        return teacherSignUpMutableLiveData;
    }

}

