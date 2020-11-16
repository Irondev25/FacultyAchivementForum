package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.pojo.ProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.profileUpdate.viewModel.ProfileGetViewModel;
import com.irondev25.facultyachivementforum.ui.teacherProfile.pojo.BasicProfileObject;
import com.irondev25.facultyachivementforum.ui.teacherProfile.viewModel.BasicProfileViewModel;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements Observer<BasicProfileObject> {
    private static final String TAG = "HomeFragment";
    private BasicProfileViewModel viewModel;

    //class vars
    String username;

    //view elements
    ProgressDialog progressBar;
    CircleImageView circleImageView;
    TextView usernameTextView;
    TextView emailTextView;
    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView middleNameTextView;
    TextView mobileNumTextView;
    TextView dobTextView;
    TextView dojTextView;
    TextView genderTextView;
    TextView departmentTextView;
    TextView numAwardTextView;
    TextView numConferenceTextView;
    TextView numJournalTextView;
    TextView numWorkshopTextView;

    public HomeFragment() {}

    public HomeFragment(String username) {
        this.username = username;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BasicProfileViewModel.class);
        viewModel.init();
        viewModel.getBasicProfile(username);
        viewModel.getLiveData().observe(this,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_home_frag,container,false);
        setProgressBar(view);
        progressBar.show();
        initView(view);
        return view;
    }

    public void initView(View view){
        circleImageView = view.findViewById(R.id.pd_profile_pic);
        usernameTextView = view.findViewById(R.id.pd_username);
        emailTextView = view.findViewById(R.id.pd_email);
        firstNameTextView = view.findViewById(R.id.pd_first_name);
        middleNameTextView = view.findViewById(R.id.pd_middle_name);
        lastNameTextView = view.findViewById(R.id.pd_last_name);
        mobileNumTextView = view.findViewById(R.id.pd_mob_num);
        dobTextView = view.findViewById(R.id.pd_dob);
        dojTextView = view.findViewById(R.id.pd_doj);
        genderTextView = view.findViewById(R.id.pd_sex);
        departmentTextView = view.findViewById(R.id.pd_department);
        numAwardTextView = view.findViewById(R.id.pd_award_num);
        numConferenceTextView = view.findViewById(R.id.pd_conference_num);
        numJournalTextView = view.findViewById(R.id.pd_journal_num);
        numWorkshopTextView = view.findViewById(R.id.pd_workshop_num);
    }


    @Override
    public void onChanged(BasicProfileObject profileObject) {
        if(profileObject!=null){
            if(profileObject.getProfilePic() != null){
                Glide.with(getView()).load(profileObject.getProfilePic()).into(circleImageView);
            }
            else{
                circleImageView.setImageResource(R.drawable.default_profile_pic);
            }
            usernameTextView.setText(profileObject.getUsername());
            emailTextView.setText(profileObject.getEmail());
            firstNameTextView.setText(profileObject.getFirstName());
            middleNameTextView.setText(profileObject.getMiddleName());
            lastNameTextView.setText(profileObject.getLastName());
            mobileNumTextView.setText(profileObject.getMobileNum());
            dobTextView.setText(profileObject.getDob());
            dojTextView.setText(profileObject.getDoj());
            genderTextView.setText(GlobalVars.reverseGenderMap.get(profileObject.getSex()));
            departmentTextView.setText(profileObject.getDepartment());
            Integer num = 0;
            if(profileObject.getAwardSet() != null){
                num = profileObject.getAwardSet().size();
            }
            else{
                num = 0;
            }
            numAwardTextView.setText(Integer.toString(num));
            if(profileObject.getConferenceSets() != null){
                num = profileObject.getConferenceSets().size();
            }
            else{
                num = 0;
            }
            numConferenceTextView.setText(Integer.toString(num));
            if(profileObject.getJournalSets() != null){
                num = profileObject.getJournalSets().size();
            }
            else{
                num = 0;
            }
            numJournalTextView.setText(Integer.toString(num));
            if(profileObject.getWorkshopSets() != null){
                num = profileObject.getWorkshopSets().size();
            }
            else{
                num = 0;
            }
            numWorkshopTextView.setText(Integer.toString(num));
        }
        progressBar.dismiss();
    }

    public void setProgressBar(View view) {
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }
}
