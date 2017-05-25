package rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.CurrentSemesterGradesSubject;

import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getCurrentSemesterSubjects;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskCurrentSemesterGradesDataParser extends AsyncTask<Document, Void, ArrayList<CurrentSemesterGradesSubject>> {

    @Override
    protected ArrayList<CurrentSemesterGradesSubject> doInBackground(Document[] params) {

        ArrayList<CurrentSemesterGradesSubject> subjects = new ArrayList<>();
        subjects = getCurrentSemesterSubjects(params[0]);

        return subjects;
    }
}
