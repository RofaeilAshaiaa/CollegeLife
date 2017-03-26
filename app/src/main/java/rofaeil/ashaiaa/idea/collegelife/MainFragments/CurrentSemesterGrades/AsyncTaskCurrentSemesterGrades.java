package rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.CurrentSemesterGradesURL;

/**
 * Created by emad on 1/2/2017.
 */

public class AsyncTaskCurrentSemesterGrades extends AsyncTask<String, Void, Document> {
    public Context mContext;

    public AsyncTaskCurrentSemesterGrades(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Document doInBackground(String[] params) {

        try {
            Connection.Response mStudent_grades_response = Jsoup.connect(CurrentSemesterGradesURL)
                    .cookies(MainActivity.mapLoginPageCookies)
                    .timeout(mContext.getResources().getInteger(R.integer.time_out) * 1000)
                    .userAgent("Mozilla/5.0")
                    .execute();

            return mStudent_grades_response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
