package rofaeil.ashaiaa.idea.collegelife.MainFragments.SubjectsRegistration;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.Subject;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;
import rofaeil.ashaiaa.idea.collegelife.databinding.SubjectsRegistrationFragmentBinding;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.SubjectsRegistrationURL;


public class SubjectsRegistrationFragment extends Fragment {

    private SubjectsRegistrationFragmentBinding mBinding;
    private ArrayList<Subject> mCollegeRequirementSubjects;
    private ArrayList<Subject> mUniversityRequirementSubjects;
    private ArrayList<Subject> mMajorSubjects;
    private ArrayList<Subject> mMinorSubjects;
    private ArrayList<Subject> mFreeSelectionSubjects;
    private LayoutInflater mInflater;
    private Map<String, String> mMap;
    //private RecyclerView mRecyclerView;
    //private ProgressBar mProgressBar;
    private Context mContext;
    private Handler mHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.subjects_registration_fragment, container, false);
//        mContext = getContext();
//        mHandler = new Handler();
//        mInflater = LayoutInflater.from(mContext);
//
//        final AsyncTaskLoader<Document> loader = new AsyncTaskLoader<Document>(mContext) {
//
//            @Override
//            public Document loadInBackground() {
//
//
//                try {
//                    Connection.Response response_of_getting_data =
//                            Jsoup.connect(SubjectsRegistrationURL)
//                                    .userAgent("Mozilla/5.0")
//                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
//                                    .cookies(mapLoginPageCookies)
//                                    .execute();
//
//                    return response_of_getting_data.parse();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            public void deliverResult(Document document) {
//                if (document != null) {
//
//                    extractDataCollegeRequirementSubjects(document);
//                    extractDataUniversityRequirementSubjects(document);
//                    extractDataFreeSelectionSubjects(document);
//                    extractDataMajorSubjects(document);
//                    extractDataMinorSubjects(document);
//
//                    setContent();
//
//                    mBinding.registerSubjectsTextLoading.setVisibility(View.INVISIBLE);
//
//                } else {
//                    mBinding.registerSubjectsTextLoading.setText("Some thing went wrong, try again !");
//                }
//
//                mBinding.progressBarRegisterSubjects.setVisibility(View.INVISIBLE);
//
//            }
//        };
//
//        Runnable runnable = new TimerTask() {
//            @Override
//            public void run() {
//                if (MainActivity.mapLoginPageCookies != null) {
//                    loader.forceLoad();
//                } else {
//                    mHandler.postDelayed(this, 100);
//                }
//            }
//        };
//        mBinding.registerSubjectsTextLoading.setText("Please Wait While Data is Loading");
//        mBinding.progressBarRegisterSubjects.setVisibility(View.VISIBLE);
//        mHandler.post(runnable);
//        mBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });
//        mBinding.fabRegisterSubjects.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StaticMethods.showToast(mContext, "finish button clicked");
//            }
//        });

        return mBinding.getRoot();
    }
//
//    private void refreshData() {
//        mBinding.linearCollegeRequirements.removeAllViews();
//        mBinding.linearFreeSelectionSubjects.removeAllViews();
//        mBinding.linearMajorSubjects.removeAllViews();
//        mBinding.linearMinorSubjects.removeAllViews();
//        mBinding.linearUniversityRequirements.removeAllViews();
//        mBinding.scrollRegisterSubjects.setVisibility(View.INVISIBLE);
//        mBinding.fabRegisterSubjects.setVisibility(View.INVISIBLE);
//        mBinding.fabRegisterSubjects.setClickable(false);
//
//
//        final AsyncTaskLoader<Document> loader = new AsyncTaskLoader<Document>(mContext) {
//
//            @Override
//            public Document loadInBackground() {
//
//
//                try {
//                    Connection.Response response_of_getting_data =
//                            Jsoup.connect(SubjectsRegistrationURL)
//                                    .userAgent("Mozilla/5.0")
//                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
//                                    .cookies(mapLoginPageCookies)
//                                    .execute();
//
//                    return response_of_getting_data.parse();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            public void deliverResult(Document document) {
//                if (document != null) {
//
//                    extractDataCollegeRequirementSubjects(document);
//                    extractDataUniversityRequirementSubjects(document);
//                    extractDataFreeSelectionSubjects(document);
//                    extractDataMajorSubjects(document);
//                    extractDataMinorSubjects(document);
//
//                    setContent();
//
//                    mBinding.registerSubjectsTextLoading.setVisibility(View.INVISIBLE);
//                    mBinding.fabRegisterSubjects.setVisibility(View.VISIBLE);
//                    mBinding.fabRegisterSubjects.setClickable(true);
//
//
//                } else {
//                    mBinding.registerSubjectsTextLoading.setText("Some thing went wrong, try again !");
//                }
//
//                mBinding.progressBarRegisterSubjects.setVisibility(View.INVISIBLE);
//                mBinding.swipeContainer.setRefreshing(false);
//                mBinding.scrollRegisterSubjects.setVisibility(View.VISIBLE);
//
//            }
//        };
//
//        Runnable runnable = new TimerTask() {
//            @Override
//            public void run() {
//                if (MainActivity.mapLoginPageCookies != null) {
//                    loader.forceLoad();
//                } else {
//                    mHandler.postDelayed(this, 100);
//                }
//            }
//        };
//        mBinding.registerSubjectsTextLoading.setText("Please Wait While Data is Loading");
//        mBinding.registerSubjectsTextLoading.setVisibility(View.VISIBLE);
//        mBinding.fabRegisterSubjects.setVisibility(View.VISIBLE);
//        mHandler.post(runnable);
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        mBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//
//        mBinding.fabRegisterSubjects.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                extractCheckedCollegeRequirementsSubjects();
//                extractCheckedFreeSelectionSubjects();
//                extractCheckedMajorSubjects();
//                extractCheckedMinorSubjects();
//                extractCheckedUniversityRequirementsSubjects();
//            }
//        });
//    }
//
//    private void extractCheckedMinorSubjects() {
//
//    }
//
//    private void extractCheckedMajorSubjects() {
//
//    }
//
//    private void extractCheckedFreeSelectionSubjects() {
//
//    }
//
//    private void extractCheckedUniversityRequirementsSubjects() {
//
//    }
//
//    private void extractCheckedCollegeRequirementsSubjects() {
//        StringBuilder builder = new StringBuilder();
//        ArrayList<Subject> subjects = new ArrayList<>();
//
//        for (int i = 0; i < mCollegeRequirementSubjects.size(); i++) {
//
//            CheckedTextView checkedTextView = (CheckedTextView)
//                    mBinding.linearCollegeRequirements.getChildAt(i).findViewById(R.id.checked_text_view);
//            if (checkedTextView.isChecked()) {
//                builder.append(mCollegeRequirementSubjects.get(i).getID());
//            }
//
//        }
//
//
//    }
//
//
//    private void extractDataMajorSubjects(Document document) {
//
//        Element targetElement = document.getElementById
//                ("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_MajorCoursesListBox");
//        if (targetElement != null) {
//
//            Elements subjects_rows = targetElement.getElementsByTag("option");
//            mMajorSubjects = new ArrayList<>(subjects_rows.size());
//
//
//            for (int i = 0; i < subjects_rows.size(); i++) {
//
//                Subject subject = new Subject();
//                subject.setName(subjects_rows.get(i).text());
//                subject.setID(subjects_rows.get(i).val());
//                mMajorSubjects.add(i, subject);
//
//            }
//
//        }
//    }
//
//    private void extractDataMinorSubjects(Document document) {
//
//        Element targetElement = document.getElementById
//                ("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_MinorCoursesListBox");
//        if (targetElement != null) {
//
//            Elements subjects_rows = targetElement.getElementsByTag("option");
//            mMinorSubjects = new ArrayList<>(subjects_rows.size());
//
//
//            for (int i = 0; i < subjects_rows.size(); i++) {
//
//                Subject subject = new Subject();
//                subject.setName(subjects_rows.get(i).text());
//                subject.setID(subjects_rows.get(i).val());
//                mMinorSubjects.add(i, subject);
//
//            }
//
//        }
//    }
//
//    private void extractDataFreeSelectionSubjects(Document document) {
//        Element targetElement = document.getElementById
//                ("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_FreeElectiveListBox");
//        if (targetElement != null) {
//
//            Elements subjects_rows = targetElement.getElementsByTag("option");
//            mFreeSelectionSubjects = new ArrayList<>(subjects_rows.size());
//
//
//            for (int i = 0; i < subjects_rows.size(); i++) {
//
//                Subject subject = new Subject();
//                subject.setName(subjects_rows.get(i).text());
//                subject.setID(subjects_rows.get(i).val());
//                mFreeSelectionSubjects.add(i, subject);
//
//            }
//
//        }
//    }
//
//    private void extractDataCollegeRequirementSubjects(Document document) {
//
//        Element targetElement = document.getElementById
//                ("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_FacCoursesListBox");
//        if (targetElement != null) {
//
//            Elements subjects_rows = targetElement.getElementsByTag("option");
//            mCollegeRequirementSubjects = new ArrayList<>(subjects_rows.size());
//
//
//            for (int i = 0; i < subjects_rows.size(); i++) {
//
//                Subject subject = new Subject();
//                subject.setName(subjects_rows.get(i).text());
//                subject.setID(subjects_rows.get(i).val());
//                mCollegeRequirementSubjects.add(i, subject);
//
//            }
//
//        }
//    }
//
//    private void extractDataUniversityRequirementSubjects(Document document) {
//
//        Element targetElement = document.getElementById
//                ("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_UniCoursesListBox");
//        if (targetElement != null) {
//
//            Elements subjects_rows = targetElement.getElementsByTag("option");
//            mUniversityRequirementSubjects = new ArrayList<>(subjects_rows.size());
//
//
//            for (int i = 0; i < subjects_rows.size(); i++) {
//
//                Subject subject = new Subject();
//                subject.setName(subjects_rows.get(i).text());
//                subject.setID(subjects_rows.get(i).val());
//                mUniversityRequirementSubjects.add(i, subject);
//
//            }
//
//        }
//    }
//
//    private void setContent() {
//
//
//        if (mMajorSubjects != null) {
//
//            mBinding.textMajorSubjects.setVisibility(View.GONE);
//
//            for (int i = 0; i < mMajorSubjects.size(); i++) {
//
//                View view = mInflater
//                        .inflate(R.layout.subjects_registration_item, mBinding.linearMajorSubjects, false);
//                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_text_view);
//                checkedTextView.setText(mMajorSubjects.get(i).getName());
//                setOnClickOnCheckedTextView(checkedTextView);
//                mBinding.linearMajorSubjects.addView(view);
//
//            }
//
//        }
//
//        if (mMinorSubjects != null) {
//
//            mBinding.textMinorSubjects.setVisibility(View.GONE);
//
//            for (int i = 0; i < mMinorSubjects.size(); i++) {
//
//                View view = mInflater
//                        .inflate(R.layout.subjects_registration_item, mBinding.linearMinorSubjects, false);
//                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_text_view);
//                checkedTextView.setText(mMinorSubjects.get(i).getName());
//                setOnClickOnCheckedTextView(checkedTextView);
//                mBinding.linearMinorSubjects.addView(view);
//
//            }
//
//        }
//
//        if (mFreeSelectionSubjects != null) {
//
//            mBinding.textFreeSelectionSubjects.setVisibility(View.GONE);
//
//            for (int i = 0; i < mFreeSelectionSubjects.size(); i++) {
//
//                View view = mInflater
//                        .inflate(R.layout.subjects_registration_item, mBinding.linearFreeSelectionSubjects, false);
//                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_text_view);
//                checkedTextView.setText(mFreeSelectionSubjects.get(i).getName());
//                setOnClickOnCheckedTextView(checkedTextView);
//                mBinding.linearFreeSelectionSubjects.addView(view);
//
//            }
//
//        }
//
//        if (mCollegeRequirementSubjects != null) {
//
//            mBinding.textCollegeRequirements.setVisibility(View.GONE);
//
//            for (int i = 0; i < mCollegeRequirementSubjects.size(); i++) {
//
//                View view = mInflater
//                        .inflate(R.layout.subjects_registration_item, mBinding.linearCollegeRequirements, false);
//                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_text_view);
//                checkedTextView.setText(mCollegeRequirementSubjects.get(i).getName());
//                setOnClickOnCheckedTextView(checkedTextView);
//
//                mBinding.linearCollegeRequirements.addView(view);
//
//            }
//
//        }
//
//        if (mUniversityRequirementSubjects != null) {
//
//            mBinding.textUniversityRequirements.setVisibility(View.GONE);
//
//            for (int i = 0; i < mUniversityRequirementSubjects.size(); i++) {
//
//                View view = mInflater
//                        .inflate(R.layout.subjects_registration_item, mBinding.linearUniversityRequirements, false);
//                final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checked_text_view);
//                checkedTextView.setText(mUniversityRequirementSubjects.get(i).getName());
//                setOnClickOnCheckedTextView(checkedTextView);
//                mBinding.linearUniversityRequirements.addView(view);
//
//            }
//
//        }
//        mBinding.fabRegisterSubjects.setVisibility(View.VISIBLE);
//        mBinding.swipeContainer.setVisibility(View.VISIBLE);
//        mBinding.progressBarRegisterSubjects.setVisibility(View.INVISIBLE);
//        mBinding.registerSubjectsTextLoading.setVisibility(View.INVISIBLE);
//
//    }
//
//    private void setOnClickOnCheckedTextView(final CheckedTextView checkedTextView) {
//        checkedTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkedTextView.isChecked())
//                    checkedTextView.setChecked(false);
//                else
//                    checkedTextView.setChecked(true);
//            }
//        });
//    }

}
