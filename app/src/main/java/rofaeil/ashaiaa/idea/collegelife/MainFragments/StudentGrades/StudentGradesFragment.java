package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.STUDENT_GRADES_LOADER_ID;


public class StudentGradesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {


    public View mRoot_View;
    public FragmentActivity mContext;
    private Handler mHandler;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (FragmentActivity) context;
        mHandler = new Handler();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot_View = inflater.inflate(R.layout.student_grades_semesters_fragment, container, false);

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {
                    initializeLoader();
                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);

        return mRoot_View;
    }

    public void initializeLoader() {
        LoaderManager loaderManager = mContext.getSupportLoaderManager();
        Loader<Document> loader = loaderManager.getLoader(STUDENT_GRADES_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(STUDENT_GRADES_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(STUDENT_GRADES_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeRecycleView(Document document) {
        ArrayList<Semester> mSemesters = new ArrayList<>();
        mSemesters = getStudentSemesters(document);

        RecyclerView mRecyclerView = (RecyclerView) mRoot_View.findViewById(R.id.student_grades_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        SemesterRecyclerViewAdapter mSemesterRecyclerViewAdapter = new SemesterRecyclerViewAdapter(mSemesters, mContext);
        mRecyclerView.setAdapter(mSemesterRecyclerViewAdapter);
    }

    public void initializeSwipeRefreshLayout() {
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) mRoot_View.findViewById(R.id.student_grades_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final AsyncTaskStudentGrades mAsyncTaskStudentGrades = new AsyncTaskStudentGrades() {
                    @Override
                    protected void onPostExecute(Document document) {

                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                };
                mAsyncTaskStudentGrades.execute();

            }
        });
    }

    public void makeProgressBarInvisible() {
        TextView mWaitingMassage = (TextView) mRoot_View.findViewById(R.id.student_grades_semesters_fragment_waiting_text);
        ProgressBar mProgressBar = (ProgressBar) mRoot_View.findViewById(R.id.student_grades_semesters_fragment_progress_bar);
        mWaitingMassage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public ArrayList<Semester> getStudentSemesters(Document document) {

        ArrayList<Semester> mSemesters = new ArrayList<>();
        Element mRoot_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1");
        Elements mSemesters_tables = mRoot_table.children().get(0).children();
        for (int i = 0; i < mSemesters_tables.size(); i++) {

            Semester mSemester = new Semester();
            ArrayList<StudentGradesSubject> mSubjects = new ArrayList<>();

            Element mSemester_table = mSemesters_tables.get(i);

            mSemester.setName(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_SemesterNameLabel_" + i + "").text());
            mSemester.setGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_GPALabel").text());
            mSemester.setSemesterLoad(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterLoadLabel").text());
            mSemester.setEarnedHours(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterCHLabel").text());
            mSemester.setCGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_CGPALabel").text());
            mSemester.setBackgroundId(new Random().nextInt(10));

            Element mSemester_Subjects_table = mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_GridView1_" + i + "");
            Elements mSemester_Subjects = mSemester_Subjects_table.getElementsByTag("tr");

            for (int n = 1; n < mSemester_Subjects.size(); n++) {

                StudentGradesSubject mSubject = new StudentGradesSubject();

                Elements mSubject_data = mSemester_Subjects.get(n).getElementsByTag("td");


                mSubject.setID(mSubject_data.get(0).text());
                mSubject.setOldID(mSubject_data.get(1).text());
                mSubject.setName(mSubject_data.get(2).text());
                mSubject.setPollingUrl(mSubject_data.get(2).child(0).absUrl("href"));
                mSubject.setGrade(mSubject_data.get(3).text());
                mSubject.setPoints(mSubject_data.get(4).text());
                mSubject.setHours(mSubject_data.get(5).text());
                mSubject.setPoints_X_Hours(mSubject_data.get(6).text());
                mSubject.setBackgroundId(new Random().nextInt(10));

                mSubjects.add(mSubject);

            }
            mSemester.setSubjects(mSubjects);
            mSemesters.add(mSemester);
        }

        return mSemesters;
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderStudentGrades(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        initializeRecycleView(data);
        initializeSwipeRefreshLayout();
        makeProgressBarInvisible();
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }
}
