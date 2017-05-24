package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.STUDENT_GRADES_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.deleteOfflineLayout;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getIconRightCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getSemesterLogoBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextLeftCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isNetworkAvailable;


public class StudentGradesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {


    private View mRoot_View;
    private FragmentActivity mContext;
    private Handler mHandler;
    private LoaderManager loaderManager;
    private StudentGradesFragment mFragment;
    private ArrayList<Semester> mSemesters = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (FragmentActivity) context;
        mHandler = new Handler();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment = this;
        if (isNetworkAvailable(mContext)) {
            mRoot_View = inflater.inflate(R.layout.student_grades_semesters_fragment, container, false);
            Runnable runnable = new TimerTask() {
                @Override
                public void run() {
                    if (MainActivity.mapLoginPageCookies != null) {
                        if (StaticMethods.isNetworkAvailable(mContext)) {
                            loaderManager = mContext.getSupportLoaderManager();
                            loaderManager.initLoader(STUDENT_GRADES_LOADER_ID, null, mFragment).forceLoad();
                        }
                    } else {
                        mHandler.postDelayed(this, 100);
                    }
                }
            };

            mHandler.post(runnable);
        } else {
            mRoot_View = inflater.inflate(R.layout.offline_layout, container, false);
        }

        return mRoot_View;
    }

    public ArrayList<Semester> getCurrentSemesterSubjectsSingleton() {
        if (mSemesters == null) {
            mSemesters = new ArrayList<>();
        }
        return mSemesters;
    }

    public void initializeLoader() {
        loaderManager = mContext.getSupportLoaderManager();
    }

    public void startLoader(){
        Loader<Document> loader = loaderManager.getLoader(STUDENT_GRADES_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(STUDENT_GRADES_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(STUDENT_GRADES_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeRecycleView() {

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
                if (isNetworkAvailable(mContext)) {
                    loaderManager.restartLoader(STUDENT_GRADES_LOADER_ID, null, mFragment).forceLoad();
                } else {
                    Snackbar.make(mRoot_View, "No InterNet Connection", Snackbar.LENGTH_LONG).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void makeProgressBarInvisible() {
        AVLoadingIndicatorView mProgressBar = (AVLoadingIndicatorView) mRoot_View.findViewById(R.id.student_grades_semesters_fragment_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public ArrayList<Semester> getStudentSemesters(Document document) {

        ArrayList<Semester> mSemesters = new ArrayList<>();
        Element mRoot_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1");
        Elements mSemesters_tables = mRoot_table.children().get(0).children();
        for (int i = 0; i < mSemesters_tables.size(); i++) {

            Semester mSemester = new Semester();
            int BackgroundId = new Random().nextInt(10);

            Element mSemester_table = mSemesters_tables.get(i);

            mSemester.setName(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_SemesterNameLabel_" + i + "").text());
            mSemester.setGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_GPALabel").text());
            mSemester.setSemesterLoad(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterLoadLabel").text());
            mSemester.setEarnedHours(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterCHLabel").text());
            mSemester.setCGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_CGPALabel").text());
            mSemester.setBackgroundId(BackgroundId);
            mSemester.setLogoBackgroundResource(getSemesterLogoBackgroundResource(BackgroundId));
            mSemester.setTextBackgroundResource(getTextBackgroundResource(BackgroundId));
            mSemester.setIconRightCornerBackgroundResource(getIconRightCornerBackgroundResource(BackgroundId));
            mSemester.setTextLeftCornerBackgroundResource(getTextLeftCornerBackgroundResource(BackgroundId));

            Element mSemester_Subjects_table = mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_GridView1_" + i + "");

            mSemester.setSubjectsDocument(mSemester_Subjects_table.toString());
            Elements Subjects = mSemester_Subjects_table.child(0).children();
            int SubjectSize = Subjects.size();

            mSemester.setSubjectNum(SubjectSize - 1);
            mSemester.setSuccessSubjectsNum(getSuccessSubjectsNum(SubjectSize, Subjects));

            mSemesters.add(mSemester);
        }

        return mSemesters;
    }

    public int getSuccessSubjectsNum(int SubjectSize, Elements Subjects) {
        int mFailSubjectsNam = 0;
        for (int j = 1; j < SubjectSize; j++) {
            String SubjectGrade = Subjects.get(j).child(3).text();
            if (SubjectGrade.equals("F")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (SubjectGrade.equals("إستبيان")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (SubjectGrade.equals("P")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
        }
        return SubjectSize - mFailSubjectsNam - 1;
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderStudentGrades(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {

        mSemesters = getStudentSemesters(data);
        initializeRecycleView();
        initializeSwipeRefreshLayout();
        makeProgressBarInvisible();
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }
}
