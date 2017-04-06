package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationRequirements;
import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationSheetData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.GraduationSheetSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.GRADUATION_SHEET_LOADER_ID;


public class GraduationSheetFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {

    private View mRoot_view;
    private FragmentActivity mContext;
    private  ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRoot_view = inflater.inflate(R.layout.graduation_sheet_fragment, container, false);
        initializeProgressDialog();
        initializeLoaderManager();

        return mRoot_view;
    }

    public void initializeProgressDialog(){
        mProgressDialog  = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading data");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
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

    public void initializeRecycleView(Document document) {
        GraduationSheetData mGraduationSheetData = new GraduationSheetData();
        mGraduationSheetData = getGraduationSheetData(document);

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

    public GraduationSheetData getGraduationSheetData(Document document) {
        GraduationSheetData mGraduationSheetData = new GraduationSheetData();

        Element mSubjects_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_GraduationSheetGridView");
        Elements mSubjects_tr = mSubjects_table.getElementsByTag("tr");

        ArrayList<GraduationSheetSubject> mSubjects = new ArrayList<>();
        for (int i = 1; i < mSubjects_tr.size(); i++) {

            GraduationSheetSubject mSubject = new GraduationSheetSubject();

            mSubject.setCategory(mSubjects_tr.get(i).child(0).text());
            mSubject.setID(mSubjects_tr.get(i).child(1).text());
            mSubject.setOldID(mSubjects_tr.get(i).child(2).text());
            mSubject.setName(mSubjects_tr.get(i).child(3).text());
            mSubject.setHours(mSubjects_tr.get(i).child(4).text());
            mSubject.setSemester(mSubjects_tr.get(i).child(5).text());
            mSubject.setEarnedGrade(mSubjects_tr.get(i).child(6).text());
            mSubject.setActualGrade(mSubjects_tr.get(i).child(7).text());
            mSubject.setPoints(mSubjects_tr.get(i).child(8).text());
            mSubject.setNodes(mSubjects_tr.get(i).child(9).text());
            mSubject.setBackgroundId(new Random().nextInt(10));
            mSubjects.add(mSubject);
        }

        Element mRequirement_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_GraduationSheetSumGridView");
        Elements mRequirement_tr = mRequirement_table.getElementsByTag("tr");

        ArrayList<GraduationRequirements> mGraduationRequirements = new ArrayList<>();
        for (int n = 1; n < mRequirement_tr.size(); n++) {

            GraduationRequirements mRequirement = new GraduationRequirements();

            mRequirement.setName(mRequirement_tr.get(n).child(0).text());
            mRequirement.setRequiredHours(mRequirement_tr.get(n).child(1).text());
            mRequirement.setEarnedHours(mRequirement_tr.get(n).child(2).text());

            mGraduationRequirements.add(mRequirement);
        }

        mGraduationSheetData.setGraduationRequirements(mGraduationRequirements);
        mGraduationSheetData.setSubject(mSubjects);

        return mGraduationSheetData;
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderGraduationSheet(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        initializeRecycleView(data);
        mProgressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

}
