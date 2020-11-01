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

public class JournalAdapterPublic extends RecyclerView.Adapter<JournalAdapterPublic.CardViewHolder>{
    private TeacherDetailPublic teacherDetailPublic;
    private List<TeacherDetailPublic.Journal> journals;
    private OnDownloadClick onDownloadClick;
    private String dowloadUrl;

    public JournalAdapterPublic(OnDownloadClick onDownloadClick){
        this.onDownloadClick = onDownloadClick;
    }

    public void setResults(TeacherDetailPublic teacherDetailPublic){
        this.teacherDetailPublic = teacherDetailPublic;
        this.journals = this.teacherDetailPublic.getJournalsList();

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_card_item,parent,false);
        return new CardViewHolder(view,onDownloadClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        TeacherDetailPublic.Journal journal = journals.get(position);
        holder.journalNameTextView.setText(journal.getJournalTitle());
        holder.paperTitleTextView.setText(journal.getPaperTitle());

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(journal.getJournalDate());
        holder.journalDateTextView.setText(date);

        holder.journalTypeTextView.setText(GlobalVars.paperTypes.get(journal.getJournalType()));
        holder.journalImpactFactorTextView.setText(journal.getImpactFactor());

        if(journal.getJournalCertificate() == null) {
            holder.downloadButton.setVisibility(View.GONE);
        }
        dowloadUrl = journal.getJournalCertificate();
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView journalNameTextView;
        public TextView paperTitleTextView;
        public TextView journalDateTextView;
        public TextView journalTypeTextView;
        public TextView journalImpactFactorTextView;
        public Button downloadButton;

        public OnDownloadClick onDownloadClick;

        public CardViewHolder(@NonNull View itemView,OnDownloadClick onDownloadClick) {
            super(itemView);
            journalNameTextView = itemView.findViewById(R.id.journal_name_public);
            paperTitleTextView = itemView.findViewById(R.id.journal_paper_title_public);
            journalDateTextView = itemView.findViewById(R.id.journal_date_public);
            journalTypeTextView = itemView.findViewById(R.id.journal_type_public);
            journalImpactFactorTextView = itemView.findViewById(R.id.journal_if_public);
            downloadButton = itemView.findViewById(R.id.journal_certificate_download_button);
            this.onDownloadClick = onDownloadClick;
            downloadButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDownloadClick.onDowloadButtonClick(JournalAdapterPublic.this.dowloadUrl);
        }
    }

    public interface OnDownloadClick {
        void onDowloadButtonClick(String url);
    }
}
