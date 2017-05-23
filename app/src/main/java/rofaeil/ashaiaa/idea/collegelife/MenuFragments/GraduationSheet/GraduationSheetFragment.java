package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationSheetData;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.GRADUATION_SHEET_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isNetworkAvailable;


public class GraduationSheetFragment extends Fragment implements LoaderManager.LoaderCallbacks<GraduationSheetData> {

    private View mRoot_view;
    private FragmentActivity mContext;
    private View mDataView;
    private AVLoadingIndicatorView mProgressBar;
    private Handler mHandler;
    private static GraduationSheetData mGraduationSheetData = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (mGraduationSheetData == null) {
            if (isNetworkAvailable(mContext)) {
                mRoot_view = inflater.inflate(R.layout.graduation_sheet_fragment, container, false);

                initializeProgressBar(mRoot_view);

                getGraduationSheetDataSingleton();
                Runnable runnable = new TimerTask() {
                    @Override
                    public void run() {
                        if (MainActivity.mapLoginPageCookies != null) {
                            initializeLoaderManager();
                        } else {
                            mHandler.postDelayed(this, 100);
                        }
                    }
                };
                mHandler.post(runnable);

            } else {
                mRoot_view = inflater.inflate(R.layout.offline_layout, container, false);
            }
        } else {
            mRoot_view = inflater.inflate(R.layout.graduation_sheet_fragment, container, false);

            initializeProgressBar(mRoot_view);

            initializeRecycleView();
            mProgressBar.hide();
            mDataView.setVisibility(View.VISIBLE);
        }

        return mRoot_view;
    }

    public void initializeProgressBar(View RootView){
        mProgressBar = (AVLoadingIndicatorView) RootView.findViewById(R.id.graduation_sheet_progressBar);
        mDataView = (View) RootView.findViewById(R.id.graduation_sheet_View);
    }

    public GraduationSheetData getGraduationSheetDataSingleton() {
        if (mGraduationSheetData == null) {
            mGraduationSheetData = new GraduationSheetData();
        }
        return mGraduationSheetData;
    }

    public void initializeLoaderManager() {
        LoaderManager loaderManager = mContext.getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(GRADUATION_SHEET_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(GRADUATION_SHEET_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(GRADUATION_SHEET_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeRecycleView() {

        RecyclerView mRecyclerView = (RecyclerView) mRoot_view.findViewById(R.id.graduation_sheet_subject_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        initializeGraduationSheetRequirements(mRoot_view, mGraduationSheetData);

        GraduationSheetRecyclerViewAdepter mRecyclerViewAdepter = new GraduationSheetRecyclerViewAdepter(mGraduationSheetData);
        mRecyclerView.setAdapter(mRecyclerViewAdepter);
    }

    public void initializeGraduationSheetRequirements(View view, GraduationSheetData GraduationSheetData) {

        TextView mFreeChoiceEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_free_choice_num);
        TextView mUniversityRequirementEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_university_requirement_num);
        TextView mFacultyRequirementEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_faculty_requirement_num);
        TextView mMajorChoiceEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_major_choice_num);
        TextView mMajorForcedEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_major_forced_num);
        TextView mMinorChoiceEarnedHours = (TextView) view.findViewById(R.id.graduation_sheet_requirements_minor_choice_num);

        ProgressBar mFreeChoiceProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_free_choice_progress_bar);
        ProgressBar mUniversityRequirementProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_university_requirement_progress_bar);
        ProgressBar mFacultyRequirementProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_faculty_requirement_progress_bar);
        ProgressBar mMajorChoiceProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_major_choice_progress_bar);
        ProgressBar mMajorForcedProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_major_forced_progress_bar);
        ProgressBar mMinorChoiceProgressBar = (ProgressBar) view.findViewById(R.id.graduation_sheet_requirements_minor_choice_progress_bar);

        mFreeChoiceEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(0).getEarnedHours());
        mUniversityRequirementEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(1).getEarnedHours());
        mFacultyRequirementEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(2).getEarnedHours());
        mMajorForcedEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(3).getEarnedHours());
        mMajorChoiceEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(4).getEarnedHours());
        mMinorChoiceEarnedHours.setText(GraduationSheetData.getGraduationRequirements().get(5).getEarnedHours());

        mFreeChoiceProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(0).getRequiredHours()));
        mUniversityRequirementProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(1).getRequiredHours()));
        mFacultyRequirementProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(2).getRequiredHours()));
        mMajorForcedProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(3).getRequiredHours()));
        mMajorChoiceProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(4).getRequiredHours()));
        mMinorChoiceProgressBar.setMax(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(5).getRequiredHours()));

        mFreeChoiceProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(0).getEarnedHours()));
        mUniversityRequirementProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(1).getEarnedHours()));
        mFacultyRequirementProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(2).getEarnedHours()));
        mMajorForcedProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(3).getEarnedHours()));
        mMajorChoiceProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(4).getEarnedHours()));
        mMinorChoiceProgressBar.setProgress(Integer.parseInt(GraduationSheetData.getGraduationRequirements().get(5).getEarnedHours()));


    }

    @Override
    public Loader<GraduationSheetData> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderGraduationSheet(mContext);
    }

    @Override
    public void onLoadFinished(Loader<GraduationSheetData> loader, GraduationSheetData data) {

        mGraduationSheetData = data;
        initializeRecycleView();

        mProgressBar.hide();
        mDataView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<GraduationSheetData> loader) {

    }

}
