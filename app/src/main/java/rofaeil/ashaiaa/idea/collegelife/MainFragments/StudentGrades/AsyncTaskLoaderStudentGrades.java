package rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentGradesURL;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskLoaderStudentGrades extends AsyncTaskLoader<Document> {

    public AsyncTaskLoaderStudentGrades(Context context) {
        super(context);
    }

    @Override
    public Document loadInBackground() {
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
