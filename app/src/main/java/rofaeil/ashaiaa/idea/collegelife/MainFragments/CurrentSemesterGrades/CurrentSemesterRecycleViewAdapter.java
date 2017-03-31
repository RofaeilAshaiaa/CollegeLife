package rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.CurrentSemesterGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

/**
 * Created by emad on 1/2/2017.
 */

public class CurrentSemesterRecycleViewAdapter extends RecyclerView.Adapter<CurrentSemesterRecycleViewAdapter.RecycleViewHolder> {
    ArrayList<CurrentSemesterGradesSubject> mSubjects;


    public CurrentSemesterRecycleViewAdapter(ArrayList<CurrentSemesterGradesSubject> subjects) {
        this.mSubjects = subjects;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_semester_list_item_temp, parent, false);
        RecycleViewHolder mRecycleViewHolder = new RecycleViewHolder(mView);
        return mRecycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {

        CurrentSemesterGradesSubject subject = mSubjects.get(position);
        int subjectTextBackgroundResource = subject.getTextBackgroundResource();

        holder.subject_old_id.setText(subject.getOldID());
        holder.subject_name.setText(subject.getName());
        holder.subject_midTerm_num.setText(subject.getMidTerm());
        holder.subject_oral_num.setText(subject.getOral());
        holder.subject_total_term_num.setText(subject.getTotalTerm());
        holder.subject_lab_num.setText(subject.getLab());
        holder.subject_final_num.setText(subject.getFinal());
        holder.subject_total_num.setText(subject.getTotal());

        holder.subject_old_id.setBackgroundResource(subject.getOldIdBackgroundResource());
        holder.subject_midTerm_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_oral_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_total_term_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_lab_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_final_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_total_logo.setBackgroundResource(subjectTextBackgroundResource);
    }


    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView subject_old_id;
        TextView subject_name;
        TextView subject_midTerm_num;
        TextView subject_oral_num;
        TextView subject_total_term_num;
        TextView subject_lab_num;
        TextView subject_final_num;
        TextView subject_total_num;

        TextView subject_midTerm_logo;
        TextView subject_oral_logo;
        TextView subject_total_term_logo;
        TextView subject_lab_logo;
        TextView subject_final_logo;
        TextView subject_total_logo;


        public RecycleViewHolder(View itemView) {
            super(itemView);
            CardView mCardView = (CardView) itemView.findViewById(R.id.current_semester_card_view);
            subject_old_id = (TextView) mCardView.findViewById(R.id.current_semester_old_id_logo);
            subject_name = (TextView) mCardView.findViewById(R.id.current_semester_name);
            subject_midTerm_num = (TextView) mCardView.findViewById(R.id.current_semester_midterm_num);
            subject_oral_num = (TextView) mCardView.findViewById(R.id.current_semester_oral_num);
            subject_total_term_num = (TextView) mCardView.findViewById(R.id.current_semester_total_term_num);
            subject_lab_num = (TextView) mCardView.findViewById(R.id.current_semester_lab_num);
            subject_final_num = (TextView) mCardView.findViewById(R.id.current_semester_final_num);
            subject_total_num = (TextView) mCardView.findViewById(R.id.current_semester_total_num);

            subject_midTerm_logo = (TextView) mCardView.findViewById(R.id.current_semester_midterm_logo);
            subject_oral_logo = (TextView) mCardView.findViewById(R.id.current_semester_oral_logo);
            subject_total_term_logo = (TextView) mCardView.findViewById(R.id.current_semester_total_term_logo);
            subject_lab_logo = (TextView) mCardView.findViewById(R.id.current_semester_lab_logo);
            subject_final_logo = (TextView) mCardView.findViewById(R.id.current_semester_final_logo);
            subject_total_logo = (TextView) mCardView.findViewById(R.id.current_semester_total_logo);
        }
    }


}
