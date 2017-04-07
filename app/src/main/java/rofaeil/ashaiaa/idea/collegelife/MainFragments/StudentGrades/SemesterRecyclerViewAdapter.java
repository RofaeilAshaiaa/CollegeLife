package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades.SemesterSubject.SemesterSubjectActivity;
import rofaeil.ashaiaa.idea.collegelife.R;

/**
 * Created by emad on 1/1/2017.
 */
public class SemesterRecyclerViewAdapter extends RecyclerView.Adapter<SemesterRecyclerViewAdapter.RecyclerViewHolder> {

    public ArrayList<Semester> mSemesters;
    public Context mContext;

    public SemesterRecyclerViewAdapter(ArrayList<Semester> semesters, Context Context) {
        this.mSemesters = semesters;
        this.mContext = Context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_grades_semester_list_item_temp, parent, false);
        RecyclerViewHolder mRecyclerViewHolder = new RecyclerViewHolder(mView, mContext, mSemesters);
        return mRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Semester semester = mSemesters.get(position);
        int semesterTextBackgroundResource = semester.getTextBackgroundResource();
        int semesterTextLeftCornerBackgroundResource = semester.getTextLeftCornerBackgroundResource();
        int semesterIconRightCornerBackgroundResource = semester.getIconRightCornerBackgroundResource();

        holder.semester_name.setText(semester.getName());
//        holder.subject_num.setText("" + semester.getSubjects().size() + "");
        holder.semester_cgpa.setText(semester.getCGPA());
        holder.semester_gpa.setText(semester.getGPA());
        holder.semester_loaded_hours.setText(semester.getSemesterLoad());
        holder.semester_earned_hours.setText(semester.getEarnedHours());

        holder.semester_logo.setBackgroundResource(semester.getLogoBackgroundResource());
        holder.semester_cgpa_logo.setBackgroundResource(semesterTextBackgroundResource);
        holder.semester_gpa_logo.setBackgroundResource(semesterTextBackgroundResource);
        holder.semester_loaded_hours_logo.setBackgroundResource(semesterTextBackgroundResource);
        holder.semester_earned_hours_text_logo.setBackgroundResource(semesterTextLeftCornerBackgroundResource);
        holder.subject_num_text_logo.setBackgroundResource(semesterTextLeftCornerBackgroundResource);
        holder.semester_earned_hours_icon_logo.setBackgroundResource(semesterIconRightCornerBackgroundResource);
        holder.subject_num_icon_logo.setBackgroundResource(semesterIconRightCornerBackgroundResource);

    }

    @Override
    public int getItemCount() {
        return mSemesters.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView semester_name;
        TextView subject_num;
        TextView semester_cgpa;
        TextView semester_gpa;
        TextView semester_loaded_hours;
        TextView semester_earned_hours;
        CardView mCardView;

        ImageView subject_num_icon_logo;
        TextView subject_num_text_logo;
        TextView semester_cgpa_logo;
        TextView semester_gpa_logo;
        TextView semester_loaded_hours_logo;
        TextView semester_earned_hours_text_logo;
        ImageView semester_earned_hours_icon_logo;
        ImageView semester_logo;


        public RecyclerViewHolder(View itemView, final Context mContext, final ArrayList<Semester> mSemesters) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.student_grades_semester_card_view);
            semester_name = (TextView) mCardView.findViewById(R.id.student_grades_semester_name);
            subject_num = (TextView) mCardView.findViewById(R.id.student_grades_semester_subjects_num);
            semester_cgpa = (TextView) mCardView.findViewById(R.id.student_grades_semester_cgpa_num);
            semester_gpa = (TextView) mCardView.findViewById(R.id.student_grades_semester_gpa_num);
            semester_loaded_hours = (TextView) mCardView.findViewById(R.id.student_grades_semester_loaded_hours_num);
            semester_earned_hours = (TextView) mCardView.findViewById(R.id.student_grades_semester_earned_hours_num);

            subject_num_icon_logo = (ImageView) mCardView.findViewById(R.id.student_grades_semester_subjects_icon_logo);
            subject_num_text_logo = (TextView) mCardView.findViewById(R.id.student_grades_semester_subjects_num_text_logo);
            semester_cgpa_logo = (TextView) mCardView.findViewById(R.id.student_grades_semester_cgpa_logo);
            semester_gpa_logo = (TextView) mCardView.findViewById(R.id.student_grades_semester_gpa_logo);
            semester_loaded_hours_logo = (TextView) mCardView.findViewById(R.id.student_grades_semester_loaded_hours_logo);
            semester_earned_hours_text_logo = (TextView) mCardView.findViewById(R.id.student_grades_semester_earned_hours_text_logo);
            semester_earned_hours_icon_logo = (ImageView) mCardView.findViewById(R.id.student_grades_semester_earned_hours_icon_logo);
            semester_logo = (ImageView) mCardView.findViewById(R.id.student_grades_semester_logo);


            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, SemesterSubjectActivity.class);
                    if (getLayoutPosition() > 0) {
                        mIntent.putExtra("PreviousSemesterCGPA", mSemesters.get(getLayoutPosition() - 1).getCGPA());
                    } else {
                        mIntent.putExtra("PreviousSemesterCGPA", mSemesters.get(getLayoutPosition()).getCGPA());
                    }
                    if (mSemesters.size() - 1 == getLayoutPosition()) {
                        mIntent.putExtra("LastSemester", true);
                    } else {
                        mIntent.putExtra("LastSemester", false);
                    }
                    mIntent.putExtra("Name", mSemesters.get(getLayoutPosition()).getName());
                    mIntent.putExtra("CGPA", mSemesters.get(getLayoutPosition()).getCGPA());
                    mIntent.putExtra("GPA", mSemesters.get(getLayoutPosition()).getGPA());
                    mIntent.putExtra("SemesterLoad", mSemesters.get(getLayoutPosition()).getSemesterLoad());
                    mIntent.putExtra("EarnedHours", mSemesters.get(getLayoutPosition()).getEarnedHours());
                    mIntent.putExtra("SubjectsDocument", mSemesters.get(getLayoutPosition()).getSubjectsDocument());
                    mContext.startActivity(mIntent);
                }
            });
        }
    }
}
