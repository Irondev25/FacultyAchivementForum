package com.irondev25.facultyachivementforum.ui.browseAchivement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.browseAchivement.adapter.TeacherItemAdapater;
import com.irondev25.facultyachivementforum.ui.browseAchivement.pojo.TeacherItem;
import com.irondev25.facultyachivementforum.ui.browseAchivement.viewModel.TeacherListViewModel;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.TeacherAchivements;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AchivementExplorer extends AppCompatActivity implements TeacherItemAdapater.OnTeacherItemClick {
    private TeacherListViewModel viewModel;
    private TeacherItemAdapater adapater;

    private AutoCompleteTextView deptListSpinner;
    private TextInputLayout firstName;
    private Button button;
    private Integer deptVal = 0;
    private String firstNameString;

    private HashMap<String, Integer> deptPairList;
    private List<String> deptList;
    private List<TeacherItem> teacherItemList;

    private static final String TAG = "AchivementExplorer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement_explorer);

        deptPairList = GlobalVars.getDeptMap();
        deptList = GlobalVars.getDeptList();
        deptPairList.put("All",0);
        deptList.add(0,"All");

        adapater = new TeacherItemAdapater(this);

        viewModel = ViewModelProviders.of(this).get(TeacherListViewModel.class);
        viewModel.init();

        viewModel.getTeacherListResponseLiveData().observe(this, new Observer<List<TeacherItem>>() {
            @Override
            public void onChanged(List<TeacherItem> teacherItems) {
                if(teacherItems!=null){
                    teacherItemList = teacherItems;
                    adapater.setResults(teacherItems);
                }
            }
        });

        initView();
    }

    void initView(){
        deptListSpinner = findViewById(R.id.deptList);
//        deptListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                deptVal = deptPairList.get(deptList.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,deptList);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        deptListSpinner.setAdapter(aa);
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_menu_popup_item,deptList);
        deptListSpinner.setAdapter(aa);
        deptListSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deptVal = deptPairList.get(deptList.get(position));
            }
        });

        firstName = findViewById(R.id.serach_by_first_name);
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    firstNameString = firstName.getEditText().getText().toString();
                }
            }
        });
        button = findViewById(R.id.search_button);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapater);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = firstName.getEditText().getText().toString();
                Pattern pattern = Pattern.compile("[A-Za-z]*");
                Matcher matcher = pattern.matcher(text);
                if(!matcher.matches()){
                    firstName.setError("Invalid name input (must only contain aplhabets)");
                    return;
                }
                firstNameString = text;
                performSearch();
            }
        });
    }

    public void performSearch(){
        Integer deptId = null;
        if(deptVal != 0){
            deptId = deptVal;
        }
        String fName = null;
        if(firstNameString !=null && firstNameString.length() > 0){
            fName = firstNameString;
        }
        viewModel.getTeacherList(deptId,fName);
    }

    @Override
    public void onTItemClick(int position) {
        Intent publicTeacherDetail = new Intent(getApplicationContext(), TeacherAchivements.class);
        String profileUrl = teacherItemList.get(position).getProfileUrl();
        String fullName = teacherItemList.get(position).getFullName();
        publicTeacherDetail.putExtra("profile_url",profileUrl);
        publicTeacherDetail.putExtra("name", fullName);
        Log.d(TAG, "onTItemClick: " + profileUrl);
        startActivity(publicTeacherDetail);
    }
}