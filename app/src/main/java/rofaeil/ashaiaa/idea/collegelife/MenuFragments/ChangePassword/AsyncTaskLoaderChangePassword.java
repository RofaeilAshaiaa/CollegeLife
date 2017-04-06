package rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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

    public AsyncTaskLoaderChangePassword(Context context, String currentPassword, String newPassword, String confirmNewPassword) {
        super(context);
        mContext = context;
        mConfirmNewPassword = confirmNewPassword;
        mCurrentPassword = currentPassword;
        mNewPassword = newPassword;
    }

    @Override
    public Document loadInBackground() {
        try {

            Connection.Response response = Jsoup.connect(FinalData.ChangePasswordURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(70 * 1000)
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", "HBH0k4gH4WC1XaEuySzGDAOuervbb3WUr+sXetzQBlElP/fcvQfvvwyw3xdGj4NMN/cLKJn6Q9A2FuE7a7UAThCcXp7GkapEo6QlwFSPXu5F8bCoOA1KtSMkizLsr/drdx5gdqMnf1csgtL9xrps6gssec6KJd5EZ3FX/OR2tUv5K9aAfqh0qAiB9vJE2DHFnMyNLtujTqJQIvaC+U8JOtkLiYTb4tAtWH9MBI2SLFg8FivCK4Xc0W+BTfUskqxH6fclW9Eb0NMlsDBwx9SaVzKxlQ2Jn62WyPi+MgG53AXnjUGFA4VqDMGzF38iyOx8MHdinm/bff/QHFemOPY5zrqqJrhhCwgnYBeUJ9RlCA9VLjUw59QJaTYlE1oC6YloIjHHRDHaVwf2CRDXuF5gBGaSJAbKM5QbhSQ1SDgqe7xEIWvjdi7Zj59FHPlqO6Zf27S5bTRyMR4Oximb+RmfFg==")
                    .data("__VIEWSTATEGENERATOR", "6E774491")
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", "Y2oy62K2eA5a4b3wi47jSVx/6u/mpj+ehPEXgN/bNgg9xCjOoYUp6PPna1XMQlkmwVpotymDHPoNNotHxxIDxFTRH0ORfBTIomxo9cCWRTQjKpazs7OyNO4O2RhrlOFA4tL64Uqd4XzoNWnfKj5GF0e8gD2YLbpeSDuqQp8T1HYVgqu0aQb2hW2S3+pJdVQLFB2YO1ujcIpNF85Xd3kG0WRs26ZWOsn+7E0JK75eRN4=")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$CurrentPasswordTextBox", mCurrentPassword)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$NewPasswordTextBox", mNewPassword)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ConfirmPasswordTextBox", mConfirmNewPassword)
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ChngeButton", "تغيير")
                    .followRedirects(true)
                    .execute();

            return response.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
