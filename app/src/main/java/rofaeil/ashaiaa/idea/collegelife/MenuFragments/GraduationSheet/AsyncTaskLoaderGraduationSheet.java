package rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity;
import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationRequirements;
import rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet.GraduationSheetData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.GraduationSheetSubject;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.GraduationSheetURL;

/**
 * Created by emad on 1/1/2017.
 */
public class AsyncTaskLoaderGraduationSheet extends AsyncTaskLoader<Connection.Response> {

    public AsyncTaskLoaderGraduationSheet(Context context) {
        super(context);
    }

    @Override
    public Connection.Response loadInBackground() {
        try {

            Connection.Response mGraduation_sheet_response = Jsoup.connect(GraduationSheetURL)
                    .timeout(70 * 1000)
                    .userAgent("Mozilla/5.0")
                    .cookies(MainActivity.mapLoginPageCookies)
                    .execute();

            return mGraduation_sheet_response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
