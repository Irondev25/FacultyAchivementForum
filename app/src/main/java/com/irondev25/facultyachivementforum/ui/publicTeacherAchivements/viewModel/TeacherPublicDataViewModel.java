package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.repository.TeacherDetailPublicRepository;

public class TeacherPublicDataViewModel extends AndroidViewModel {
    private TeacherDetailPublicRepository teacherDetailPublicRepository;
    private LiveData<TeacherDetailPublic> teacherDetailPublicLiveData;

    public TeacherPublicDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        teacherDetailPublicRepository = new TeacherDetailPublicRepository();
        teacherDetailPublicLiveData = teacherDetailPublicRepository.getTeacherPublicLiveDate();
    }

    public void getTeacherList(String url){
        teacherDetailPublicRepository.getTeacherPublicDate(url);
    }

    public LiveData<TeacherDetailPublic> getTeacherDetailPublicLiveData(){
        return teacherDetailPublicLiveData;
    }
}
