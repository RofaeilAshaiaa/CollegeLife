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
import android.widget.FrameLayout;

import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.STUDENT_GRADES_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getResponseDescription;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isNetworkAvailable;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isResponseSuccess;


public class StudentGradesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Connection.Response> {


    private View mRoot_View;
    private FragmentActivity mContext;
    private Handler mHandler;
    private LoaderManager loaderManager;
    private StudentGradesFragment mFragment;
    private ArrayList<Semester> mSemesters = null;
    private boolean mErrorLayoutInflated = false;

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
        initializeLoaderManager();

        if (mSemesters == null) {

            if (isNetworkAvailable(mContext)) {

                mRoot_View = inflater.inflate(R.layout.student_grades_semesters_fragment, container, false);

                initializeSwipeRefreshLayout();

                Runnable runnable = new TimerTask() {
                    @Override
                    public void run() {
                        if (MainActivity.mapLoginPageCookies != null) {
                            if (isNetworkAvailable(mContext)) {
                                startLoader();
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
        } else {

            mRoot_View = inflater.inflate(R.layout.student_grades_semesters_fragment, container, false);

            initializeRecycleView();
            initializeSwipeRefreshLayout();
            makeProgressBarInvisible();

        }


        return mRoot_View;
    }

    public ArrayList<Semester> getCurrentSemesterSubjectsSingleton() {
        if (mSemesters == null) {
            mSemesters = new ArrayList<>();
        }
        return mSemesters;
    }

    public void initializeLoaderManager() {
        loaderManager = mContext.getSupportLoaderManager();
    }

    public void startLoader() {
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
                    if (MainActivity.mapLoginPageCookies != null) {
                        loaderManager.restartLoader(STUDENT_GRADES_LOADER_ID, null, mFragment).forceLoad();
                    }else {
                        Snackbar.make(mRoot_View, "No Cookies", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mRoot_View, "No Internet Connection", Snackbar.LENGTH_LONG).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void makeProgressBarInvisible() {
        AVLoadingIndicatorView mProgressBar = (AVLoadingIndicatorView) mRoot_View.findViewById(R.id.student_grades_semesters_fragment_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public Loader<Connection.Response> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderStudentGrades(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Connection.Response> loader, Connection.Response data) {

        if (data != null) {

            if (isResponseSuccess(data)) {

                AsyncTaskStudentGradesDataParser asyncTaskStudentGradesDataParser = new AsyncTaskStudentGradesDataParser() {
                    @Override
                    protected void onPostExecute(ArrayList<Semester> semesters) {
                        mSemesters = semesters;
                        initializeRecycleView();
                        makeProgressBarInvisible();
                    }
                };
                try {
                    asyncTaskStudentGradesDataParser.execute(data.parse());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                mErrorLayoutInflated = true;
                String ResponseErrorDescription = getResponseDescription(data);
                FrameLayout frameLayout = (FrameLayout)mRoot_View.findViewById(R.id.student_grades_semesters_error_frame);
                View layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.offline_layout, frameLayout,false);
                frameLayout.addView(layoutInflater);

            }
        } else {

            mErrorLayoutInflated = true;
            FrameLayout frameLayout = (FrameLayout)mRoot_View.findViewById(R.id.student_grades_semesters_error_frame);
            View layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.offline_layout, frameLayout,false);
            frameLayout.addView(layoutInflater);

        }


    }

    @Override
    public void onLoaderReset(Loader<Connection.Response> loader) {

    }
}
