package rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.databinding.ReviewRegisteredSubjectsFragmentBinding;

import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.calculate_total_hours_of_semester;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.extractLastSemesterReviewSubjects;

public class ReviewRegisteredSubjectsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {

    private ReviewRegisteredSubjectsFragmentBinding binding;
    private ArrayList<StudentGradesSubject> semester_subjects;
    private ProgressBar progressBar;
    private Context mContext;
    private Handler mHandler;
    private ReviewRegisteredSubjectsFragment mFragment;

    public ReviewRegisteredSubjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate
                (inflater, R.layout.review_registered_subjects_fragment, container, false);
        progressBar = binding.progressBarReviewSubjects;
        mContext = getContext();
        mHandler = new Handler();
        mFragment = this;

        binding.reviewSubjectsTextView.setText("Please Wait While Data is Loading");
        binding.reviewSubjectsTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

//        getActivity().getSupportLoaderManager().initLoader(1, null,this ).forceLoad();

        Runnable runnable = new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.mapLoginPageCookies != null) {

                    getActivity().getSupportLoaderManager().initLoader(1, null, mFragment).forceLoad();

                } else {
                    mHandler.postDelayed(this, 100);
                }
            }
        };

        mHandler.post(runnable);
        start_request_and_get_data();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void start_request_and_get_data() {


    }

    private void set_content_of_views(ArrayList<StudentGradesSubject> semester_subjects) {


        Semester semester = new Semester();
        semester.setSubjects(semester_subjects);
        int total_hours_of_semester = calculate_total_hours_of_semester(semester);

        RecyclerView recyclerView = binding.recyclerview;
        ReviewRegisteredSubjectsAdapter adapter = new ReviewRegisteredSubjectsAdapter(getActivity(), semester_subjects);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "user refreshed", Toast.LENGTH_SHORT).show();
                binding.swipeContainer.setRefreshing(false);
            }
        });
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        ExamTableAdapter registeredSubjectsAdapter = new ExamTableAdapter(semester_subjects, getActivity());
//        binding.reviewSubjectsList.setAdapter(registeredSubjectsAdapter);
//
//        binding.totalHoursReviewSubjects.setText("total_hours_of_semester : " + total_hours_of_semester);

    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new MyAsyncTaskLoaderReviewRegisteredSubjects(mContext);
    }


    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {

        if (document != null) {

            semester_subjects = extractLastSemesterReviewSubjects(document);

            if (semester_subjects != null) {

                set_content_of_views(semester_subjects);
                binding.reviewSubjectsTextView.setVisibility(View.INVISIBLE);
            } else {

                binding.reviewSubjectsTextView.setText("There is no Subjects to View");

            }

        } else {
            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }


}
