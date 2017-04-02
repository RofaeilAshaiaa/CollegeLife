package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.GraduationSheetURL;

/**
 * Created by emad on 1/1/2017.
 */
public class AsyncTaskGraduationSheet extends AsyncTask<String, Void, Document> {
    @Override
    protected Document doInBackground(String... params) {

        try {

            Connection.Response mGraduation_sheet_response = Jsoup.connect(GraduationSheetURL)
                    .timeout(70 * 1000)
                    .userAgent("Mozilla/5.0")
                    .cookies(MainActivity.mapLoginPageCookies)
                    .execute();

            return mGraduation_sheet_response.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
