package com.irondev25.facultyachivementforum;

import com.irondev25.facultyachivementforum.ui.browseAchivement.pojo.TeacherItem;
import com.irondev25.facultyachivementforum.ui.login.pojo.TeacherSignIn;
import com.irondev25.facultyachivementforum.ui.login.pojo.TokenPojo;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;
import com.irondev25.facultyachivementforum.ui.signup.pojo.TeacherSignUp;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("/api/teacher/teachers")
    Call<List<TeacherItem>> getTeacherList(@Query("dept") Integer dept_id, @Query("first_name") String fName);

    @POST("/api-auth-token")
    Call<TokenPojo> loginRequest(@Body TeacherSignIn teacherSignIn);

    /*@Multipart
    @POST("/api/teacher/create")
    Call<TeacherSignUp> signupRequest(@Part("username") String username, @Part("email") String email, @Part("first_name") String firstName,
                                      @Part("middle name") String middleName, @Part("last_name") String lastName, @Part("dob") String dob, @Part("doj") String doj, @Part("sex") String sex, @Part("department") Integer dept,
                                      @Part MultipartBody.Part profile_pic, @Part("password") String password, @Part("password2") String password2, @Header("Content-Disposition")String file_name);*/

    @Multipart
    @POST("/api/teacher/create")
    Call<TeacherSignUp> signupRequest(@Part("username") RequestBody username, @Part("email") RequestBody email, @Part("first_name") RequestBody firstName,
                                      @Part("middle name") RequestBody middleName, @Part("last_name") RequestBody lastName, @Part("dob") RequestBody dob, @Part("doj") RequestBody doj, @Part("sex") RequestBody sex, @Part("department") RequestBody dept,
                                      @Part MultipartBody.Part profile_pic, @Part("password") RequestBody password, @Part("password2") RequestBody password2/*, @Header("Content-Disposition")String file_name*/);

    /*@FormUrlEncoded
    @POST("/api/teacher/create")
    Call<TeacherSignUp> signupRequest(@Field("username") String username, @Field("email") String email, @Field("first_name") String firstName,
                                      @Field("middle name") String middleName, @Field("last_name") String lastName, @Field("dob") String dob, @Field("doj") String doj, @Field("sex") String sex, @Field("department") Integer dept,
                                      @Field("profile_pic") MultipartBody.Part profile_pic, @Field("password") String password, @Field("password2") String password2);*/

    //    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);


    @GET
    Call<TeacherDetailPublic> teacherPublicDetail(@Url String url);

    @GET
    Call<TeacherDetailPublic> getTeacherDetail(@Url String url);

    @Multipart
    @PUT("/api/teacher/update")
    Call<ProfileObject> teacherProfileUpdate(@Part("username") RequestBody username, @Part("email") RequestBody email, @Part("first_name") RequestBody firstName,
                                             @Part("middle_name") RequestBody middleName, @Part("last_name") RequestBody lastName, @Part("dob") RequestBody dob, @Part("doj") RequestBody doj, @Part("sex") RequestBody sex, @Part("department") RequestBody dept,
                                             @Part("mobile_num") RequestBody mobileNumber,
                                             @Part MultipartBody.Part profile_pic, @Header("Authorization")String token);

    @GET("/api/teacher/update")
    Call<ProfileObject> teacherProfileRetrive(@Header("Authorization") String token);

    @GET
    Call<BasicProfileObject> getBasicProfile(@Url String url);

    @GET("/api/achivements/award/list")
    Call<List<AwardObject>> getProfileAwards(@Header("Authorization") String token);

    @GET("/api/achivements/conference/list")
    Call<List<ConferenceObject>> getProfileConferences(@Header("Authorization") String token);

    @GET("/api/achivements/journal/list")
    Call<List<JournalObject>> getProfileJournal(@Header("Authorization") String token);

    @GET("/api/achivements/workshop/list")
    Call<List<WorkshopObject>> getProfileWorkshop(@Header("Authorization") String token);

    @Multipart
    @PUT
    Call<AwardObject> updateProfileAward(@Header("Authorization") String token, @Url String url,
                                         @Part("award_title")RequestBody awardTitle,
                                         @Part("award_date") RequestBody awardDate,
                                         @Part("award_details")RequestBody awardDetail,
                                         @Part MultipartBody.Part certificate);

    @Multipart
    @POST("/api/achivements/award/create")
    Call<AwardObject> createProfileAward(@Header("Authorization") String token,
                                         @Part("award_title")RequestBody awardTitle,
                                         @Part("award_date") RequestBody awardDate,
                                         @Part("award_details")RequestBody awardDetail,
                                         @Part MultipartBody.Part certificate);

    @Multipart
    @PUT
    Call<ConferenceObject> updateProfileConference(@Header("Authorization") String token, @Url String url,
                                                   @Part("name") RequestBody conferenceName,
                                                   @Part("title") RequestBody paperTitle,
                                                   @Part("date") RequestBody conferenceDate,
                                                   @Part MultipartBody.Part certificate,
                                                   @Part("conference_type") RequestBody conferenceType);

    @Multipart
    @POST("/api/achivements/conference/create")
    Call<ConferenceObject> createProfileConference(@Header("Authorization") String token,
                                                   @Part("name") RequestBody conferenceName,
                                                   @Part("title") RequestBody paperTitle,
                                                   @Part("date") RequestBody conferenceDate,
                                                   @Part MultipartBody.Part certificate,
                                                   @Part("conference_type") RequestBody conferenceType);

    @Multipart
    @PUT
    Call<JournalObject> updateProfileJournal(@Header("Authorization") String token, @Url String url,
                                             @Part("journal_title") RequestBody journalTitle,
                                             @Part("paper_title") RequestBody paperTitle,
                                             @Part("date") RequestBody journalDate,
                                             @Part("journal_type") RequestBody journalType,
                                             @Part("impact_factor") RequestBody journalImapactFactor,
                                             @Part MultipartBody.Part certificate);

    @Multipart
    @POST("/api/achivements/journal/create")
    Call<JournalObject> createProfileJournal(@Header("Authorization") String token,
                                             @Part("journal_title") RequestBody journalTitle,
                                             @Part("paper_title") RequestBody paperTitle,
                                             @Part("date") RequestBody journalDate,
                                             @Part("journal_type") RequestBody journalType,
                                             @Part("impact_factor") RequestBody journalImapactFactor,
                                             @Part MultipartBody.Part certificate);

    @Multipart
    @PUT
    Call<WorkshopObject> updateProfileWorkshop(@Header("Authorization") String token, @Url String url,
                                               @Part("topic") RequestBody workshopTopic,
                                               @Part("date") RequestBody workshopDate,
                                               @Part("workshop_type") RequestBody workshopType,
                                               @Part("location") RequestBody workshopLocation,
                                               @Part MultipartBody.Part certificate);

    @Multipart
    @POST("/api/achivements/workshop/create")
    Call<WorkshopObject> createProfileWorkshop(@Header("Authorization") String token,
                                               @Part("topic") RequestBody workshopTopic,
                                               @Part("date") RequestBody workshopDate,
                                               @Part("workshop_type") RequestBody workshopType,
                                               @Part("location") RequestBody workshopLocation,
                                               @Part MultipartBody.Part certificate);


    @DELETE
    Call<ResponseBody> deleteProfileAward(@Header("Authorization") String token, @Url String url);

    @DELETE
    Call<ResponseBody> deleteProfileConference(@Header("Authorization") String token, @Url String url);

    @DELETE
    Call<ResponseBody> deleteProfileJournal(@Header("Authorization") String token, @Url String url);

    @DELETE
    Call<ResponseBody> deleteProfileWorkshop(@Header("Authorization") String token, @Url String url);
}


