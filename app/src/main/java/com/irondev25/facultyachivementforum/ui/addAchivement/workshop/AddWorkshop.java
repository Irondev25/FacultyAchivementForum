package com.irondev25.facultyachivementforum.ui.addAchivement.workshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.irondev25.facultyachivementforum.ui.addAchivement.workshop.viewModel.AddWorkshopViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;
import com.irondev25.facultyachivementforum.util.DatePickerForActivity;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddWorkshop extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddWorkshop";
    private static final int ADD_WORKSHOP = 126;
    private static final int REQUEST_CODE_FILE = 16;

    ProgressDialog progressBar;

    private ProfileUpdate.OnFragmentInteractionListener mListener;

    private AddWorkshopViewModel viewModel;

    String token;

    String workshopTitle,workshopLocation,workshopDate,workshopType;
    MultipartBody.Part certificate = null;

    private TextInputLayout workshopTitleTextInputLayout;
    private TextInputLayout workshopLocationTextInputLayout;
    private TextInputLayout workshopDateTextInputLayout;
    private AutoCompleteTextView workshopTypeAutoCompleteTextView;
    private TextView workshopCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateWorkshopButton;
    private Button cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);

        initView();
        token = getIntent().getStringExtra("token");
        viewModel = ViewModelProviders.of(this).get(AddWorkshopViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<WorkshopObject>() {
            @Override
            public void onChanged(WorkshopObject workshopObject) {
                if(workshopObject != null) {
                    Toast.makeText(getApplicationContext(), "Conference Added", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    Intent intent = new Intent();
                    setResult(ADD_WORKSHOP);
                    finish();
                }
                else if(workshopObject.getError()!=null){
                    Toast.makeText(getApplicationContext(), workshopObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"AddConference: Some Error Occured",Toast.LENGTH_SHORT).show();
                }
                progressBar.dismiss();
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

    private void initView() {
        setProgressBar();
        workshopTitleTextInputLayout = findViewById(R.id.teacher_profile_workshop_add_title);
        workshopLocationTextInputLayout = findViewById(R.id.teacher_profile_workshop_add_location);
        workshopDateTextInputLayout = findViewById(R.id.teacher_profile_workshop_add_date);
        workshopDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment datePicker = new DatePickerForActivity();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            }
        });
        workshopTypeAutoCompleteTextView = findViewById(R.id.teacher_profile_workshop_add_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, GlobalVars.paperList);
        workshopTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        workshopTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workshopType = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
            }
        });
        workshopCertificateDetailTextView = findViewById(R.id.workshop_add_certificateDetail);
        attachCertificateButton = findViewById(R.id.teacher_profile_workshop_add_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateWorkshopButton = findViewById(R.id.teacher_profile_workshop_add);
        updateWorkshopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshopTitle = workshopTitleTextInputLayout.getEditText().getText().toString();
                workshopLocation = workshopLocationTextInputLayout.getEditText().getText().toString();
                workshopDate = workshopDateTextInputLayout.getEditText().getText().toString();
                workshopType = GlobalVars.revPaperTypes.get(workshopTypeAutoCompleteTextView.getText().toString());
                viewModel.createProfileWorkshop(
                        token,
                        workshopTitle, workshopLocation,
                        workshopDate, workshopType,
                        certificate
                );
                progressBar.show();
            }
        });
        cancleButton = findViewById(R.id.teacher_profile_workshop_add_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        workshopDateTextInputLayout.getEditText().setText(selectedDate);
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
                    workshopCertificateDetailTextView.setText(originalFile.getName());
                    break;
            }
        }
    }

    public void setProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }
}