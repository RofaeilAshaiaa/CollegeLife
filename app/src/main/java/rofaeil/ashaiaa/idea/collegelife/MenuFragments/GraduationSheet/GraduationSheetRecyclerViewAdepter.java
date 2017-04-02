package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationSheetData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.GraduationSheetSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.geIconBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.geIconRightCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.geTextBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.geTextLeftCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getSubjectOldIdBackgroundResource;

/**
 * Created by emad on 1/1/2017.
 */
public class GraduationSheetRecyclerViewAdepter extends RecyclerView.Adapter<GraduationSheetRecyclerViewAdepter.RecyclerViewHolder> {

    GraduationSheetData mGraduationSheetData;

    public GraduationSheetRecyclerViewAdepter(GraduationSheetData graduationSheetData) {
        this.mGraduationSheetData = graduationSheetData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.graduation_sheet_subject_list_item_temp, parent, false);
        RecyclerViewHolder mRecyclerViewHolder = new RecyclerViewHolder(mView);
        return mRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        GraduationSheetSubject subject = mGraduationSheetData.getSubject().get(position);
        int backgroundId = subject.getBackgroundId();
        int subjectTextBackgroundResource = geTextBackgroundResource(backgroundId);
        int subjectTextLeftCornerBackgroundResource = geTextLeftCornerBackgroundResource(backgroundId);

        holder.subject_old_id.setText(subject.getOldID());
        holder.subject_old_id.setBackgroundResource(getSubjectOldIdBackgroundResource(backgroundId));
        holder.subject_name.setText(subject.getName());
        holder.subject_category.setText(subject.getCategory());
        holder.subject_hours.setText(subject.getHours());
        holder.subject_semester.setText(subject.getSemester());
        holder.subject_earned_grade.setText(subject.getEarnedGrade());
        holder.subject_actual_grade.setText(subject.getActualGrade());
        holder.subject_point.setText(subject.getPoints());
        holder.subject_nodes.setText(subject.getNodes());

        holder.subject_semester_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_earned_grade_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_actual_grade_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_nodes_logo.setBackgroundResource(subjectTextBackgroundResource);
        holder.subject_point_text_logo.setBackgroundResource(subjectTextLeftCornerBackgroundResource);
        holder.subject_hours_logo.setBackgroundResource(geIconBackgroundResource(backgroundId));
        holder.subject_point_icon_logo.setBackgroundResource(geIconRightCornerBackgroundResource(backgroundId));

    }

    @Override
    public int getItemCount() {
        return mGraduationSheetData.getSubject().size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView subject_old_id;
        TextView subject_name;
        TextView subject_category;
        TextView subject_hours;
        TextView subject_semester;
        TextView subject_earned_grade;
        TextView subject_actual_grade;
        TextView subject_point;
        TextView subject_nodes;

        TextView subject_semester_logo;
        ImageView subject_hours_logo;
        TextView subject_earned_grade_logo;
        TextView subject_actual_grade_logo;
        ImageView subject_point_icon_logo;
        TextView subject_point_text_logo;
        TextView subject_nodes_logo;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            CardView mCardView = (CardView) itemView.findViewById(R.id.graduation_sheet_subject_card_view);
            subject_old_id = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_old_id_logo);
            subject_name = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_name);
            subject_category = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_category);
            subject_hours = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_hours_num);
            subject_semester = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_semester_name);
            subject_earned_grade = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_earned_grade_val);
            subject_actual_grade = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_actual_grade_val);
            subject_point = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_point_num);
            subject_nodes = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_nodes_val);

            subject_semester_logo = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_semester_logo);
            subject_earned_grade_logo = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_earned_grade_logo);
            subject_actual_grade_logo = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_actual_grade_logo);
            subject_point_text_logo = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_point_text_logo);
            subject_nodes_logo = (TextView) mCardView.findViewById(R.id.graduation_sheet_subject_nodes_logo);
            subject_hours_logo = (ImageView) mCardView.findViewById(R.id.graduation_sheet_subject_hours_logo);
            subject_point_icon_logo = (ImageView) mCardView.findViewById(R.id.graduation_sheet_subject_point_icon_logo);

        }
    }

}
