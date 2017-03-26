package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentGradesURL;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskStudentGrades extends AsyncTask<String, Void, Document> {

    @Override
    protected Document doInBackground(String[] params) {

        try {
            Connection.Response mStudent_grades_response = Jsoup.connect(StudentGradesURL)
                    .cookies(MainActivity.mapLoginPageCookies)
                    .timeout(70 * 1000)
                    .userAgent("Mozilla/5.0")
                    .execute();

            return mStudent_grades_response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
