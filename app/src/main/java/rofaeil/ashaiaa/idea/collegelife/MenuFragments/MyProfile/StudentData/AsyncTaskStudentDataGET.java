package rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.StudentData;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentDataURL;

/**
 * Created by Matrix on 12/31/2016.
 */

public class AsyncTaskStudentDataGET extends AsyncTask<String, Void, Document> {
    public AsyncTaskStudentDataGET() {
        super();
    }

    @Override
    protected Document doInBackground(String... params) {

        try {
            Connection.Response StudentData = Jsoup.connect(StudentDataURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(70 * 1000)
                    .method(Connection.Method.GET)
                    .cookies(mapLoginPageCookies)
                    .followRedirects(true)
                    .execute();

            Document document = StudentData.parse();
            return document;


        } catch (IOException e) {
            Log.v("crash", "crash from first request");
        }


        return null;
    }
}
