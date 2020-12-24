package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.ProfileJournal;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journalEdit.viewModel.JournalEditViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.ProfileUpdate;
import com.irondev25.facultyachivementforum.util.DatePickerFragment;
import com.irondev25.facultyachivementforum.util.RealPathUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileJournalEdit extends Fragment {
    private static final String TAG = "ProfileJournalEdit";
    private static final int REQUEST_CODE_DATE = 15;
    private static final int REQUEST_CODE_FILE = 16;

    ProgressDialog progressBar;

    private ProfileUpdate.OnFragmentInteractionListener mListener;
    String token;
    String currentVal;

    String journalTitle, paperTitle, journalDate, journalType, journalImpactFactor;
    MultipartBody.Part file = null;

    private JournalEditViewModel viewModel;

    private JournalObject journal;
    public ProfileJournalEdit(JournalObject journal, String token) {
        this.journal = journal;
        this.token = token;
    }

    private TextInputLayout journalNameTextInputLayout;
    private TextInputLayout paperTitleTextInputLayout;
    private TextInputLayout journalDateTextInputLayout;
    private AutoCompleteTextView journalTypeAutoCompleteTextView;
    private TextInputLayout journalImpactFactorTextInputLayout;
    private TextView journalCertificateDetailTextView;
    private Button attachCertificateButton;
    private Button updateConferenceButton;
    private Button cancleButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(JournalEditViewModel.class);
        viewModel.init();
        viewModel.getLiveData().observe(this, new Observer<JournalObject>() {
            @Override
            public void onChanged(JournalObject journalObject) {
                if(journalObject != null) {
                    Toast.makeText(getContext(), "Journal Updated", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileJournal(token)).commit();
                }
                else if(journalObject.getError() != null) {
                    Toast.makeText(getContext(), journalObject.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "ProfileJournalEdit: Some error occured", Toast.LENGTH_SHORT).show();
                }
                if(progressBar.isShowing())
                {
                    progressBar.dismiss();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_journal_edit,container,false);
        setProgressBar(view);

        journalNameTextInputLayout = view.findViewById(R.id.teacher_profile_journal_edit_title);
        paperTitleTextInputLayout = view.findViewById(R.id.teacher_profile_journal_edit_paper);
        journalDateTextInputLayout = view.findViewById(R.id.teacher_profile_journal_edit_date);
        final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        journalDateTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.setTargetFragment(ProfileJournalEdit.this,REQUEST_CODE_DATE);
                    newFragment.show(fm,"datePicker");
                }
            }
        });
        journalTypeAutoCompleteTextView = view.findViewById(R.id.teacher_profile_journal_edit_paperType);
        ArrayAdapter<String> paperTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_menu_popup_item, GlobalVars.paperList);
        journalTypeAutoCompleteTextView.setAdapter(paperTypeAdapter);
        journalTypeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                journalType = GlobalVars.revPaperTypes.get(GlobalVars.paperList.get(position));
                Log.d(TAG, "onItemClick: " + journalType);
            }
        });
        journalTypeAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                currentVal = "";
                AutoCompleteTextView actv = (AutoCompleteTextView) v;
                if(hasFocus) {
                    currentVal = actv.getText().toString();
                    actv.setText("");
                }
            }
        });
        journalImpactFactorTextInputLayout = view.findViewById(R.id.teacher_profile_journal_edit_if);
        attachCertificateButton = view.findViewById(R.id.teacher_profile_journal_attach_certificate);
        attachCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile =Intent.createChooser(chooseFile,"Choose Certificate");
                startActivityForResult(chooseFile,REQUEST_CODE_FILE);
            }
        });
        journalCertificateDetailTextView = view.findViewById(R.id.journal_certificateDetail);
        updateConferenceButton = view.findViewById(R.id.teacher_profile_journal_update);
        updateConferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalTitle = journalNameTextInputLayout.getEditText().getText().toString();
                paperTitle = paperTitleTextInputLayout.getEditText().getText().toString();
                journalDate = journalDateTextInputLayout.getEditText().getText().toString();
                Log.d(TAG, "onClick: " + journalTypeAutoCompleteTextView.getText().toString());
                if(journalTypeAutoCompleteTextView.getText().toString() != null) {
                    journalType = GlobalVars.revPaperTypes.get(journalTypeAutoCompleteTextView.getText().toString());
                }
                journalImpactFactor = journalImpactFactorTextInputLayout.getEditText().getText().toString();
                Log.d(TAG, "onClick: "+token+" "+journal.getUrl()+" "+
                        journalTitle+" "+paperTitle+" "+journalDate+" "+journalType+" "+
                        journalImpactFactor);
                viewModel.updateProfileJournal(token,journal.getUrl(),
                        journalTitle,paperTitle,journalDate,journalType,
                        journalImpactFactor,file);
                progressBar.show();
            }
        });
        cancleButton = view.findViewById(R.id.teacher_profile_journal_cancle);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.teacher_profile_fragment,new ProfileJournal(token)).commit();
            }
        });
        setDataIntoView(journal);
        return view;
    }

    public void setDataIntoView(JournalObject journal) {
        journalNameTextInputLayout.getEditText().setText(journal.getJournalTitle());
        paperTitleTextInputLayout.getEditText().setText(journal.getJournalPaperTitle());
        journalDateTextInputLayout.getEditText().setText(journal.getJournalDate());
        journalTypeAutoCompleteTextView.setText(GlobalVars.paperTypes.get(journal.getJournalType()));
        journalImpactFactorTextInputLayout.getEditText().setText(journal.getJournalImpactFactor());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String selectedDate ;
        if(resultCode == Activity.RESULT_OK && data!=null) {
            switch (requestCode) {
                case REQUEST_CODE_DATE:
                    selectedDate = data.getStringExtra("selectedDate");
                    journalDateTextInputLayout.getEditText().setText(selectedDate);
                    break;
                case REQUEST_CODE_FILE:
                    Uri uri = data.getData();
                    File originalFile = new File(RealPathUtil.getRealPath(getContext(),uri));
                    RequestBody filePart = RequestBody.create(
                            MediaType.parse(getContext().getContentResolver().getType(uri)),
                            originalFile);
                    file = MultipartBody.Part.createFormData("certificate",originalFile.getName(),filePart);
                    journalCertificateDetailTextView.setText(originalFile.getName());
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
