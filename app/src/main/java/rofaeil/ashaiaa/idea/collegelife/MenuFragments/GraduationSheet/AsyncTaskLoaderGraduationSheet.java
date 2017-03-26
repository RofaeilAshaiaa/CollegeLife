package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.GraduationSheetURL;

/**
 * Created by emad on 1/1/2017.
 */
public class AsyncTaskLoaderGraduationSheet extends AsyncTaskLoader<Document> {

    public AsyncTaskLoaderGraduationSheet(Context context) {
        super(context);
    }

    @Override
    public Document loadInBackground() {
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
