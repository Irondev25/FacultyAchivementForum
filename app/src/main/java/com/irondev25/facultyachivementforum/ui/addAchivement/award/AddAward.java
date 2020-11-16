package com.irondev25.facultyachivementforum.ui.addAchivement.award;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.addAchivement.award.viewModel.AddAwardViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;
import com.irondev25.facultyachivementforum.util.DatePickerForActivity;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddAward extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final String TAG = "AddAward";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;
    public static final int ADD_AWARD = 123;

    ProgressDialog progressBar;

    private AddAwardViewModel viewModel;

    MultipartBody.Part certificate;
    String awardTitle, awardDetail, awardDate;
    String token;

    private TextInputLayout awardTitleTextInputLayout;
    private TextInputLayout awardDateTextInputLayout;
    private TextInputLayout awardDetailTextInputLayout;
    private TextView awardFileDetailTextView;
    private Button attachCertificateButton;
    private Button updateAwardButton;
    private Button cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_award);
        initView();

        token = getIntent().getStringExtra("token");

        viewModel = ViewModelProviders.of(this).get(AddAwardViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<AwardObject>() {
            @Override
            public void onChanged(AwardObject awardObject) {
                if(awardObject != null) {
                    Toast.makeText(getApplicationContext(), "Award Added", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    Intent intent = new Intent();
                    setResult(ADD_AWARD);
                    finish();
                }
                else if(awardObject.getError()!=null){
                    Toast.makeText(getApplicationContext(), awardObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"AddAward: Some Error Occured",Toast.LENGTH_SHORT).show();
                }
                progressBar.dismiss();
            }
        });

        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else{
            Log.d(TAG, "onCreate: actionbar not available");
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
        setProgressBar();
        awardTitleTextInputLayout = findViewById(R.id.teacher_profile_award_add_title);
        awardDateTextInputLayout = findViewById(R.id.teacher_profile_award_add_date);
        final FragmentManager fm = getSupportFragmentManager();
        awardDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: AddAward");
                if(hasFocus) {
                    DialogFragment datePicker = new DatePickerForActivity();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            }
        });
//        awardDateTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment datePicker = new DatePickerForActivity();
//                datePicker.show(getSupportFragmentManager(), "date picker");
//            }
//        });
        awardDetailTextInputLayout = findViewById(R.id.teacher_profile_award_add_detail);
        awardFileDetailTextView = findViewById(R.id.award_add_certificateDetail);
        attachCertificateButton = findViewById(R.id.teacher_profile_award_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateAwardButton = findViewById(R.id.teacher_profile_award_add);
        updateAwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awardTitle = awardTitleTextInputLayout.getEditText().getText().toString();
                awardDate = awardDateTextInputLayout.getEditText().getText().toString();
                awardDetail = awardDetailTextInputLayout.getEditText().getText().toString();
                viewModel.createProfileAward(token,awardTitle,awardDate,awardDetail,certificate);
                progressBar.show();
            }
        });
        cancleButton = findViewById(R.id.teacher_profile_award_add_cancle);
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
        awardDateTextInputLayout.getEditText().setText(selectedDate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
//                        List<Uri> docPath = new ArrayList<>();
//                        docPath.addAll(data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
//                    Log.d(TAG, docPath.toString());
                    Uri uri = data.getData();//docPath.get(0);
                    Log.d(TAG, uri.toString());
                    File originalFile = new File(RealPathUtil.getRealPath(this, uri));
                    RequestBody filePart = RequestBody.create(
                            MediaType.parse(getContentResolver().getType(uri)),
                            originalFile
                    );
                    Log.d(TAG, "onActivityResult: "+uri.getPath()+" "+originalFile.getName());
                    certificate = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                    awardFileDetailTextView.setText(originalFile.getName());
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