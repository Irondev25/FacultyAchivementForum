package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.adapter.JournalAdapterPublic;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;

public class JournalPublic extends Fragment implements JournalAdapterPublic.OnDownloadClick{
    private JournalAdapterPublic adapter;
    public TeacherDetailPublic teacherDetailPublic;

    private static final String TAG = "JournalPublic";

    public JournalPublic(TeacherDetailPublic teacherDetailPublic){
        this.teacherDetailPublic = teacherDetailPublic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_journal_frag,container,false);
        adapter = new JournalAdapterPublic(this);
        adapter.setResults(teacherDetailPublic);
        RecyclerView recyclerView = view.findViewById(R.id.journal_public_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDowloadButtonClick(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}

