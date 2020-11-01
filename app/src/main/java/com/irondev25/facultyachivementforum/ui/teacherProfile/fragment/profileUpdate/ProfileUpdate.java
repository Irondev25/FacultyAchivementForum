package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.signup.SignupActivity;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.viewModel.ProfileGetViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.viewModel.ProfileUpdateViewModel;
import com.irondev25.facultyachivementforum.util.DatePickerFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileUpdate extends Fragment {
    private static final String TAG = "ProfileUpdate";

    public static final int REQUEST_CODE_DOB = 11;
    public static final int REQUEST_CODE_DOJ = 12;
    private OnFragmentInteractionListener mListerner;

    private ProfileGetViewModel viewModel;
    private ProfileUpdateViewModel updateViewModel;

    CircleImageView profilePic;
    FloatingActionButton addPic;
    TextInputLayout firstNameTextInputLayout;
    TextInputLayout middleNameTextInputLayout;
    TextInputLayout lastNameTextInputLayout;
    TextInputLayout dobTextInputLayout;
    TextInputLayout dojTextInputLayout;
    TextInputLayout mobileNumberTextInputLayout;
    AutoCompleteTextView sexAutoCompleteTextView;
    AutoCompleteTextView departmentAutoCompleteTextView;
    Button registerButton;

    MultipartBody.Part file = null;

    private HashMap<String, String> genderMap;
    private HashMap<String, String> reverserGenderMap;
    private ArrayList<String> genderList;

    private HashMap<String,Integer> deptMap;
    private ArrayList<String> deptList;
    private HashMap<Integer,String> reverseDeptMap;

    private String token;

    String username,email,firstName, middleName, lastName, dob,doj, mobileNumber, sex;
    int department;

    public ProfileUpdate() {}

    public ProfileUpdate(String token) {
        this.token = token;
    }

    public static ProfileUpdate newInstance() {
        ProfileUpdate fragment = new ProfileUpdate();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deptMap = GlobalVars.deptPairList;
        reverseDeptMap = GlobalVars.reverseDeptMap;
        deptList = GlobalVars.deptList;
        genderList = GlobalVars.genderList;
        genderMap = GlobalVars.genderMap;
        reverserGenderMap = GlobalVars.reverseGenderMap;

        viewModel = ViewModelProviders.of(this).get(ProfileGetViewModel.class);
        updateViewModel = ViewModelProviders.of(this).get(ProfileUpdateViewModel.class);

        viewModel.init();
        updateViewModel.init();

        viewModel.getProfileData(token);
        viewModel.getProfileLiveData().observe(this, new Observer<ProfileObject>() {
            @Override
            public void onChanged(ProfileObject profileObject) {
                if(profileObject!=null){
                    username = profileObject.getUserName();
                    email = profileObject.getEmail();
                    sex = profileObject.getSex();
                    department = profileObject.getDepartment();
                    setDataIntoView(profileObject);
                    Log.d(TAG, "onChanged: " + profileObject.getFirstName());
                }
                else if(profileObject.getT()!=null){
                    Toast.makeText(getContext(), profileObject.getT().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateViewModel.getLiveData().observe(this, new Observer<ProfileObject>() {
            @Override
            public void onChanged(ProfileObject profileObject) {
                if(profileObject!=null){
                    Toast.makeText(getContext(), "Profile Update Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error At ProfileUpdate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_ru_frag,container,false);

        profilePic = view.findViewById(R.id.profile_profile_image);
        addPic = view.findViewById(R.id.profile_pick_image);
        setupAddPicButton();
        firstNameTextInputLayout = view.findViewById(R.id.profile_first_name);
        middleNameTextInputLayout = view.findViewById(R.id.profile_middle_name);
        lastNameTextInputLayout = view.findViewById(R.id.profile_last_name);
        dobTextInputLayout = view.findViewById(R.id.profile_dob);

        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        dobTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(ProfileUpdate.this,REQUEST_CODE_DOB);
                newFragment.show(fm,"datePicker");
            }
        });

        dojTextInputLayout = view.findViewById(R.id.profile_doj);
        dojTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(ProfileUpdate.this,REQUEST_CODE_DOJ);
                newFragment.show(fm,"datePicker");
            }
        });
        mobileNumberTextInputLayout = view.findViewById(R.id.profile_mob_no);
        sexAutoCompleteTextView = view.findViewById(R.id.profile_sex);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_menu_popup_item,GlobalVars.getGenderList());
        sexAutoCompleteTextView.setAdapter(sexAdapter);
        sexAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sex = genderMap.get(genderList.get(position));
            }
        });
        sexAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String currentVal = "";
                AutoCompleteTextView actv = (AutoCompleteTextView)v;
                if(hasFocus){
                    currentVal = actv.getText().toString();
                    actv.setText("");
                }
                else{
                    actv.setText(currentVal);
                }
            }
        });
        departmentAutoCompleteTextView = view.findViewById(R.id.profile_department);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item,deptList);
        departmentAutoCompleteTextView.setAdapter(departmentAdapter);
        departmentAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                department = deptMap.get(deptList.get(position));
            }
        });
        departmentAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String currentVal = "";
                AutoCompleteTextView actv = (AutoCompleteTextView)v;
                if(hasFocus){
                    currentVal = actv.getText().toString();
                    actv.setText("");
                }
                else{
                    actv.setText(currentVal);
                }
            }
        });
        mobileNumberTextInputLayout = view.findViewById(R.id.profile_mob_no);
        registerButton = view.findViewById(R.id.profile_update_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameTextInputLayout.getEditText().getText().toString();
                middleName = middleNameTextInputLayout.getEditText().getText().toString();
                lastName = lastNameTextInputLayout.getEditText().getText().toString();
                dob = dobTextInputLayout.getEditText().getText().toString();
                doj = dojTextInputLayout.getEditText().getText().toString();
                mobileNumber = mobileNumberTextInputLayout.getEditText().toString();
//                sex = genderMap.get(sexAutoCompleteTextView.getText().toString());
//                department = deptMap.get(departmentAutoCompleteTextView.getText().toString());
                teacherProfileUpdate();
            }
        });
        return view;
    }

    public void setDataIntoView(ProfileObject profileObject){
        Glide.with(getView()).load(profileObject.getProfile_pic()).into(profilePic);
        firstNameTextInputLayout.getEditText().setText(profileObject.getFirstName());
        middleNameTextInputLayout.getEditText().setText(profileObject.getMiddleName());
        lastNameTextInputLayout.getEditText().setText(profileObject.getLastName());
        dobTextInputLayout.getEditText().setText(profileObject.getDob());
        dojTextInputLayout.getEditText().setText(profileObject.getDoj());
        mobileNumberTextInputLayout.getEditText().setText(profileObject.getMobileNum());
        sexAutoCompleteTextView.setText(reverserGenderMap.get(profileObject.getSex()));
        departmentAutoCompleteTextView.setText(reverseDeptMap.get(profileObject.getDepartment()));
        mobileNumberTextInputLayout.getEditText().setText(profileObject.getMobileNum());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        String selectedDate;
        if(resultCode == Activity.RESULT_OK && data!=null){
            if(requestCode == 3006){
                Uri fileUri = data.getData();
                File originalFile = ImagePicker.Companion.getFile(data);

                RequestBody filePart = RequestBody.create(
                        MediaType.parse("image/*"),
                        originalFile
                );
                file = MultipartBody.Part.createFormData("profile_pic",originalFile.getName(), filePart);
                profilePic.setImageURI(fileUri);

            }
            else{
                selectedDate = data.getStringExtra("selectedDate");
                if(requestCode == REQUEST_CODE_DOB) {
                    dobTextInputLayout.getEditText().setText(selectedDate);
                }
                else if(requestCode == REQUEST_CODE_DOJ) {
                    dojTextInputLayout.getEditText().setText(selectedDate);
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListerner = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            mListerner = (OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    private void setupAddPicButton(){
        addPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("REST", "onClick: "+addPic);
                ImagePicker.Companion.with(ProfileUpdate.this)
                        .maxResultSize(480,480).start(3006);
            }
        });
    }

    private void teacherProfileUpdate(){
        updateViewModel.teacherProfileUpdate(
                username,email,firstName,middleName,lastName,
                dob,doj,sex,department,mobileNumber,file,token
        );
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
