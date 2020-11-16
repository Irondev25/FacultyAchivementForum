package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshopEdit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.confernceEdit.ProfileConferenceEdit;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.ProfileWorkshop;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshopEdit.viewModel.WorkshopEditViewModel;
import com.irondev25.facultyachivementforum.util.DatePickerFragment;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileWorkshopEdit extends Fragment {
    private static final String TAG = "ProfileWorkshopEdit";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;

    ProgressDialog progressBar;

    private ProfileUpdate.OnFragmentInteractionListener mListener;
    String token;

    //store value of selected paper type
    String currentVal = "";

    String workshopTitle,workshopLocation,workshopDate,workshopType;
    MultipartBody.Part certificate = null;

    private WorkshopEditViewModel viewModel;

    private WorkshopObject workshop;
    public ProfileWorkshopEdit(WorkshopObject workshop,String token) {
        this.workshop = workshop;
        this.token    = token;
    }

    private TextInputLayout workshopTitleTextInputLayout;
    private TextInputLayout workshopLocationTextInputLayout;
    private TextInputLayout workshopDateTextInputLayout;
    private AutoCompleteTextView workshopTypeAutoCompleteTextView;
    private TextView workshopCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateConferenceButton;
    private Button cancleButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(WorkshopEditViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<WorkshopObject>() {
            @Override
            public void onChanged(WorkshopObject workshopObject) {
                if(workshopObject != null) {
                    Toast.makeText(getContext(), "Workshop Updated", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                            new ProfileWorkshop(token)).commit();
                }
                else if(workshopObject.getError() != null) {
                    Toast.makeText(getContext(), workshopObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "ProfileWorkshopEdit: Some Error Occured", Toast.LENGTH_SHORT).show();
                }
                if(progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_workshop_edit,container,false);
        setProgressBar(view);

        workshopTitleTextInputLayout = view.findViewById(R.id.teacher_profile_workshop_edit_title);
        workshopLocationTextInputLayout = view.findViewById(R.id.teacher_profile_workshop_edit_location);
        workshopDateTextInputLayout = view.findViewById(R.id.teacher_profile_workshop_edit_date);
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        workshopDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.setTargetFragment(ProfileWorkshopEdit.this,REQUEST_CODE_DATE);
                    newFragment.show(fm,"datePicker");
                }
            }
        });
        workshopTypeAutoCompleteTextView = view.findViewById(R.id.teacher_profile_workshop_edit_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_menu_popup_item,GlobalVars.paperList);
        workshopTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        workshopTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workshopType = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
                currentVal = workshopType;
            }
        });
        workshopTypeAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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
        workshopCertificateDetailTextView = view.findViewById(R.id.workshop_certificateDetail);
        attachCertificateButton = view.findViewById(R.id.teacher_profile_workshop_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateConferenceButton = view.findViewById(R.id.teacher_profile_workshop_update);
        updateConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshopTitle = workshopTitleTextInputLayout.getEditText().getText().toString();
                workshopLocation = workshopLocationTextInputLayout.getEditText().getText().toString();
                workshopDate = workshopDateTextInputLayout.getEditText().getText().toString();
                workshopType = GlobalVars.revPaperTypes.get(workshopTypeAutoCompleteTextView.getText().toString());
                viewModel.updateProfileWorkshop(
                        token,workshop.getUrl(),
                        workshopTitle, workshopLocation,
                        workshopDate, workshopType,
                        certificate
                );
                progressBar.show();
            }
        });
        cancleButton = view.findViewById(R.id.teacher_profile_workshop_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,
                        new ProfileWorkshop(token)).commit();
            }
        });
        setDataIntoView(workshop);
        return view;
    }

    private void setDataIntoView(WorkshopObject workshop) {
        workshopTitleTextInputLayout.getEditText().setText(workshop.getTopic());
        workshopLocationTextInputLayout.getEditText().setText(workshop.getLocation());
        workshopDateTextInputLayout.getEditText().setText(workshop.getDate());
        workshopTypeAutoCompleteTextView.setText(GlobalVars.paperTypes.get(workshop.getWorkshopType()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String selectedDate ;
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_DATE:
                    selectedDate = data.getStringExtra("selectedDate");
                    workshopDateTextInputLayout.getEditText().setText(selectedDate);
                    break;
                case REQUEST_CODE_FILE:
                    Uri uri = data.getData();
                    File originalFile = new File(RealPathUtil.getRealPath(getContext(),uri));
                    RequestBody filePart = RequestBody.create(
                            MediaType.parse(getContext().getContentResolver().getType(uri)),
                            originalFile
                    );
                    certificate = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                    workshopCertificateDetailTextView.setText(originalFile.getName());
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

    public void setProgressBar(View view) {
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }
}
