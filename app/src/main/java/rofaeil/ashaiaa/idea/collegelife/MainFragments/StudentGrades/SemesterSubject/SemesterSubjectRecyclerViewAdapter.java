package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades.SemesterSubject;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

/**
 * Created by emad on 1/28/2017.
 */
public class SemesterSubjectRecyclerViewAdapter extends RecyclerView.Adapter<SemesterSubjectRecyclerViewAdapter.RecyclerViewHolder> {

    public ArrayList<StudentGradesSubject> mSubjects;

    public SemesterSubjectRecyclerViewAdapter(ArrayList<StudentGradesSubject> Subjects) {
        this.mSubjects = Subjects;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_grades_subject_list_item_temp, parent, false);
        RecyclerViewHolder mRecyclerViewHolder = new RecyclerViewHolder(mView);
        return mRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        StudentGradesSubject subject = mSubjects.get(position);
        holder.subject_name.setText(subject.getName());
        holder.subject_old_id.setText(subject.getOldID());
        holder.subject_point.setText(subject.getPoints());
        holder.subject_hours.setText(subject.getHours());
        if (mSubjects.get(position).getGrade().equals("إستبيان")) {
            holder.subject_grade.setText("P");
        } else {
            holder.subject_grade.setText(mSubjects.get(position).getGrade());
        }

        holder.subject_old_id.setBackgroundResource(subject.getOldIdBackgroundResource());
        holder.subject_hours_logo.setBackgroundResource(subject.getIconBackgroundResource());
        holder.subject_point_text_logo.setBackgroundResource(subject.getLeftCornerBackgroundResource());
        holder.subject_point_icon_logo.setBackgroundResource(subject.getIconRightCornerBackgroundResource());
        holder.subject_grade_logo.setBackgroundResource(subject.getTextBackgroundResource());
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView subject_old_id;
        TextView subject_hours;
        TextView subject_name;
        TextView subject_point;
        TextView subject_grade;

        ImageView subject_hours_logo;
        TextView subject_point_text_logo;
        ImageView subject_point_icon_logo;
        TextView subject_grade_logo;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            CardView mCardView = (CardView) itemView.findViewById(R.id.student_grades_subject_card_view);
            subject_name = (TextView) mCardView.findViewById(R.id.student_grades_subject_name);
            subject_old_id = (TextView) mCardView.findViewById(R.id.student_grades_subject_old_id_logo);
            subject_point = (TextView) mCardView.findViewById(R.id.student_grades_subject_point_num);
            subject_grade = (TextView) mCardView.findViewById(R.id.student_grades_subject_grade);
            subject_hours = (TextView) mCardView.findViewById(R.id.student_grades_subject_hours_num);

            subject_hours_logo = (ImageView) mCardView.findViewById(R.id.student_grades_subject_hours_logo);
            subject_point_text_logo = (TextView) mCardView.findViewById(R.id.student_grades_subject_point_text_logo);
            subject_point_icon_logo = (ImageView) mCardView.findViewById(R.id.student_grades_subject_point_logo);
            subject_grade_logo = (TextView) mCardView.findViewById(R.id.student_grades_subject_grade);

        }
    }
}
