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

import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.publicTeacherAchivements.pojo.TeacherDetailPublic;

import java.util.List;

public class AwardAdapterPublic extends RecyclerView.Adapter<AwardAdapterPublic.CardViewHolder>  {
    private TeacherDetailPublic teacherDetailPublic;
    private List<TeacherDetailPublic.Award> awards;
    private OnDownloadClick onDownloadClick;
    private String dowloadUrl;

    public AwardAdapterPublic(OnDownloadClick onDownloadClick){
        this.onDownloadClick = onDownloadClick;
    }

    public void setResult(TeacherDetailPublic teacherDetailPublic){
        this.teacherDetailPublic = teacherDetailPublic;
        this.awards = teacherDetailPublic.getAwardsList();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_card_item,parent,false);
        return new CardViewHolder(cardItemView, onDownloadClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        TeacherDetailPublic.Award award = awards.get(position);
        holder.awardTitleTextView.setText(award.getTitle());

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(award.getDate());
        holder.awardDateTextView.setText(date);
        if(award.getCertificate() == null){
            holder.downloadButton.setVisibility(View.GONE);
        }
        dowloadUrl = award.getCertificate();
    }

    @Override
    public int getItemCount() {
        return awards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView awardTitleTextView;
        public TextView awardDateTextView;
        public TextView awardDetail;
        public Button downloadButton;
        public OnDownloadClick onDownloadClick;

        public CardViewHolder(@NonNull View itemView, OnDownloadClick onDownloadClick) {
            super(itemView);
            awardTitleTextView = itemView.findViewById(R.id.award_title_public);
            awardDateTextView = itemView.findViewById(R.id.award_date_public);
            awardDetail = itemView.findViewById(R.id.award_detail_public);
            downloadButton = itemView.findViewById(R.id.certificate_download_button);
            this.onDownloadClick = onDownloadClick;
            downloadButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDownloadClick.onDowloadButtonClick(AwardAdapterPublic.this.dowloadUrl);
        }
    }

    public interface OnDownloadClick {
        void onDowloadButtonClick(String url);
    }
}
