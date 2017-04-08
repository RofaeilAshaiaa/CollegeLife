package rofaeil.ashaiaa.idea.collegelife.MainFragments.FinalExamsTable;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.ExamTableTimeSubject;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.FinalData;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;
import rofaeil.ashaiaa.idea.collegelife.databinding.ExamTableTimeFragmentBinding;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.CurrentSemesterGradesURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.ExamTableTimeURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getCurrentSemesterSubjectsFinalTable;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getSubjectOldIdBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextBackgroundResource;

public class ExamTableTimeFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Document>{

    private ExamTableTimeFragmentBinding mBinding;
    private ArrayList<ExamTableTimeSubject> semester_subjects;
    private ArrayList<ExamTableTimeSubject> mExamTableTimeSubjects;
    private RecyclerView recyclerView;
    private ExamTableAdapter examTableAdapter;
    private ProgressBar progressBar;
    private ExamTableTimeFragment mFragment;
    private Context mContext;
    private Handler mHandler;
    private FragmentActivity mActivity;
    private boolean CalledFromSwipeRefresh=false;
    private boolean wasOffline ;

    public ExamTableTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.exam_table_time_fragment, container, false);
        mContext = getContext();
        mHandler = new Handler();
        mActivity = getActivity() ;
        mFragment = this ;
        recyclerView = mBinding.recyclerview;
        progressBar = mBinding.progressBarReviewSubjects;
        recyclerView = mBinding.recyclerview;
        progressBar.setVisibility(View.VISIBLE);
        mBinding.recyclerview.setVisibility(View.INVISIBLE);


        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {

                    if( StaticMethods.isNetworkAvailable(mActivity) )
                        mActivity.getSupportLoaderManager()
                                .initLoader(FinalData.Semester_Exam_Table_LOADER_ID, null,mFragment )
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

    private int extractSemesterSubject(Document document) {

        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_GridView1");

        if (target_table != null) {
            //gets rows of the table
            Elements rows_of_target_table = target_table.getElementsByTag("tr");
            mExamTableTimeSubjects = new ArrayList<>();

            for (int i = 1; i < rows_of_target_table.size(); i++) {
                Elements subject_attributes = rows_of_target_table.get(i).getElementsByTag("td");

                ExamTableTimeSubject subject = new ExamTableTimeSubject();

                subject.setHours(subject_attributes.get(3).text());
                String subject_date = subject_attributes.get(4).text();
                if (subject_date.matches(" ") || subject_date == null || subject_date.matches("")) {
                    subject.setExamDate("لا يوجد تاريخ");
                } else {
                    subject.setExamDate(subject_date);
                }
                subject.setName(subject_attributes.get(2).text());
                subject.setOldID(subject_attributes.get(1).text());
                subject.setID(subject_attributes.get(0).text());

                mExamTableTimeSubjects.add(subject);

                String current_subject_row_code = subject_attributes.get(0).text();

                for (int j = 0; j < semester_subjects.size(); j++) {
                    int BackgroundId = new Random().nextInt(9);

                    String subject_code = semester_subjects.get(j).getID();

                    if (subject_code.matches(current_subject_row_code)) {


                        if (subject_date.matches("\\s") ) {
                            semester_subjects.get(j).setExamDate(getString(R.string.exam_date_not_determined_yet));
                        } else {
                            semester_subjects.get(j).setExamDate(subject_date);
                        }
                        semester_subjects.get(j).setHours(subject_attributes.get(3).text());

                        semester_subjects.get(j).setBackgroundResourceIdOldCode(
                                getSubjectOldIdBackgroundResource(BackgroundId));
                        semester_subjects.get(j).setBackgroundResourceIdDateAndHours(
                                getTextBackgroundResource(BackgroundId));

                    }

                }


            }
//            Log.v("sjhdk", mExamTableTimeSubjects.toString());
            return 0;

        } else {
            return -1;
        }

    }

    private void set_content_of_final_table() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        examTableAdapter = new ExamTableAdapter(mContext, semester_subjects);
        recyclerView.setAdapter(examTableAdapter);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Document>(mContext) {
            @Override
            public Document loadInBackground() {
                try {
                    Connection.Response response_of_getting_data =
                            Jsoup.connect(CurrentSemesterGradesURL)
                                    .userAgent("Mozilla/5.0")
                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
                                    .cookies(mapLoginPageCookies)
                                    .execute();

                    Document document =  response_of_getting_data.parse();
                    semester_subjects = getCurrentSemesterSubjectsFinalTable(document);

                    Connection.Response response =
                            Jsoup.connect(ExamTableTimeURL)
                                    .userAgent("Mozilla/5.0")
                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
                                    .execute();

                    return response.parse();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {

        int returned = extractSemesterSubject(document);
        if (returned == 0) {
            set_content_of_final_table();

            if(CalledFromSwipeRefresh){
                CalledFromSwipeRefresh=false ;
                mBinding.swipeContainer.setRefreshing(false);
            }
            mBinding.recyclerview.setVisibility(View.VISIBLE);

        } else {
            StaticMethods.showSnackbarNoAction(
                    mBinding.examTableMainContainer ,
                    getString(R.string.no_exam_table_message),
                    Snackbar.LENGTH_LONG);
        }

        progressBar.setVisibility(View.INVISIBLE);
        setUpSwipeRefreshLayout();


    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

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
                                .restartLoader(FinalData.Semester_Exam_Table_LOADER_ID, null, mFragment)
                                .forceLoad();
                        CalledFromSwipeRefresh = true;
                    }else {
                        mBinding.swipeContainer.setRefreshing(false);
                        if(!wasOffline)StaticMethods.inflateOfflineLayout(mContext,mBinding.errorLayoutContainer);
                        wasOffline=true;
                        mBinding.recyclerview.setVisibility(View.INVISIBLE);
                    }
                }
            }});
        mBinding.swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

}