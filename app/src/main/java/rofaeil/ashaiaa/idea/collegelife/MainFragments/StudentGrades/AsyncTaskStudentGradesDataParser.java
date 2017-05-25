package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentGradesURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getIconRightCornerBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getSemesterLogoBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextBackgroundResource;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getTextLeftCornerBackgroundResource;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskStudentGradesDataParser extends AsyncTask<Document, Void, ArrayList<Semester>> {

    @Override
    protected ArrayList<Semester> doInBackground(Document[] params) {

        return getStudentSemesters(params[0]);
    }

    public ArrayList<Semester> getStudentSemesters(Document document) {

        ArrayList<Semester> mSemesters = new ArrayList<>();
        Element mRoot_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1");
        Elements mSemesters_tables = mRoot_table.children().get(0).children();
        for (int i = 0; i < mSemesters_tables.size(); i++) {

            Semester mSemester = new Semester();
            int BackgroundId = new Random().nextInt(10);

            Element mSemester_table = mSemesters_tables.get(i);

            mSemester.setName(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_SemesterNameLabel_" + i + "").text());
            mSemester.setGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_GPALabel").text());
            mSemester.setSemesterLoad(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterLoadLabel").text());
            mSemester.setEarnedHours(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_SemesterCHLabel").text());
            mSemester.setCGPA(mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_FormView1_" + i + "_CGPALabel").text());
            mSemester.setBackgroundId(BackgroundId);
            mSemester.setLogoBackgroundResource(getSemesterLogoBackgroundResource(BackgroundId));
            mSemester.setTextBackgroundResource(getTextBackgroundResource(BackgroundId));
            mSemester.setIconRightCornerBackgroundResource(getIconRightCornerBackgroundResource(BackgroundId));
            mSemester.setTextLeftCornerBackgroundResource(getTextLeftCornerBackgroundResource(BackgroundId));

            Element mSemester_Subjects_table = mSemester_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_DataList1_GridView1_" + i + "");

            mSemester.setSubjectsDocument(mSemester_Subjects_table.toString());
            Elements Subjects = mSemester_Subjects_table.child(0).children();
            int SubjectSize = Subjects.size();

            mSemester.setSubjectNum(SubjectSize - 1);
            mSemester.setSuccessSubjectsNum(getSuccessSubjectsNum(SubjectSize, Subjects));

            mSemesters.add(mSemester);
        }

        return mSemesters;
    }

    public int getSuccessSubjectsNum(int SubjectSize, Elements Subjects) {
        int mFailSubjectsNam = 0;
        for (int j = 1; j < SubjectSize; j++) {
            String SubjectGrade = Subjects.get(j).child(3).text();
            if (SubjectGrade.equals("F")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (SubjectGrade.equals("إستبيان")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
            if (SubjectGrade.equals("P")) {
                mFailSubjectsNam = mFailSubjectsNam + 1;
            }
        }
        return SubjectSize - mFailSubjectsNam - 1;
    }
}
