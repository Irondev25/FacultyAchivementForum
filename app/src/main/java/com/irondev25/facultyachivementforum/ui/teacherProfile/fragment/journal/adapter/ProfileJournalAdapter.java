package com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irondev25.facultyachivementforum.GlobalVars;
import com.irondev25.facultyachivementforum.R;
import com.irondev25.facultyachivementforum.ui.teacherProfile.fragment.journal.pojo.JournalObject;

import java.util.List;

public class ProfileJournalAdapter extends RecyclerView.Adapter<ProfileJournalAdapter.CardViewHolder>{
    private String downloadUrl;
    private static final String TAG = "ProfileJournalAdapter";

    private List<JournalObject> journals;
    private MyCardButtons myCardButtons;

    public ProfileJournalAdapter(MyCardButtons myCardButtons){
        this.myCardButtons = myCardButtons;
    }

    public void setResult(List<JournalObject> journals){
        this.journals = journals;
//        for(int i=0; i<7; i++){
//            this.journals.add(journals.get(0));
//        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_profile_journal_card,parent,false);
        return new CardViewHolder(view, myCardButtons);
    }

    @Override
    public int getItemCount() {
        if(journals == null){
            return 0;
        }
        return journals.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        JournalObject journal = journals.get(position);
        holder.journalNameTextView.setText(journal.getJournalTitle());
        holder.paperTitleTextView.setText(journal.getJournalPaperTitle());
        holder.journalDateTextView.setText(journal.getJournalDate());
        holder.journalTypeTextView.setText(GlobalVars.paperTypes.get(journal.getJournalType()));
        holder.journalImpactFactorTextView.setText(
                journal.getJournalImpactFactor()
        );
        if(journal.getCertificate() == null) {
            holder.downloadButton.setVisibility(View.GONE);
        }
        downloadUrl = journal.getCertificate();
    }

    public interface MyCardButtons {
        void onDownloadButtonClick(String url);
        void onEditClickButton();
        void onDeleteClickButton();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView journalNameTextView;
        public TextView paperTitleTextView;
        public TextView journalDateTextView;
        public TextView journalTypeTextView;
        public TextView journalImpactFactorTextView;
        public Button downloadButton;
        public Button editButton;
        public Button deleteButton;
        public MyCardButtons myCardButtons;

        public CardViewHolder(@NonNull View itemView, MyCardButtons myCardButtons) {
            super(itemView);
            journalNameTextView = itemView.findViewById(R.id.teacher_profile_journal_name);
            paperTitleTextView = itemView.findViewById(R.id.teacher_profile_journal_paper_title);
            journalDateTextView = itemView.findViewById(R.id.teacher_profile_journal_date);
            journalTypeTextView = itemView.findViewById(R.id.teacher_profile_journal_type);
            journalImpactFactorTextView = itemView.findViewById(R.id.teacher_profile_journal_if);
            downloadButton = itemView.findViewById(R.id.teacher_profile_journal_certificate_download_button);
            editButton = itemView.findViewById(R.id.teacher_profile_journal_edit_button);
            deleteButton = itemView.findViewById(R.id.teacher_profile_journal_delete_button);
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
}
