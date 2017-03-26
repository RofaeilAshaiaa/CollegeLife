package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades.SemesterSubject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

public class SemesterSubjectFragment extends Fragment {

    public static StudentGradesSemesterDataSet mDataSet;
    FragmentActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDataSet = (StudentGradesSemesterDataSet) getActivity();
        mContext = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.student_grades_semester_subjects_fragment, container, false);

        RecyclerView mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.student_grades_semester_subjects_recycle_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        SemesterSubjectRecyclerViewAdapter mRecyclerViewAdapter = new SemesterSubjectRecyclerViewAdapter(mDataSet.getSemesterSubjects());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return mRootView;
    }

    public static interface StudentGradesSemesterDataSet {
        String getSemesterName();

        String getSemesterCGPA();

        String getSemesterGPA();

        String getSemesterEarnedHours();

        String getSemesterSemesterLoad();

        ArrayList<StudentGradesSubject> getSemesterSubjects();
    }
}
