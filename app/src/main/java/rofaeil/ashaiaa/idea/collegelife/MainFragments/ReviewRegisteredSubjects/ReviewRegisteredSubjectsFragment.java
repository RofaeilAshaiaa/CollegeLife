package rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.FinalData;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;
import rofaeil.ashaiaa.idea.collegelife.databinding.ReviewRegisteredSubjectsFragmentBinding;

import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.extractLastSemesterReviewSubjects;

public class ReviewRegisteredSubjectsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Document> {

    private ReviewRegisteredSubjectsFragmentBinding mBinding;
    private ArrayList<StudentGradesSubject> semester_subjects;
    private String mTotalHours ;
    private int mTotalHoursOfSemester ;
    private String mSemesterName ;
    private String mCGPA ;
    private ProgressBar progressBar;
    private Context mContext;
    private Handler mHandler;
    private FragmentActivity mActivity;
    private ReviewRegisteredSubjectsFragment mFragment;

    public ReviewRegisteredSubjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate
                (inflater, R.layout.review_registered_subjects_fragment, container, false);
        progressBar = mBinding.progressBarReviewSubjects;
        mContext = getContext();
        mActivity = getActivity();
        mHandler = new Handler();
        mFragment = this;

        progressBar.setVisibility(View.VISIBLE);

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null && StaticMethods.isNetworkAvailable(mActivity)) {

                    mBinding.swipeContainer.setVisibility(View.INVISIBLE);
                    mActivity.getSupportLoaderManager()
                            .initLoader(FinalData.REVIEW_SUBJECTS_LOADER_ID, null, mFragment)
                            .forceLoad();

                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);
        start_request_and_get_data();

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void start_request_and_get_data() {


    }

    private void setContentOfViews(ArrayList<StudentGradesSubject> semester_subjects) {

        mBinding.semesterTitle.setText(mSemesterName);
        mBinding.numberOfSubjects.setText(Integer.toString(semester_subjects.size() ));
        mBinding.totalNumberOfHours.setText(mTotalHours);
        mBinding.totalNumberOfHoursOfSemester.setText( Integer.toString(mTotalHoursOfSemester));
        mBinding.tvCgpa.setText(mCGPA);

        RecyclerView recyclerView = mBinding.recyclerview;
        ReviewRegisteredSubjectsAdapter adapter =
                new ReviewRegisteredSubjectsAdapter(mActivity, semester_subjects);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mActivity, "user refreshed", Toast.LENGTH_SHORT).show();
                mBinding.swipeContainer.setRefreshing(false);
            }
        });
        mBinding.swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTaskLoaderReviewRegisteredSubjects(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {

        if (document != null) {

            semester_subjects = extractLastSemesterReviewSubjects(document);
            extractOtherSemesterData(document);

            if (semester_subjects != null) {

                setContentOfViews(semester_subjects);
                mBinding.swipeContainer.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(mActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.INVISIBLE);

    }

    private void extractOtherSemesterData(Document document) {

        Semester semester = new Semester();
        semester.setSubjects(semester_subjects);
        mTotalHoursOfSemester = StaticMethods.calculateTotalHoursOfSemester(semester);

        Element element = document.body()
              .getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_FormView1");


        mCGPA = element
                .getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_FormView1_CGPALabel")
                .text();
        mTotalHours = element
                .getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_FormView1_CreditLabel")
                .text() ;

        Elements elements = document.body().getElementById("MasterPageform").getElementsByTag("table") ;
        mSemesterName = elements.get(1).getElementsByTag("table").get(1).getElementsByTag("b").get(0).text();

    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }


}
