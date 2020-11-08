package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.ProfileAward;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.awardEdit.viewModel.AwardEditViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.util.DatePickerFragment;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;


public class ProfileAwardEdit extends Fragment {
    private static final String TAG = "ProfileAwardEdit";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;
    private ProfileUpdate.OnFragmentInteractionListener mListener;
    String token;

    String[] fileTypes = new String[]{"pdf"};

    String awardTitle,awardDate,awardDetail;
    MultipartBody.Part file = null;

    private AwardEditViewModel viewModel;


    private AwardObject award;
    public ProfileAwardEdit(AwardObject awardObject, String token) {
        this.award = awardObject;
        this.token = token;
    }

    private TextInputLayout awardTitleTextView;
    private TextInputLayout awardDateTextView;
    private TextInputLayout awardDetailTextView;
    private TextView awardCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateAwardButton;
    private Button cancleButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AwardEditViewModel.class);
        viewModel.init();

        viewModel.getLiveData().observe(this, new Observer<AwardObject>() {
            @Override
            public void onChanged(AwardObject awardObject) {
                if(awardObject != null) {
                    Toast.makeText(getContext(), "Award Updated", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.teacher_profile_fragment,new ProfileAward(token)).commit();
                }
                else if(awardObject.getError()!=null){
                    Toast.makeText(getContext(), awardObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"ProfileAwardEdit: Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_award_edit,container,false);
        awardTitleTextView = view.findViewById(R.id.teacher_profile_award_edit_title);

        awardDateTextView = view.findViewById(R.id.teacher_profile_award_edit_date);
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        awardDateTextView.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.setTargetFragment(ProfileAwardEdit.this,REQUEST_CODE_DATE);
                    newFragment.show(fm,"datePicker");
                }
            }
        });

        awardDetailTextView = view.findViewById(R.id.teacher_profile_award_edit_detail);
        awardCertificateDetailTextView = view.findViewById(R.id.award_certificateDetail);
        attachCertificateButton = view.findViewById(R.id.teacher_profile_award_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//                    FilePickerBuilder.getInstance()
//                            .setMaxCount(1)
//                            .setActivityTheme(R.style.LibAppTheme)
//                            .pickFile(ProfileAwardEdit.this,REQUEST_CODE_FILE);
//                }
//                else{
//                    ActivityCompat.requestPermissions(getActivity(),new );
//                }
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateAwardButton = view.findViewById(R.id.teacher_profile_award_update);
        updateAwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awardTitle = awardTitleTextView.getEditText().getText().toString();
                awardDate = awardDateTextView.getEditText().getText().toString();
                awardDetail = awardDetailTextView.getEditText().getText().toString();
                viewModel.updateProfileAward(
                        token,award.getUrl(),
                        awardTitle,awardDate,awardDetail,
                        file
                );
            }
        });
        cancleButton = view.findViewById(R.id.teacher_profile_award_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileAward(token)).commit();
            }
        });
        setDataIntoView(award);
        return view;
    }

    public void setDataIntoView(AwardObject award){
        awardTitleTextView.getEditText().setText(award.getAwardTitle());
        awardDateTextView.getEditText().setText(award.getAwardDate());
        awardDetailTextView.getEditText().setText(award.getAwardDetails());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode);
        String selectedDate;
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_DATE:
                    selectedDate = data.getStringExtra("selectedDate");
                    awardDateTextView.getEditText().setText(selectedDate);
                    break;
                case REQUEST_CODE_FILE:
//                        List<Uri> docPath = new ArrayList<>();
//                        docPath.addAll(data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
//                    Log.d(TAG, docPath.toString());
                        Uri uri = data.getData();//docPath.get(0);
                    Log.d(TAG, uri.toString());
                        File originalFile = new File(RealPathUtil.getRealPath(getContext(), uri));
                        RequestBody filePart = RequestBody.create(
                                MediaType.parse(getContext().getContentResolver().getType(uri)),
                                originalFile
                        );
                        Log.d(TAG, "onActivityResult: "+uri.getPath()+" "+originalFile.getName());
                        file = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                        awardCertificateDetailTextView.setText(originalFile.getName());
                    break;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ProfileUpdate.OnFragmentInteractionListener) {
            mListener = (ProfileUpdate.OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }
}
