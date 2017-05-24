package rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades;

import android.content.Context;
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

import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.CurrentSemesterGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.CURRENT_SEMESTER_GRADES_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.STUDENT_GRADES_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getCurrentSemesterSubjects;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isNetworkAvailable;


public class CurrentSemesterGradesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {

    private View mRoot_View;
    public FragmentActivity mContext;
    private Handler mHandler;
    private CurrentSemesterGradesFragment mFragment;
    private LoaderManager loaderManager;
    private ArrayList<CurrentSemesterGradesSubject> mCurrentSemesterGradesSubjects = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
        mHandler = new Handler();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initializeLoaderManager();
        if (mCurrentSemesterGradesSubjects == null) {

            if (isNetworkAvailable(mContext)) {

                mRoot_View = inflater.inflate(R.layout.current_semester_gardes_fragment, container, false);
                getCurrentSemesterSubjectsSingleton();
                initializeSwipeRefreshLayout();
                Runnable runnable = new TimerTask() {
                    @Override
                    public void run() {
                        if (MainActivity.mapLoginPageCookies != null) {
                            if (isNetworkAvailable(mContext)) {
                                startLoaderManager();
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
            mRoot_View = inflater.inflate(R.layout.current_semester_gardes_fragment, container, false);
            initializeRecycleView();
            initializeSwipeRefreshLayout();
            makeProgressBarInvisible();
        }

        return mRoot_View;
    }

    public ArrayList<CurrentSemesterGradesSubject> getCurrentSemesterSubjectsSingleton() {
        if (mCurrentSemesterGradesSubjects == null) {
            mCurrentSemesterGradesSubjects = new ArrayList<>();
        }
        return mCurrentSemesterGradesSubjects;
    }

    public void initializeLoaderManager() {
        loaderManager = mContext.getSupportLoaderManager();
    }

    public void startLoaderManager(){
        Loader<Document> loader = loaderManager.getLoader(CURRENT_SEMESTER_GRADES_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(CURRENT_SEMESTER_GRADES_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(CURRENT_SEMESTER_GRADES_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeSwipeRefreshLayout() {
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) mRoot_View.findViewById(R.id.current_semester_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable(mContext)) {
                    loaderManager.initLoader(STUDENT_GRADES_LOADER_ID, null, mFragment).forceLoad();
                } else {
                    Snackbar.make(mRoot_View, "No Internet Connection", Snackbar.LENGTH_LONG).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initializeRecycleView() {

        RecyclerView mRecycleView = (RecyclerView) mRoot_View.findViewById(R.id.current_semester_recycle_view);
        mRecycleView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycleView.setLayoutManager(mLayoutManager);

        CurrentSemesterRecycleViewAdapter mRecycleAdapter = new CurrentSemesterRecycleViewAdapter(mCurrentSemesterGradesSubjects);
        mRecycleView.setAdapter(mRecycleAdapter);
    }

    public void makeProgressBarInvisible() {
        AVLoadingIndicatorView mProgressBar = (AVLoadingIndicatorView) mRoot_View.findViewById(R.id.current_semester_progress_bar);
        mProgressBar.hide();
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderCurrentSemesterGrades(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {

        mCurrentSemesterGradesSubjects = getCurrentSemesterSubjects(data);
        initializeRecycleView();
        makeProgressBarInvisible();

    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }
}
