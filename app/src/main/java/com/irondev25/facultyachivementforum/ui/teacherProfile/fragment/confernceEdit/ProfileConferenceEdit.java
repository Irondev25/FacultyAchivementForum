package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.ProfileAward;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.ProfileConference;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit.viewModel.ConferenceEditViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.util.DatePickerFragment;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileConferenceEdit extends Fragment {
    private static final String TAG = "ProfileConferenceEdit";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;
    private ProfileUpdate.OnFragmentInteractionListener mListener;
    String token;

    String[] fileTypes = new String[] {"pdf"};

    String conferenceTitle,paperTitle,conferenceDate, conferenceType;
    MultipartBody.Part file = null;

    private ConferenceEditViewModel viewModel;

    private ConferenceObject conference;
    public ProfileConferenceEdit(ConferenceObject conference,String token) {
        this.conference = conference;
        this.token = token;
    }

    private TextInputLayout conferenceTitleTextInputLayout;
    private TextInputLayout paperTitleTextInputLayout;
    private TextInputLayout conferenceDateTextInputLayout;
    private AutoCompleteTextView conferenceTypeAutoCompleteTextView;
    private TextView conferenceCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateConferenceButton;
    private Button cancleButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this)
                .get(ConferenceEditViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<ConferenceObject>() {
            @Override
            public void onChanged(ConferenceObject conferenceObject) {
                if(conferenceObject != null) {
                    Toast.makeText(getContext(), "Confernce Updated", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.teacher_profile_fragment, new ProfileConference(token)).commit();
                }
                else if(conferenceObject.getError() != null) {
                    Toast.makeText(getContext(), conferenceObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"ProfileConferenceEdit: Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_conference_edit,container,false);
        conferenceTitleTextInputLayout = view.findViewById(R.id.teacher_profile_conference_edit_title);
        paperTitleTextInputLayout = view.findViewById(R.id.teacher_profile_conference_edit_paper);
        conferenceDateTextInputLayout = view.findViewById(R.id.teacher_profile_conference_edit_date);
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        conferenceDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.setTargetFragment(ProfileConferenceEdit.this,REQUEST_CODE_DATE);
                    newFragment.show(fm,"datePicker");
                }
            }
        });
        conferenceTypeAutoCompleteTextView = view.findViewById(R.id.teacher_profile_conference_edit_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_menu_popup_item,GlobalVars.paperList);
        conferenceTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        conferenceTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conferenceType  = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
            }
        });
        conferenceTypeAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String currentVal = "";
                AutoCompleteTextView actv = (AutoCompleteTextView) v;
                if(hasFocus) {
                    currentVal = actv.getText().toString();
                    actv.setText("");
                }
                else{
//                    if(actv.getText().toString().equals("")){
                        actv.setText(currentVal);
//                    }
                }
            }
        });
        conferenceCertificateDetailTextView = view.findViewById(R.id.conference_certificateDetail);
        attachCertificateButton = view.findViewById(R.id.teacher_profile_conference_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateConferenceButton = view.findViewById(R.id.teacher_profile_conference_update);
        updateConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conferenceTitle = conferenceTitleTextInputLayout.getEditText().getText().toString();
                paperTitle = paperTitleTextInputLayout.getEditText().getText().toString();
                conferenceDate = conferenceDateTextInputLayout.getEditText().getText().toString();
                conferenceType = GlobalVars.revPaperTypes.get(conferenceTypeAutoCompleteTextView.getText().toString());
                Log.d(TAG, conferenceTitle+" "+paperTitle+" "+conferenceDate+" "+conferenceType);
                viewModel.updateProfileConference(
                        token,conference.getUrl(),
                        conferenceTitle,paperTitle,
                        conferenceDate,file,conferenceType);
            }
        });
        cancleButton = view.findViewById(R.id.teacher_profile_conference_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileConference(token)).commit();
            }
        });
        setDataIntoView(conference);
        return view;
    }

    public void setDataIntoView(ConferenceObject conference) {
        conferenceTitleTextInputLayout.getEditText().setText(conference.getConferenceName());
        paperTitleTextInputLayout.getEditText().setText(conference.getConferencePaperTitle());
        conferenceDateTextInputLayout.getEditText().setText(conference.getConferenceDate());
        conferenceTypeAutoCompleteTextView.setText(GlobalVars.paperTypes.get(conference.getConferenceType()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String selectedDate ;
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_DATE:
                    selectedDate = data.getStringExtra("selectedDate");
                    conferenceDateTextInputLayout.getEditText().setText(selectedDate);
                    break;
                case REQUEST_CODE_FILE:
                    Uri uri = data.getData();
                    File originalFile = new File(RealPathUtil.getRealPath(getContext(),uri));
                    RequestBody filePart = RequestBody.create(
                            MediaType.parse(getContext().getContentResolver().getType(uri)),
                                    originalFile
                    );
                    file = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                    conferenceCertificateDetailTextView.setText(originalFile.getName());
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
