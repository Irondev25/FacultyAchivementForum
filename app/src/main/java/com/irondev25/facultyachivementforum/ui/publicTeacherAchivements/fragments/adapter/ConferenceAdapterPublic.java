package com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.adapter;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;

import java.util.List;

public class ConferenceAdapterPublic extends RecyclerView.Adapter<ConferenceAdapterPublic.CardViewHolder> {
    private TeacherDetailPublic teacherDetailPublic;
    private List<TeacherDetailPublic.Conference> conferences;
    private OnDownloadClick onDownloadClick;
    private String downloadUrl;

    public ConferenceAdapterPublic(OnDownloadClick onDownloadClick) {
        this.onDownloadClick = onDownloadClick;
    }

    public void setResult(TeacherDetailPublic teacherDetailPublic) {
        this.teacherDetailPublic = teacherDetailPublic;
        conferences = this.teacherDetailPublic.getConferencesList();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.conference_card_item,parent,false);
        return new CardViewHolder(cardItemView,onDownloadClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        TeacherDetailPublic.Conference conference = conferences.get(position);

        holder.conferenceNameTextView.setText(conference.getConferenceName());
        holder.paperTitleTextView.setText(conference.getConferenceTitle());

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(conference.getConferenceDate());
        holder.conferenceDateTextView.setText(date);

        holder.conferenceTypeTextView.setText(GlobalVars.paperTypes.get(conference.getConferenceType()));

        if(conference.getConferenceCertificate() == null){
            holder.downloadButton.setVisibility(View.GONE);
        }
        downloadUrl = conference.getConferenceCertificate();
    }

    @Override
    public int getItemCount() {
        return conferences.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView conferenceNameTextView;
        public TextView paperTitleTextView;
        public TextView conferenceDateTextView;
        public TextView conferenceTypeTextView;
        public Button downloadButton;
        public OnDownloadClick onDownloadClick;

        public CardViewHolder(@NonNull View itemView, OnDownloadClick onDownloadClick) {
            super(itemView);
            conferenceNameTextView = itemView.findViewById(R.id.conference_name_public);
            paperTitleTextView = itemView.findViewById(R.id.conference_paper_title_public);
            conferenceDateTextView = itemView.findViewById(R.id.conference_date_public);
            conferenceTypeTextView = itemView.findViewById(R.id.conference_type_public);
            downloadButton = itemView.findViewById(R.id.conference_certificate_download_button);
            this.onDownloadClick = onDownloadClick;
            downloadButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDownloadClick.onDowloadButtonClick(ConferenceAdapterPublic.this.downloadUrl);
        }
    }

    public interface OnDownloadClick {
        void onDowloadButtonClick(String url);
    }
}

