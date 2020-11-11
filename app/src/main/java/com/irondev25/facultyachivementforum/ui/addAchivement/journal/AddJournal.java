package com.irondev25.facultyachivementforum.ui.addAchivement.journal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
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
import com.irondev25.facultyachivementforum.ui.addAchivement.journal.viewModel.AddJournalViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.util.DatePickerForActivity;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddJournal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddJournal";
    private static final int REQUEST_CODE_FILE = 16;
    public static final int ADD_JOURNAL = 125;

    private ProfileUpdate.OnFragmentInteractionListener mListener;

    private AddJournalViewModel viewModel;

    String token, journalTitle, paperTitle, journalDate, journalType, journalImpactFactor;
    MultipartBody.Part certificate = null;

    private TextInputLayout journalNameTextInputLayout;
    private TextInputLayout paperTitleTextInputLayout;
    private TextInputLayout journalDateTextInputLayout;
    private AutoCompleteTextView journalTypeAutoCompleteTextView;
    private TextInputLayout journalImpactFactorTextInputLayout;
    private TextView journalCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateJournalButton;
    private Button cancleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        initView();

        token = getIntent().getStringExtra("token");
        viewModel = ViewModelProviders.of(this).get(AddJournalViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<JournalObject>() {
            @Override
            public void onChanged(JournalObject journalObject) {
                if(journalObject != null) {
                    Toast.makeText(getApplicationContext(), "Journal Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(ADD_JOURNAL);
                    finish();
                }
                else if(journalObject.getError()!=null){
                    Toast.makeText(getApplicationContext(), journalObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        journalNameTextInputLayout = findViewById(R.id.teacher_profile_journal_add_title);
        paperTitleTextInputLayout = findViewById(R.id.teacher_profile_journal_add_paper);
        journalDateTextInputLayout = findViewById(R.id.teacher_profile_journal_add_date);
        journalDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment datePicker = new DatePickerForActivity();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            }
        });

        journalTypeAutoCompleteTextView = findViewById(R.id.teacher_profile_journal_add_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(this,R.layout.dropdown_menu_popup_item,GlobalVars.paperList);
        journalTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        journalTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                journalType = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
            }
        });
        journalImpactFactorTextInputLayout = findViewById(R.id.teacher_profile_journal_add_if);
        journalCertificateDetailTextView = findViewById(R.id.journal_add_certificateDetail);
        attachCertificateButton = findViewById(R.id.teacher_profile_journal_add_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        updateJournalButton = findViewById(R.id.teacher_profile_journal_add);
        updateJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalTitle = journalNameTextInputLayout.getEditText().getText().toString();
                paperTitle = paperTitleTextInputLayout.getEditText().getText().toString();
                journalDate = journalDateTextInputLayout.getEditText().getText().toString();
                journalType = GlobalVars.revPaperTypes.get(journalTypeAutoCompleteTextView.getText().toString());
                journalImpactFactor = journalImpactFactorTextInputLayout.getEditText().getText().toString();
                viewModel.createProfileJournal(token,journalTitle,paperTitle,journalDate,journalType,
                        journalImpactFactor,certificate);
            }
        });
        cancleButton = findViewById(R.id.teacher_profile_journal_add_cancle);
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
        journalDateTextInputLayout.getEditText().setText(selectedDate);
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
                    journalCertificateDetailTextView.setText(originalFile.getName());
                    break;
            }
        }
    }
}