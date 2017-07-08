package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades.SemesterSubject;


import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getIconBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getIconRightCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getSubjectOldIdBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextLeftCornerBackgroundResource;

public class SemesterSubjectActivity extends AppCompatActivity implements
        SemesterSubjectFragment.StudentGradesSemesterDataSet {


    private final String VIEW_STATE = "0Lyyj9NPpdnddB6pakBAUYzptMJIMQbGYxPeA/ofZp6ZRiMMa7ZXUs3dz8ajVg6FEPGtqiTQe1hNxUcYxIL3/Li8FJVsm4jOKaOaqBNsf4l0D9snUwUKx/0A/H2L5zmenH7qD/I9ArVUk7bDsmHpPMBATFzydB2f2o6Zc6lj9/OOkf2zOVtxlnSrg/W+E4TPk/BVrC0R3CUl0BpGkLbLIVrxiBe1aXoAm9eYh12lspe+UHHlwSFSuGvxFNQ3tLHtnZDYFi0dzvNSsf9WV98z4Q==" ;
    private final String VIEW_STATE_GENERATOR = "F7FE45A7";
    private final String EVENT_VALIDATION = "nwol/vBRfDWTLde1S4/NC/4tBd9SEGC7zvP3WfnQ77DFLvolKZwlPTlw0HlhUf5QM0joff9DV0832m4YrbEQjdWymAOxW4RjfV9kAjLNweWKaYeGhhF16zav9o52H7gB93gHxRdzYQSGVdwL5tMSCcGGOc7X/9xd7s7aZcPTgbUC5NKk9iMjkPw9BHtosdmg";

    public ArrayList<StudentGradesSubject> mSubjectsHasPolling;
    private ArrayList<StudentGradesSubject> mSemesterSubjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_grades_semester_subjects_activity);

        mSemesterSubjects = getStudentGradesSemesterSubject();

        initializeToolbar();
        initializeSemesterData();
        initializePolling();

    }

    public void initializeToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.student_grades_semester_subjects_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("Name", null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public void initializeSemesterData() {
        TextView mCGPA = (TextView) findViewById(R.id.student_grades_semester_subjects_activity_cgpa_num);
        TextView mGPA = (TextView) findViewById(R.id.student_grades_semester_subjects_activity_gpa_num);
        TextView mLoadedHours = (TextView) findViewById(R.id.student_grades_semester_subjects_activity_loaded_hours_num);
        TextView mEarnedHours = (TextView) findViewById(R.id.student_grades_semester_subjects_activity_earned_hours_num);
        TextView mSubjectNum = (TextView) findViewById(R.id.student_grades_semester_subjects_activity_subjects_num);

        mCGPA.setText(getIntent().getExtras().getString("CGPA", null));
        mGPA.setText(getIntent().getExtras().getString("GPA", null));
        mLoadedHours.setText(getIntent().getExtras().getString("SemesterLoad", null));
        mEarnedHours.setText(getIntent().getExtras().getString("EarnedHours", null));
        mSubjectNum.setText("" + mSemesterSubjects.size() + "");

        ProgressBar mEarnedHoursNumProgressBar = (ProgressBar) findViewById(R.id.student_grades_semester_subjects_activity_earned_hours_num_progress_bar);
        ProgressBar mGPANumProgressBar = (ProgressBar) findViewById(R.id.student_grades_semester_subjects_activity_gpa_num_progress_bar);
        ProgressBar mSubjectsNumProgressBar = (ProgressBar) findViewById(R.id.student_grades_semester_subjects_activity_subjects_num_progress_bar);

        mEarnedHoursNumProgressBar.setMax(Integer.parseInt(getIntent().getExtras().getString("SemesterLoad", null)));
        mEarnedHoursNumProgressBar.setProgress(Integer.parseInt(getIntent().getExtras().getString("EarnedHours", null)));

        mGPANumProgressBar.setMax(4);
        mGPANumProgressBar.setProgress((int) Double.parseDouble(getIntent().getExtras().getString("GPA", null)));

        mSubjectsNumProgressBar.setMax(mSemesterSubjects.size());
        mSubjectsNumProgressBar.setProgress(getSuccessSubjectsNum());


        ImageView mCGPAState = (ImageView) findViewById(R.id.student_grades_semester_subjects_activity_cgpa_state);
        if (Double.parseDouble(getIntent().getExtras().getString("CGPA", null)) > Double.parseDouble(getIntent().getExtras().getString("PreviousSemesterCGPA", null))) {
            mCGPAState.setImageResource(R.drawable.ic_cgpa_up_white_48dp);
        } else if (Double.parseDouble(getIntent().getExtras().getString("CGPA", null)) < Double.parseDouble(getIntent().getExtras().getString("PreviousSemesterCGPA", null))) {
            mCGPAState.setImageResource(R.drawable.ic_cgpa_down_white_48dp);
        } else {
            mCGPAState.setImageResource(R.drawable.ic_cgpa_same_white_48dp);
        }

    }

    public int getSuccessSubjectsNum() {
        int mFailSubjectsNam = 0;
        for (int i = 0; i < mSemesterSubjects.size(); i++) {
            if (mSemesterSubjects.get(i).getGrade().equals("F")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (mSemesterSubjects.get(i).getGrade().equals("إستبيان")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (mSemesterSubjects.get(i).getGrade().equals("P")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
        }
        return mSemesterSubjects.size() - mFailSubjectsNam;
    }

    public ArrayList<StudentGradesSubject> getStudentGradesSemesterSubject() {

        ArrayList<StudentGradesSubject> mSubjects = new ArrayList<>();
        String SubjectDocument = getIntent().getExtras().getString("SubjectsDocument");
        Document document = Jsoup.parse(SubjectDocument);
        Elements mSemester_Subjects = document.getElementsByTag("tr");

        for (int n = 1; n < mSemester_Subjects.size(); n++) {

            StudentGradesSubject mSubject = new StudentGradesSubject();
            int BackgroundID = new Random().nextInt(10);
            Elements mSubject_data = mSemester_Subjects.get(n).getElementsByTag("td");

            mSubject.setID(mSubject_data.get(0).text());
            mSubject.setOldID(mSubject_data.get(1).text());
            mSubject.setName(mSubject_data.get(2).text());
            mSubject.setPollingUrl(mSubject_data.get(2).child(0).absUrl("href"));
            mSubject.setGrade(mSubject_data.get(3).text());
            mSubject.setPoints(mSubject_data.get(4).text());
            mSubject.setHours(mSubject_data.get(5).text());
            mSubject.setPoints_X_Hours(mSubject_data.get(6).text());
            mSubject.setBackgroundId(BackgroundID);
            mSubject.setOldIdBackgroundResource(getSubjectOldIdBackgroundResource(BackgroundID));
            mSubject.setIconBackgroundResource(getIconBackgroundResource(BackgroundID));
            mSubject.setIconRightCornerBackgroundResource(getIconRightCornerBackgroundResource(BackgroundID));
            mSubject.setLeftCornerBackgroundResource(getTextLeftCornerBackgroundResource(BackgroundID));
            mSubject.setTextBackgroundResource(getTextBackgroundResource(BackgroundID));
            mSubjects.add(mSubject);
        }

        return mSubjects;
    }

    @Override
    public String getSemesterName() {
        return getIntent().getExtras().getString("Name", null);
    }

    @Override
    public String getSemesterCGPA() {
        return getIntent().getExtras().getString("CGPA", null);
    }

    @Override
    public String getSemesterGPA() {
        return getIntent().getExtras().getString("GPA", null);
    }

    @Override
    public String getSemesterEarnedHours() {
        return getIntent().getExtras().getString("EarnedHours", null);
    }

    @Override
    public String getSemesterSemesterLoad() {
        return getIntent().getExtras().getString("SemesterLoad", null);
    }

    @Override
    public ArrayList<StudentGradesSubject> getSemesterSubjects() {
        return getStudentGradesSemesterSubject();
    }

    public void initializePolling() {
        Button mPolling = (Button) findViewById(R.id.student_grades_semester_subjects_activity_polling_button);
        if (getIntent().getExtras().getBoolean("LastSemester", false) == true) {
            mPolling.setVisibility(View.VISIBLE);
            mSubjectsHasPolling = new ArrayList<>();
            mSubjectsHasPolling = getSubjectsHasPolling(mSemesterSubjects);
            mPolling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FinishingAllPollingStagesV2(mSubjectsHasPolling);
                }
            });
        }
    }

    public ArrayList<StudentGradesSubject> getSubjectsHasPolling(ArrayList<StudentGradesSubject> mStudentGradesSubjects) {

        ArrayList<StudentGradesSubject> mSubjectsHasPolling = new ArrayList<>();
        for (int i = 0; i < mStudentGradesSubjects.size(); i++) {
            if (mStudentGradesSubjects.get(i).getGrade().equals("إستبيان")) {
                mSubjectsHasPolling.add(mStudentGradesSubjects.get(i));
            }
        }
        return mSubjectsHasPolling;
    }

    public void FinishingPollingStage1(StudentGradesSubject mSubjectsHasPolling) {
        try {
            Connection.Response mResponsePollingStage1 =
                    Jsoup.connect(mSubjectsHasPolling.getPollingUrl())
                            .userAgent("Mozilla/5.0")
                            .timeout(50 * 1000)
                            .cookies(mapLoginPageCookies)
                            .method(Connection.Method.POST)
                            .data("__EVENTTARGET", "")
                            .data("__EVENTARGUMENT", "")
                            .data("__VIEWSTATE", VIEW_STATE)
                            .data("__VIEWSTATEGENERATOR", VIEW_STATE_GENERATOR)
                            .data("__VIEWSTATEENCRYPTED", "")
                            .data("__EVENTVALIDATION", EVENT_VALIDATION)

                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl02$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl03$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl04$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl05$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl06$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl07$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl08$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl09$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl10$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl11$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl12$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl13$RadioButtonList1", "1")


                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl02$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl03$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl04$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl05$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl06$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl07$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl08$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl09$RadioButtonList1", "1")


                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl02$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl03$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl04$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl05$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl06$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl07$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl08$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl09$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl10$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl11$RadioButtonList1", "1")


                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl02$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl03$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl04$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl05$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl06$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl07$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl08$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl09$RadioButtonList1", "1")


                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl02$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl03$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl04$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl05$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl06$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl07$RadioButtonList1", "1")
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl08$RadioButtonList1", "1")


                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$RegistCourseGenerlPollButton", "التالي")
                            .followRedirects(true)
                            .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FinishingPollingStage2(StudentGradesSubject mSubjectsHasPolling) {
        try {
            Connection.Response mResponsePollingStage2 = Jsoup.connect(mSubjectsHasPolling.getPollingUrl())
                    .timeout(50 * 1000)
                    .userAgent("Mozilla/5.0")
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", VIEW_STATE)
                    .data("__VIEWSTATEGENERATOR", VIEW_STATE_GENERATOR)
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", EVENT_VALIDATION)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$SelectStaffListCheckBoxList$0", "         1;         1;    999999")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$SelectStaffButton", "إختيار أعضاء هيئة التدريس")
                    .followRedirects(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FinishingPollingStage3(StudentGradesSubject mSubjectsHasPolling) {
        try {
            Connection.Response mResponsePollingStage3 = Jsoup.connect(mSubjectsHasPolling.getPollingUrl())
                    .timeout(50 * 1000)
                    .userAgent("Mozilla/5.0")
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", VIEW_STATE)
                    .data("__VIEWSTATEGENERATOR", VIEW_STATE_GENERATOR)
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", EVENT_VALIDATION)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl02$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl03$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl04$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl05$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl06$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl07$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl08$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl09$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl10$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl11$Grade", "RadioButton2")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$RegistStaffGenerlPollButton", "التالي")
                    .followRedirects(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FinishingPollingStage4(StudentGradesSubject mSubjectsHasPolling) {
        try {
            Connection.Response mResponsePollingStage4 = Jsoup.connect(mSubjectsHasPolling.getPollingUrl())
                    .timeout(50 * 1000)
                    .userAgent("Mozilla/5.0")
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", VIEW_STATE)
                    .data("__VIEWSTATEGENERATOR", VIEW_STATE_GENERATOR)
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", EVENT_VALIDATION)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$FinishPollButton0", "إنهاء")
                    .followRedirects(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FinishingAllPollingStagesV2(final ArrayList<StudentGradesSubject> mSubjectsHasPolling) {
        final AsyncTaskLoader<Document> loader = new AsyncTaskLoader<Document>(this) {
            @Override
            public Document loadInBackground() {

                for (int i = 0; i < mSubjectsHasPolling.size(); i++) {

                    FinishingPollingStage1(mSubjectsHasPolling.get(i));
                    FinishingPollingStage2(mSubjectsHasPolling.get(i));
                    FinishingPollingStage3(mSubjectsHasPolling.get(i));
                    FinishingPollingStage4(mSubjectsHasPolling.get(i));

                    //                  return null;
                }
                return null;
            }
        };
        loader.forceLoad();
    }

    public void FinishingAllPollingStagesV1(final ArrayList<StudentGradesSubject> mSubjectsHasPolling) {
        final AsyncTaskLoader<Document> loader = new AsyncTaskLoader<Document>(this) {
            @Override
            public Document loadInBackground() {

                for (int i = 0; i < mSubjectsHasPolling.size(); i++) {

                    try {
                        Connection.Response response =
                                Jsoup.connect(mSubjectsHasPolling.get(i).getPollingUrl())
                                        .userAgent("Mozilla/5.0")
                                        .timeout(10 * 1000)
                                        .cookies(mapLoginPageCookies)
                                        .method(Connection.Method.POST)
                                        .data("__EVENTTARGET", "")
                                        .data("__EVENTARGUMENT", "")
                                        .data("__VIEWSTATE", "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")
                                        .data("__VIEWSTATEGENERATOR", "CAD75A91")
                                        .data("__VIEWSTATEENCRYPTED", "")
                                        .data("__EVENTVALIDATION", "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")

                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl02$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl03$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl04$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl05$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl06$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl07$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl08$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl09$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl10$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl11$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl12$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl00$PointsGridView$ctl13$RadioButtonList1", "1")


                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl02$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl03$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl04$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl05$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl06$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl07$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl08$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl01$PointsGridView$ctl09$RadioButtonList1", "1")


                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl02$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl03$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl04$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl05$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl06$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl07$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl08$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl09$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl10$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl02$PointsGridView$ctl11$RadioButtonList1", "1")


                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl02$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl03$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl04$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl05$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl06$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl07$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl08$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl03$PointsGridView$ctl09$RadioButtonList1", "1")


                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl02$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl03$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl04$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl05$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl06$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl07$RadioButtonList1", "1")
                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$CourseFormView$SectionsDataList$ctl04$PointsGridView$ctl08$RadioButtonList1", "1")


                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$RegistCourseGenerlPollButton", "التالي")
                                        .followRedirects(true)
                                        .execute();

                        Connection.Response response1 = Jsoup.connect(mSubjectsHasPolling.get(i).getPollingUrl())
                                .timeout(50 * 1000)
                                .userAgent("Mozilla/5.0")
                                .method(Connection.Method.POST)
                                .cookies(mapLoginPageCookies)
                                .data("__EVENTTARGET", "")
                                .data("__EVENTARGUMENT", "")
                                .data("__VIEWSTATE", "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")
                                .data("__VIEWSTATEGENERATOR", "CAD75A91")
                                .data("__VIEWSTATEENCRYPTED", "")
                                .data("__EVENTVALIDATION", "DKiQ0eK4mF/BDTzP1N0jRydLyaTjnPpjxJTmxwMp8R89U9p5E3ML7YJfS/D2oYd3SmUC23+YgoofZbfj/K4rLfbGeNl5QmJDmIEhxwKUuCSta6xpT2DQuwtyZ59nfZ6PdREnrEnYZ7zzRhI7fLPTYKBfWmt7tW3FRgIza2oum7UoPT+tM6FIxMlZYkVrqhcX")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$SelectStaffListCheckBoxList$0", "         1;         1;    999999")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$SelectStaffButton", "إختيار أعضاء هيئة التدريس")
                                .followRedirects(true)
                                .execute();

                        Connection.Response response2 = Jsoup.connect(mSubjectsHasPolling.get(i).getPollingUrl())
                                .timeout(50 * 1000)
                                .userAgent("Mozilla/5.0")
                                .method(Connection.Method.POST)
                                .cookies(mapLoginPageCookies)
                                .data("__EVENTTARGET", "")
                                .data("__EVENTARGUMENT", "")
                                .data("__VIEWSTATE", "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")
                                .data("__VIEWSTATEGENERATOR", "CAD75A91")
                                .data("__VIEWSTATEENCRYPTED", "")
                                .data("__EVENTVALIDATION", "m/SywKuXiHcWWtRxM59l2jrKPa2hdjpDcvCHCz9jiHiy0bjQYlAF4qaLtucgEjfjkxCavS+tuVdhsOLmS4jlnVNvaqVtcFHj26FV05Fx8/94yMJnJa7uiO49/ATmqp/HInX5Fn+ngY4UZmMnJAqIVzb7/9/bESZU7E8MFNubJ1GW3KROOTAPPnnDdYDhdei5ImoJXcdorpofkVRAoV9LSUoRN+ws9Cgfy3n13KwvF6P7QIvHtWCtd8vvBpRSnvRTttHEhuI4fZjFbz6smYUIdHyClmqCc67STkiTfONRTkD+2aZl996YV3F6vJs1G6LkD+iRS4sOGUAhjolVwoMVncXCsivyrEU6jIjYm4nwvcYzhBauUheRmCErNL9krHKm/EB3n7IjP7JJt6YwV5oeRJuTZWHUcpXkrDMnnh2iPi4XkRbRycrl49W+MImgpQ9bWRvWghR7cnu+vUeJcN/eZczgsLecRGrYsmqmPTYTmh0Amxjjdy9tx87e2qKp4FvJ7QomYwqDomFjzEjXq7NUsOKmQmJiamY8SH9qcGSmyRMVFw3PWg9wsUDlq+1+PFVyNGxBy/A4Chdp6Ms8lwXEC0l3H8SEqVqIA4za4tJHG9pShQOBG5QkYIQhHCiwMI59Yasw/eApp+v6Y6bjqdcy0cqslpJHTVgEKfm34mtq7neI2qz9aplj/A3S/3Ufzgz2a7XSnlPamXIzE/xW5bE0IMU+8USF3ToJ95xYP4L6yt7uDk+Uo1ur66PxWGLLh0iVr7zauPU8fEjZObkWzv3uTsWiVtRpAgXIyTE6c3rb1FoDfN0K5m1RhbEzftfOGjGE/6Tb2mzmyCyxLsiAsaTE9FUSM5kvOU6BShFlrqZNP0W7Yqxam20wv6yuZLlryhuE7fIswmZogoh+kKU/9x8vxjOrpNeZTwn3PDaxk7OtyTbpP7YFjd/FBrWqRIdVfknP03YK7ArzpjACHQrgV4zzWU9XsUy+PL85eXuDLU2MQVDuV7qB8jxNFl1bGxSmlgYOQyGeZGhssE0pn44Tg7e5lzxdkQg9Yrh3MlfuP3lUnBxzYDvti5CHAHdTzF+MBDyHvKogj4h7NAFuA4CNaWZ8l6dVy3nkljZp2ZTY5MXrw4lmAsXmhiWlo4HUmM3aZ4Cr+YbxQsRC9whXZJOHlGP/qlMhW5SsBRbgs+rIoY5hkAou4nE/H7NiJBpoqDQhzk+rXLzJo70of/biIze8xqT9cg==")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl02$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl03$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl04$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl05$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl06$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl07$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl08$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl09$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl10$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$StaffPollFormView$DataList1$ctl00$StaffPollGridView$ctl11$Grade", "RadioButton2")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$RegistStaffGenerlPollButton", "التالي")
                                .followRedirects(true)
                                .execute();

                        Connection.Response response3 = Jsoup.connect(mSubjectsHasPolling.get(i).getPollingUrl())
                                .timeout(50 * 1000)
                                .userAgent("Mozilla/5.0")
                                .method(Connection.Method.POST)
                                .cookies(mapLoginPageCookies)
                                .data("__EVENTTARGET", "")
                                .data("__EVENTARGUMENT", "")
                                .data("__VIEWSTATE", "0Aw0+DEN3xa5ip9pD7041DlYuTepbEyJPvs29pRaNnxhj8RPi7luLZkGa509F4XLvRLITgo9fAM39TVcoy7qEtZ35vuosDtBsMjuow/NZ595xa7zxWxwKrzd/yrjVAygHWjMx1gHbrCWp24IIJLkxcb95AUuww9zjiGpTvBurc1fIYT/bCoze8M6qgpvmKV8nLsSSIfR/5MScn5fgNbwKrcni6kuFdIohKJodjjqBzSy2JLkQnwfEwFSZ2+/b9Boo1CGNSx/Y+Vm0XXAlK4JaRRexH7uXNh4/gEh3TfzoLIHYf04/KmjBKnkOEwt1Z2p8qfaKGiSphKTUJsP4dRU0Ro5xsaEjKlYk/HHb5URBz7l2MWQSsCrET4NUTUcgeVRy7pf0gUckQlA+czaEH1sMSnsXT9y3tiEa63PfevImNV9BnF9lMz4iY1e8l2Ev47ESbceXPus65fvcDmeEuPeVulBcJUDzeubUwecLk+tPPE+yqgDz2hEBh/MH7HyRRb9kFWNHzdUs1R1eLwO5QllPYdNhV7j6xaN8Oe2wRaAlRxQLbah9Gmko73LN9ePTvI5DPot3wp3iI4iywpuM60p4PYnALV0NsfX69qxSwV2p2RAFQ6Cvxc6whaURmj/DB3WMCamMs084TuDCuPcjOhCvGntk6+vUplq9gYy4WqvwEhh8tyCisIWtbTs77tjpeBIYe+/0dJnA8a8Ig7+lyJHzrDSuwehImLRheV42Dy1/c1OwTAxVNLHSwFvguyYxde9oJ6/dKsepS1K9uzF3ty0b8V0SsCtEM8ParkqZ8riJyxWwUVNaksJNvpAR8oX66QtwyF6sAizQXACWow2j1g0An49GTp3UhBYB8PNa4tsamBWOGhM+aF9/pd8hXJ1EeFmdelUc2So22Hwgkp0C2wcX2dNGIfDl+xMB2sFG79TNDQ14GpwavWraMPSl1lGxd1+QSXzleX8MBV7y8A9jes2edAZdaWDp/MH61WLi+qCNYJSEl1qjTTpvZJK9tWdMGMylIBcdKDt1+cdOUEezhEfmzXGSu3qvASMHMF920xxLnIja1KGQ0iFB9BAQn7pl8lItM1HvfZbd8euHP4ck7DcxJ1oV/qI9+yI6mJenOceNkSAzkF4pFWJfRNG8uXJKgSh2Ma2Sq/SxYCoQ8yo+PjsE6Gl81n5lvHpho52XTWj0VHmlWZm+NyiuEtHpBvGE/v8UzW4g8CvJkkfzdTbDXqgsdgCn4oC0kwddT77Sgdxx0hTphnmNyHfK4xPfEdIXPj9rvGmPDsrRBYOQKfaEv261dKlb29jTSIW7Gy0vsMpOatV7THjmd1jywbOqjjSlKl84Nr7oZeKotLj1q2UpXjLa+RlKlCApFfjRbAtpvB7yFVpSq/uA8HM4StmRCgANjsQnLzXSX2LSS4EXV5v0OY7vacupAKKuE6C6yfNZMBQXDXxS9NnbuVS8jT9M3jyXt8psgcierBBkyy+L/TaPdZxhxjFbhzufv5nIbvPEF+urJrJglwewL2/hq5cLNPTfFwcFZGZoa5aVHIVq+woZhleJi2Qj3Rj88u44EyMCEZTtX35yITUjbLdzf3UDC2TKkhRDHjSWsHVWTcGQX1eM+r/nWVsZR1AQi6qmWw16eRod2cTSWda4OLoh9Z+mWOOBK7WZr/zQbmMLDoqXDgC4XxsdRhxYct/gm9K523YNpVcvyRm2j6Ne+YM1Dpr+HMHXW88A0yLq+vYNk9FbES6xq6w7Krg6K0ztsGscQg+FHEm5WSCvKQJsPJOtG3NxLcl9dJFeR2JEs1FuQFBP6Nngr9mPU1ubZ19VXZJgmmMS/T7z+/mKLzIEZrR5olI+ZSIiDykQlcrNoTBwXbPXJGCpA6UIGNh97tuK66pH0LI0YDS3Gd6py5x/PcHr/N3vlKIkMUOuZYGqrgZMUIfyTpQy5f1UXHHsCiiuXOvKN7CdH408JA=")
                                .data("__VIEWSTATEGENERATOR", "CAD75A91")
                                .data("__VIEWSTATEENCRYPTED", "")
                                .data("__EVENTVALIDATION", "0E6I+iWeiT8ppulAjep3/QTqfmEQkMLJgcD0fAJyyXBHml4i/6PmN1gmTSuS/kXix3E36w8N0dg8+HsMRCo5zFOTD5yyrydcj5znaxySJllFfMaJQlxUfE4z4JKv7EZzuHaZiYwA1ziemP80vtwhg+OAKyp/Iu2MpXR52Bb0vfOXER6JRQejtWCDQZjgJqkkn8n31mqjJTwJb+hggnreGFEBluz1Iag+0zeIf62xgks=")
                                .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$HeadContentPlaceHolder$FinishPollButton0", "إنهاء")
                                .followRedirects(true)
                                .execute();


                        return response3.parse();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        loader.forceLoad();

    }


}
