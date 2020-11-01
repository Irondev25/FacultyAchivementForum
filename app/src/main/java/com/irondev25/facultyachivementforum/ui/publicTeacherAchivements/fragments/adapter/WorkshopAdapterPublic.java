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

public class WorkshopAdapterPublic extends RecyclerView.Adapter<WorkshopAdapterPublic.CardItemHolder> {
    private TeacherDetailPublic teacherDetailPublic;
    private List<TeacherDetailPublic.Workshop> workshops;
    private OnDownloadClick onDownloadClick;
    private String dowloadUrl;

    public WorkshopAdapterPublic(OnDownloadClick onDownloadClick) {
        this.onDownloadClick = onDownloadClick;
    }

    public void setResult(TeacherDetailPublic teacherDetailPublic){
        this.teacherDetailPublic = teacherDetailPublic;
        workshops = this.teacherDetailPublic.getWorkshopList();
    }

    @NonNull
    @Override
    public CardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_card_item,parent,false);
        return new CardItemHolder(view, onDownloadClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CardItemHolder holder, int position) {
        TeacherDetailPublic.Workshop workshop = workshops.get(position);
        holder.workshopTitleTextView.setText(workshop.getWorkshopTopic());
        holder.workshopLocationTextView.setText(workshop.getWorkshopLocation());

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(workshop.getWorkshopDate());

        holder.workshopDateTextView.setText(date);

        holder.workshopTypeTextView.setText(GlobalVars.paperTypes.get(workshop.getWorkshopType()));

        if(workshop.getWorkshopCertificate() == null) {
            holder.downloadButton.setVisibility(View.GONE);
        }
        dowloadUrl = workshop.getWorkshopCertificate();
    }

    @Override
    public int getItemCount() {
        return workshops.size();
    }

    public class CardItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView workshopTitleTextView;
        public TextView workshopLocationTextView;
        public TextView workshopDateTextView;
        public TextView workshopTypeTextView;
        public Button downloadButton;

        public OnDownloadClick onDownloadClick;

        public CardItemHolder(@NonNull View itemView, OnDownloadClick onDownloadClick) {
            super(itemView);
            workshopTitleTextView = itemView.findViewById(R.id.workshop_name_public);
            workshopLocationTextView = itemView.findViewById(R.id.workshop_location_public);
            workshopDateTextView = itemView.findViewById(R.id.workshop_date_public);
            workshopTypeTextView = itemView.findViewById(R.id.workshop_type_public);
            downloadButton = itemView.findViewById(R.id.workshop_certificate_download_button);
            this.onDownloadClick = onDownloadClick;
            downloadButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDownloadClick.onDowloadButtonClick(WorkshopAdapterPublic.this.dowloadUrl);
        }
    }

    public interface OnDownloadClick {
        void onDowloadButtonClick(String url);
    }
}

