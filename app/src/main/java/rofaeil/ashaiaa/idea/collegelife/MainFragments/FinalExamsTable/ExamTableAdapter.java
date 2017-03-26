package rofaeil.ashaiaa.idea.collegelife.MainFragments.FinalExamsTable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.ExamTableTimeSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

public class ExamTableAdapter extends RecyclerView.Adapter<ExamTableAdapter.myViewHolder> {

    private List<ExamTableTimeSubject> subjectArrayList;
    private Context mContext;
    private LayoutInflater inflater;
    private int mItemsNumber;

    public ExamTableAdapter(Context context, ArrayList<ExamTableTimeSubject> subjectArrayList) {
        this.subjectArrayList = subjectArrayList;
        mContext = context;
        inflater = LayoutInflater.from(context);
        if (subjectArrayList != null) {
            mItemsNumber = subjectArrayList.size();
        } else {
            mItemsNumber = 0;
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.exam_table_time_list_item, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.subject_name.setText(subjectArrayList.get(position).getName());
        holder.short_code.setText(subjectArrayList.get(position).getOldID());
        holder.number_of_hours.setText(subjectArrayList.get(position).getHours());
        holder.date_of_exam.setText(subjectArrayList.get(position).getExamDate());


    }

    @Override
    public int getItemCount() {
        return mItemsNumber;
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView subject_name;
        private TextView short_code;
        private TextView number_of_hours;
        private TextView date_of_exam;

        public myViewHolder(View itemView) {
            super(itemView);

            subject_name = (TextView) itemView.findViewById(R.id.exam_table_subject_name);
            short_code = (TextView) itemView.findViewById(R.id.exam_table_subject_old_id_logo);
            number_of_hours = (TextView) itemView.findViewById(R.id.exam_table_subject_hours_num);
            date_of_exam = (TextView) itemView.findViewById(R.id.exam_table_subject_date_val);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
        }
    }
}
