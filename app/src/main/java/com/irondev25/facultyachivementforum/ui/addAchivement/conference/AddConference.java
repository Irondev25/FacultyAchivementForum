package com.irondev25.facultyachivementforum.ui.addAchivement.conference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.addAchivement.conference.viewmodel.AddConferenceViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.util.DatePickerForActivity;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddConference extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddConference";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;
    public static final int ADD_CONFERENCE = 124;
    private ProfileUpdate.OnFragmentInteractionListener mListener;

    private AddConferenceViewModel viewModel;

    String token;
    String conferenceTitle,paperTitle,conferenceDate, conferenceType=null;
    MultipartBody.Part certificate = null;

    private TextInputLayout conferenceTitleTextInputLayout;
    private TextInputLayout paperTitleTextInputLayout;
    private TextInputLayout conferenceDateTextInputLayout;
    private AutoCompleteTextView conferenceTypeAutoCompleteTextView;
    private TextView conferenceCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateConferenceButton;
    private Button cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conference);

        initView();

        token = getIntent().getStringExtra("token");
        viewModel = ViewModelProviders.of(this).get(AddConferenceViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<ConferenceObject>() {
            @Override
            public void onChanged(ConferenceObject conferenceObject) {
                if(conferenceObject != null) {
                    Toast.makeText(getApplicationContext(), "Conference Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(ADD_CONFERENCE);
                    finish();
                }
                else if(conferenceObject.getError()!=null){
                    Toast.makeText(getApplicationContext(), conferenceObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"AddConference: Some Error Occured",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else{
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            else{
                Log.d(TAG, "onCreate: supportactionbar not available");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initView() {
        conferenceTitleTextInputLayout = findViewById(R.id.teacher_profile_conference_add_title);
        paperTitleTextInputLayout = findViewById(R.id.teacher_profile_conference_add_paper);
        conferenceDateTextInputLayout = findViewById(R.id.teacher_profile_conference_add_date);
        conferenceDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment datePicker = new DatePickerForActivity();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            }
        });
        conferenceTypeAutoCompleteTextView = findViewById(R.id.teacher_profile_conference_add_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(this,R.layout.dropdown_menu_popup_item,GlobalVars.paperList);
        conferenceTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        conferenceTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conferenceType = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
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
        conferenceCertificateDetailTextView = findViewById(R.id.conference_add_certificateDetail);
        attachCertificateButton = findViewById(R.id.teacher_profile_conference_add_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateConferenceButton = findViewById(R.id.teacher_profile_conference_add);
        updateConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conferenceTitle = conferenceTitleTextInputLayout.getEditText().getText().toString();
                paperTitle = paperTitleTextInputLayout.getEditText().getText().toString();
                conferenceDate = conferenceDateTextInputLayout.getEditText().getText().toString();
                conferenceType = GlobalVars.revPaperTypes.get(conferenceTypeAutoCompleteTextView.getText().toString());
                viewModel.createProfileConference(token,conferenceTitle,paperTitle,conferenceDate,conferenceType,certificate);
            }
        });
        cancleButton = findViewById(R.id.teacher_profile_conference_add_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        conferenceDateTextInputLayout.getEditText().setText(selectedDate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
                    Uri uri = data.getData();
                    File originalFile = new File(RealPathUtil.getRealPath(this, uri));
                    RequestBody filePart = RequestBody.create(
                            MediaType.parse(getContentResolver().getType(uri)),
                            originalFile
                    );
                    certificate = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                    conferenceCertificateDetailTextView.setText(originalFile.getName());
                    break;
            }
        }
    }
}