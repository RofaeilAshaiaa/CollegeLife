package rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

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

public class AsyncTaskLoaderCurrentSemesterGrades extends AsyncTaskLoader<Connection.Response> {
    public Context mContext;

    public AsyncTaskLoaderCurrentSemesterGrades(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Connection.Response loadInBackground() {
        try {
            Connection.Response mStudent_grades_response = Jsoup.connect(CurrentSemesterGradesURL)
                    .cookies(MainActivity.mapLoginPageCookies)
                    .timeout(mContext.getResources().getInteger(R.integer.time_out) * 1000)
                    .userAgent("Mozilla/5.0")
                    .execute();

            return mStudent_grades_response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
