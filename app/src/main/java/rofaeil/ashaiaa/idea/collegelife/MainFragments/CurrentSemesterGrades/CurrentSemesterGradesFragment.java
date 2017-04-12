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
import android.widget.TextView;

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

    public View mRoot_View;
    public FragmentActivity mContext;
    private Handler mHandler;
    private CurrentSemesterGradesFragment mFragment;
    private LoaderManager loaderManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
        mHandler = new Handler();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mFragment = this;

        if (isNetworkAvailable(mContext)){

            mRoot_View = inflater.inflate(R.layout.current_semester_gardes_fragment, container, false);

            Runnable runnable = new TimerTask() {
                @Override
                public void run() {
                    if (MainActivity.mapLoginPageCookies != null) {
                        if (isNetworkAvailable(mContext)){
                            mContext.getSupportLoaderManager().initLoader(CURRENT_SEMESTER_GRADES_LOADER_ID, null, mFragment).forceLoad();
                        }
                    } else {
                        mHandler.postDelayed(this, 100);
                    }
                }
            };
            mHandler.post(runnable);

        }else {
            mRoot_View = inflater.inflate(R.layout.offline_layout, container, false);
        }

        return mRoot_View;
    }

    public void initializeLoaderManager() {
        loaderManager = mContext.getSupportLoaderManager();
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
                    loaderManager.restartLoader(STUDENT_GRADES_LOADER_ID, null, mFragment).forceLoad();
                }else {
                    Snackbar.make(mRoot_View, "No InterNet Connection", Snackbar.LENGTH_LONG).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void initializeRecycleView(Document document) {
        ArrayList<CurrentSemesterGradesSubject> mSubjects = new ArrayList<>();
        mSubjects = getCurrentSemesterSubjects(document);

        RecyclerView mRecycleView = (RecyclerView) mRoot_View.findViewById(R.id.current_semester_recycle_view);
        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycleView.setLayoutManager(mLayoutManager);

        CurrentSemesterRecycleViewAdapter mRecycleAdapter = new CurrentSemesterRecycleViewAdapter(mSubjects);
        mRecycleView.setAdapter(mRecycleAdapter);
    }

    public void makeProgressBarInvisible() {
        ProgressBar mProgressBar = (ProgressBar) mRoot_View.findViewById(R.id.current_semester_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderCurrentSemesterGrades(mContext);
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
