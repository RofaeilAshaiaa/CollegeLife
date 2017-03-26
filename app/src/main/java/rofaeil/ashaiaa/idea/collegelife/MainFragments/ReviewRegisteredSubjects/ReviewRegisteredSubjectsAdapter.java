package rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

public class ReviewRegisteredSubjectsAdapter extends RecyclerView.Adapter<ReviewRegisteredSubjectsAdapter.myViewHolder> {

    LayoutInflater inflater;
    private List<StudentGradesSubject> subjectArrayList;
    private Context mContext;
    private int mItemsNumber;

    public ReviewRegisteredSubjectsAdapter(Context context, ArrayList<StudentGradesSubject> subjectArrayList) {
        this.subjectArrayList = subjectArrayList;
        mContext = context;
        inflater = LayoutInflater.from(context);
        mItemsNumber = subjectArrayList.size();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.review_registered_subjects_list_item, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.subject_name.setText(subjectArrayList.get(position).getName());
        holder.short_code.setText(subjectArrayList.get(position).getOldID());
        holder.number_of_hours.setText(subjectArrayList.get(position).getHours());

    }

    @Override
    public int getItemCount() {
        return mItemsNumber;
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView subject_name;
        public TextView short_code;
        public TextView number_of_hours;

        public myViewHolder(View itemView) {
            super(itemView);

            subject_name = (TextView) itemView.findViewById(R.id.review_registered_subject_name);
            short_code = (TextView) itemView.findViewById(R.id.review_registered_subject_old_id_logo);
            number_of_hours = (TextView) itemView.findViewById(R.id.review_registered_subject_hours_num);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
        }
    }
}
