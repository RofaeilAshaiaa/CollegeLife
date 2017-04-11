package rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
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
    private boolean CalledFromSwipeRefresh=false;
    private boolean wasOffline ;

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
        mBinding.nestedScroll.setVisibility(View.INVISIBLE);

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {

                    if( StaticMethods.isNetworkAvailable(mActivity) )
                        mActivity.getSupportLoaderManager()
                            .initLoader(FinalData.REVIEW_SUBJECTS_LOADER_ID, null, mFragment)
                            .forceLoad();
                    else {
                        wasOffline= true ;
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setContentOfViews(ArrayList<StudentGradesSubject> semester_subjects) {

        mBinding.semesterTitle.setText(mSemesterName);
        mBinding.numberOfSubjects.setText(Integer.toString(semester_subjects.size() ));
        mBinding.totalNumberOfHours.setText(mTotalHours);
        mBinding.totalNumberOfHoursOfSemester.setText( Integer.toString(mTotalHoursOfSemester));
        mBinding.tvCgpa.setText(mCGPA);


        mBinding.cgpaNumProgressBar.setProgress(Integer.parseInt(mCGPA.charAt(0)+mCGPA.substring(2)));
        mBinding.earnedHoursNumProgressBar.setProgress(Integer.parseInt(mTotalHours));
        mBinding.registeredSubjectsNumProgressBar.setProgress(mTotalHoursOfSemester);


        RecyclerView recyclerView = mBinding.recyclerview;
        ReviewRegisteredSubjectsAdapter adapter =
                new ReviewRegisteredSubjectsAdapter(mActivity, semester_subjects);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
    }

    private void setUpSwipeRefreshLayout() {
        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.mapLoginPageCookies != null) {
                    if( StaticMethods.isNetworkAvailable(mActivity) ) {
                        if(wasOffline) {
                            StaticMethods.deleteOfflineLayout(mBinding.errorLayoutContainer);
                            wasOffline = false;
                        }
                        mActivity.getSupportLoaderManager()
                                .restartLoader(FinalData.REVIEW_SUBJECTS_LOADER_ID, null, mFragment)
                                .forceLoad();
                        CalledFromSwipeRefresh = true;
                    }else {
                        mBinding.swipeContainer.setRefreshing(false);
                        if(!wasOffline)StaticMethods.inflateOfflineLayout(mContext,mBinding.errorLayoutContainer);
                        wasOffline=true;
                        mBinding.nestedScroll.setVisibility(View.INVISIBLE);
                    }
            }
        }});
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
                if(CalledFromSwipeRefresh){
                    CalledFromSwipeRefresh=false ;
                    mBinding.swipeContainer.setRefreshing(false);
                }
                mBinding.nestedScroll.setVisibility(View.VISIBLE);
            } else {
                showSnackbarWithReloadAction();
            }

        } else {
            showSnackbarWithReloadAction();
        }

        progressBar.setVisibility(View.INVISIBLE);
        setUpSwipeRefreshLayout();

    }

    private void showSnackbarWithReloadAction() {
        StaticMethods.showSnackbarWithAction(
                mBinding.examTableMainContainer ,
                getString(R.string.some_thing_went_wrong_message),
                Snackbar.LENGTH_LONG,
                getString(R.string.snackbar_reload_action ),
                getSnackbarClickListener());
    }

    private View.OnClickListener getSnackbarClickListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new TimerTask() {
                    @Override
                    public void run() {
                        if (MainActivity.mapLoginPageCookies != null && StaticMethods.isNetworkAvailable(mActivity)) {

                            mActivity.getSupportLoaderManager()
                                    .restartLoader(FinalData.REVIEW_SUBJECTS_LOADER_ID, null, mFragment)
                                    .forceLoad();
                        } else {
                            mHandler.postDelayed(this, 100);
                        }
                    }
                };

                mHandler.post(runnable);
            }
        };
        return listener;
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
