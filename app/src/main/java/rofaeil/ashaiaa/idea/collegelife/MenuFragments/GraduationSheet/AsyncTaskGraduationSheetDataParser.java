package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationRequirements;
import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationSheetData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.CurrentSemesterGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.GraduationSheetSubject;

import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getCurrentSemesterSubjects;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskGraduationSheetDataParser extends AsyncTask<Document, Void, GraduationSheetData> {

    @Override
    protected GraduationSheetData doInBackground(Document[] params) {

        GraduationSheetData graduationSheetData = new GraduationSheetData();
        graduationSheetData = getGraduationSheetData(params[0]);

        return graduationSheetData;
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
}
