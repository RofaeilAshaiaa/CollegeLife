package rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.FinalData;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;

/**
 * Created by emad on 4/5/2017.
 */

public class AsyncTaskLoaderChangePassword extends AsyncTaskLoader<Document> {

    String mCurrentPassword;
    String mNewPassword;
    String mConfirmNewPassword;
    Context mContext;
    public AsyncTaskLoaderChangePassword(Context context ,String currentPassword,String newPassword ,String confirmNewPassword) {
        super(context);
        mContext = context;
        mConfirmNewPassword = confirmNewPassword;
        mCurrentPassword = currentPassword;
        mNewPassword = newPassword;
    }

    @Override
    public Document loadInBackground() {
        try {

            Connection.Response response =
                    Jsoup.connect(FinalData.ChangePasswordURL)
                            .userAgent("Mozilla/5.0")
                            .timeout(10 * 1000)
                            .followRedirects(true)
                            .method(Connection.Method.POST)
                            .cookies(mapLoginPageCookies)
                            .data("__EVENTTARGET", "")
                            .data("__EVENTARGUMENT", "")
                            .data("__VIEWSTATE", mContext.getString(R.string.__VIEWSTATE))
                            .data("__VIEWSTATEGENERATOR", mContext.getString(R.string.__VIEWSTATEGENERATOR))
                            .data("__VIEWSTATEENCRYPTED", "")
                            .data("__EVENTVALIDATION", mContext.getString(R.string.__EVENTVALIDATION))
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$CurrentPasswordTextBox", mCurrentPassword)
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$NewPasswordTextBox", mNewPassword)
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ConfirmPasswordTextBox", mConfirmNewPassword)
                            .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ChngeButton", "تغيير")
                            .execute();

            return response.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
