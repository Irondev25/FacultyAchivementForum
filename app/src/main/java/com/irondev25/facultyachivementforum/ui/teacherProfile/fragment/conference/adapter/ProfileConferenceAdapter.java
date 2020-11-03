package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.conference.pojo.ConferenceObject;

import java.util.List;

public class ProfileConferenceAdapter extends RecyclerView.Adapter<ProfileConferenceAdapter.CardViewHolder>{
    private List<ConferenceObject> conferences;
    private MyCardButtons myCardButtons;

    String downloadUrl;

    public ProfileConferenceAdapter(MyCardButtons myCardButtons){
        this.myCardButtons = myCardButtons;
    }

    public void setResult(List<ConferenceObject> conferences){
        this.conferences = conferences;
        for(int i=0; i<7; i++){
            this.conferences.add(conferences.get(0));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_profile_conference_card,parent,false);
        return new CardViewHolder(view,myCardButtons);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ConferenceObject conference = conferences.get(position);
        holder.conferenceName.setText(conference.getConferenceName());
        holder.paperTitle.setText(conference.getConferencePaperTitle());
        holder.conferenceDate.setText(conference.getConferenceDate());
        if(conference.getCertificate() == null) {
            holder.certificateDownload.setVisibility(View.GONE);
        }
        downloadUrl = conference.getCertificate();
    }

    @Override
    public int getItemCount() {
        if(conferences == null){
            return 0;
        }
        return conferences.size();
    }


    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView conferenceName;
        public TextView paperTitle;
        public TextView conferenceDate;
        public TextView conferenceType;
        public Button certificateDownload;
        public Button editConference;
        public Button deleteConference;

        public MyCardButtons myCardButtons;


        public CardViewHolder(@NonNull View itemView,MyCardButtons myCardButtons) {
            super(itemView);
            conferenceName = itemView.findViewById(R.id.teacher_profile_conference_name);
            paperTitle = itemView.findViewById(R.id.teacher_profile_conference_paper_title);
            conferenceDate = itemView.findViewById(R.id.teacher_profile_conference_date);
            conferenceType = itemView.findViewById(R.id.teacher_profile_conference_type);
            certificateDownload = itemView.findViewById(R.id.teacher_profile_conference_certificate_download);
            editConference = itemView.findViewById(R.id.teacher_profile_conference_edit_button);
            deleteConference = itemView.findViewById(R.id.teacher_profile_conference_delete_button);

            this.myCardButtons = myCardButtons;
            certificateDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCardButtons.onDownloadButtonClick(downloadUrl);
                }
            });

            editConference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCardButtons.onEditClickButton();
                }
            });

            deleteConference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCardButtons.onDeleteClickButton();
                }
            });
        }
    }

    public interface MyCardButtons {
        void onDownloadButtonClick(String url);
        void onEditClickButton();
        void onDeleteClickButton();
    }
}
