package rofaeil.ashaiaa.idea.collegelife.MainFragments.FinalExamsTable;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.ExamTableTimeSubject;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.databinding.ExamTableTimeFragmentBinding;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.CurrentSemesterGradesURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.ExamTableTimeURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getCurrentSemesterSubjectsFinalTable;

public class ExamTableTimeFragment extends Fragment {

    private ExamTableTimeFragmentBinding binding;
    private ArrayList<ExamTableTimeSubject> semester_subjects;
    private ArrayList<ExamTableTimeSubject> mExamTableTimeSubjects;
    private RecyclerView recyclerView;
    private ExamTableAdapter examTableAdapter;
    private ProgressBar progressBar;
    private Context mContext;
    private Handler mHandler;

    public ExamTableTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.exam_table_time_fragment, container, false);
        mContext = getContext();
        mHandler = new Handler();
        recyclerView = binding.recyclerview;
        progressBar = binding.progressBarReviewSubjects;
        recyclerView = binding.recyclerview;
        binding.reviewSubjectsTextView.setText("Please Wait While Data is Loading");
        binding.reviewSubjectsTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        final AsyncTaskLoader<Document> taskLoader = new AsyncTaskLoader<Document>(mContext) {
            @Override
            public Document loadInBackground() {
                try {
                    Connection.Response response_of_getting_data =
                            Jsoup.connect(CurrentSemesterGradesURL)
                                    .userAgent("Mozilla/5.0")
                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
                                    .cookies(mapLoginPageCookies)
                                    .execute();

                    return response_of_getting_data.parse();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void deliverResult(Document document) {

                semester_subjects = getCurrentSemesterSubjectsFinalTable(document);

                AsyncTaskLoader<Document> asyncTaskLoader = new AsyncTaskLoader<Document>(getActivity()) {
                    @Override
                    public Document loadInBackground() {
                        try {
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

                    @Override
                    public void deliverResult(Document document1) {

                        int returned = extractSemesterSubject(document1);
                        if (returned == 0) {
                            binding.reviewSubjectsTextView.setVisibility(View.INVISIBLE);
                            set_content_of_final_table();
                        } else {
                            binding.reviewSubjectsTextView.setText("There is no Exams Table");
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                refreshData();
                            }
                        });
                        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light);
                    }
                };

                asyncTaskLoader.forceLoad();

            }
        };

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {
                    taskLoader.forceLoad();
                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);

        return binding.getRoot();
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
                String subject_hours = subject_attributes.get(4).text();
                if (subject_hours.matches(" ") || subject_hours == null || subject_hours.matches("")) {
                    subject.setExamDate("لا يوجد تاريخ");
                } else {
                    subject.setExamDate(subject_hours);
                }
                subject.setName(subject_attributes.get(2).text());
                subject.setOldID(subject_attributes.get(1).text());
                subject.setID(subject_attributes.get(0).text());
                mExamTableTimeSubjects.add(subject);

                String current_subject_row_code = subject_attributes.get(0).text();

                for (int j = 0; j < semester_subjects.size(); j++) {

                    String subject_code = semester_subjects.get(j).getID();

                    if (subject_code.matches(current_subject_row_code)) {

                        if (subject_hours.matches(" ") || subject_hours == null || subject_hours.matches("")) {
                            semester_subjects.get(j).setExamDate("لا يوجد تاريخ");
                        } else {
                            semester_subjects.get(j).setExamDate(subject_hours);
                        }
                        semester_subjects.get(j).setHours(subject_attributes.get(3).text());

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


        examTableAdapter = new ExamTableAdapter(mContext, semester_subjects);
        recyclerView.setAdapter(examTableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


//        binding.examTable.setVisibility(View.GONE);
//
//        ExamTableAdapter examTableAdapter = new ExamTableAdapter(semester_subjects,getActivity());
//
//        listView.setAdapter(examTableAdapter);

    }

    private void refreshData() {

        final AsyncTaskLoader<Document> taskLoader = new AsyncTaskLoader<Document>(mContext) {
            @Override
            public Document loadInBackground() {
                try {
                    Connection.Response response_of_getting_data =
                            Jsoup.connect(CurrentSemesterGradesURL)
                                    .userAgent("Mozilla/5.0")
                                    .timeout(getResources().getInteger(R.integer.time_out) * 1000)
                                    .cookies(mapLoginPageCookies)
                                    .execute();

                    return response_of_getting_data.parse();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public void deliverResult(Document document) {

                semester_subjects = getCurrentSemesterSubjectsFinalTable(document);

                AsyncTaskLoader<Document> asyncTaskLoader = new AsyncTaskLoader<Document>(getActivity()) {
                    @Override
                    public Document loadInBackground() {
                        try {
                            Connection.Response response_of_getting_data_2 =
                                    Jsoup.connect(ExamTableTimeURL)
                                            .userAgent("Mozilla/5.0")
                                            .timeout(getResources().getInteger(R.integer.time_out) * 1000)
                                            .execute();

                            return response_of_getting_data_2.parse();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    public void deliverResult(Document document1) {

                        int returned = extractSemesterSubject(document1);
                        if (returned == 0) {
                            binding.reviewSubjectsTextView.setVisibility(View.INVISIBLE);
                            set_content_of_final_table();
                        } else {
                            binding.reviewSubjectsTextView.setText("There is no Exams Table");
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                        binding.swipeContainer.setRefreshing(false);


                    }
                };

                asyncTaskLoader.forceLoad();

            }
        };

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {
                    taskLoader.forceLoad();
                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);

    }

}
