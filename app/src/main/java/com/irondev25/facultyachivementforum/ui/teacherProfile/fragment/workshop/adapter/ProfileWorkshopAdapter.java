package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.fragments.adapter.WorkshopAdapterPublic;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.workshop.pojo.WorkshopObject;

import java.util.List;

public class ProfileWorkshopAdapter extends RecyclerView.Adapter<ProfileWorkshopAdapter.CardItemHolder>{
    private List<WorkshopObject> workshops;
    private MyCardButtons myCardButtons;
    private String downloadUrl;

    public ProfileWorkshopAdapter(MyCardButtons myCardButtons) {
        this.myCardButtons = myCardButtons;
    }

    public void setResult(List<WorkshopObject> workshops){
        this.workshops = workshops;
//        for(int i=0; i<7; i++){
//            this.workshops.add(workshops.get(0));
//        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_profile_workshop_card,
                parent,false);
        return new CardItemHolder(view, myCardButtons);
    }

    @Override
    public void onBindViewHolder(@NonNull CardItemHolder holder, int position) {
        WorkshopObject workshop = workshops.get(position);
        holder.workshopTitleTextView.setText(workshop.getTopic());
        holder.workshopLocationTextView.setText(workshop.getLocation());
        holder.workshopDateTextView.setText(workshop.getDate());
        holder.workshopTypeTextView.setText(GlobalVars.paperTypes.get(workshop.getWorkshopType()));
        if(workshop.getCertificate() == null){
            holder.downloadButton.setVisibility(View.GONE);
        }
        downloadUrl = workshop.getCertificate();
    }

    @Override
    public int getItemCount() {
        if(workshops == null) {
            return 0;
        }
        return workshops.size();
    }

    public class CardItemHolder extends RecyclerView.ViewHolder {
        public TextView workshopTitleTextView;
        public TextView workshopLocationTextView;
        public TextView workshopDateTextView;
        public TextView workshopTypeTextView;
        public Button downloadButton;
        public Button editButton;
        public Button deleteButton;

        public MyCardButtons myCardButtons;

        public WorkshopAdapterPublic.OnDownloadClick onDownloadClick;

        public CardItemHolder(@NonNull View itemView, MyCardButtons myCardButtons) {
            super(itemView);
            workshopTitleTextView = itemView.findViewById(R.id.teacher_profile_workshop_name);
            workshopLocationTextView = itemView.findViewById(R.id.teacher_profile_workshop_location);
            workshopDateTextView = itemView.findViewById(R.id.teacher_profile_workshop_date);
            workshopTypeTextView = itemView.findViewById(R.id.teacher_profile_workshop_type);
            downloadButton = itemView.findViewById(R.id.teacher_profile_workshop_certificate_download_button);
            editButton = itemView.findViewById(R.id.teacher_profile_workshop_edit_button);
            deleteButton = itemView.findViewById(R.id.teacher_profile_workshop_delete_button);
            this.myCardButtons = myCardButtons;
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCardButtons.onDownloadButtonClick(downloadUrl);
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCardButtons.onEditClickButton();
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
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
