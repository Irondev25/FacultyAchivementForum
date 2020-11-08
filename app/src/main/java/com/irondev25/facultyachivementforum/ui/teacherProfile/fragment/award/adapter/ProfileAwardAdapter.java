package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.adapter;

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

import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.award.pojo.AwardObject;

import java.util.List;

public class ProfileAwardAdapter extends RecyclerView.Adapter<ProfileAwardAdapter.CardViewHolder> {
    private List<AwardObject> awards;
    private MyCardButtons myCardButtons;

    private String downloadUrl;

    public ProfileAwardAdapter(MyCardButtons myCardButtons) {
        this.myCardButtons = myCardButtons;
    }

    public void setResult(List<AwardObject> awards){
        this.awards = awards;
//        for(int i=0; i<7; i++){
//            this.awards.add(awards.get(0));
//        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_profile_award_card,
                parent,false);
        return new CardViewHolder(view);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        AwardObject award = awards.get(position);
        holder.awardTitleTextView.setText(award.getAwardTitle());

//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        String date = simpleDateFormat.format(award.getAwardDate());

        holder.awardDateTextView.setText(award.getAwardDate());
        if(award.getCertificate() == null) {
            holder.downloadButton.setVisibility(View.GONE);
        }
        holder.awardDetail.setText(award.getAwardDetails());
        downloadUrl = award.getCertificate();

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCardButtons.onDownloadButtonClick(downloadUrl);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCardButtons.onEditClickButton(award);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCardButtons.onDeleteClickButton(award);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(awards == null){
            return  0;
        }
        return awards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        public TextView awardTitleTextView;
        public TextView awardDateTextView;
        public TextView awardDetail;
        public Button downloadButton;
        public Button editButton;
        public Button deleteButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            awardTitleTextView = itemView.findViewById(R.id.teacher_profile_award_title);
            awardDateTextView = itemView.findViewById(R.id.teacher_profile_award_date);
            awardDetail = itemView.findViewById(R.id.teacher_profile_award_detail);
            downloadButton = itemView.findViewById(R.id.teacher_profile_certificate_download_button);
            editButton = itemView.findViewById(R.id.teacher_profile_edit_button);
            deleteButton = itemView.findViewById(R.id.teacher_profile_delete_button);
        }

//        @Override
//        public void onClick(View v) {
//            onDownloadClick.onDowloadButtonClick(ProfileAwardAdapter.this.dowloadUrl);
//        }
    }

    public interface MyCardButtons {
        void onDownloadButtonClick(String url);
        void onEditClickButton(AwardObject awardObject);
        void onDeleteClickButton(AwardObject awardObject);
    }
}
