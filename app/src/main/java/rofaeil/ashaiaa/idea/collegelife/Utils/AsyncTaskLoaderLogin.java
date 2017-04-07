package rofaeil.ashaiaa.idea.collegelife.Utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.DefaultHomeURL;

public class AsyncTaskLoaderLogin extends AsyncTaskLoader<Connection.Response> {

    private String mID;
    private String mPassword;

    public AsyncTaskLoaderLogin(Context context,String Password,String ID) {
        super(context);
        mID = ID;
        mPassword = Password;
    }

    @Override
    public Connection.Response loadInBackground() {
        try {
            Connection.Response LoginResponse = Jsoup.connect(DefaultHomeURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(70 * 1000)
                    .method(Connection.Method.POST)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", "0Lyyj9NPpdnddB6pakBAUYzptMJIMQbGYxPeA/ofZp6ZRiMMa7ZXUs3dz8ajVg6FEPGtqiTQe1hNxUcYxIL3/Li8FJVsm4jOKaOaqBNsf4l0D9snUwUKx/0A/H2L5zmenH7qD/I9ArVUk7bDsmHpPMBATFzydB2f2o6Zc6lj9/OOkf2zOVtxlnSrg/W+E4TPk/BVrC0R3CUl0BpGkLbLIVrxiBe1aXoAm9eYh12lspe+UHHlwSFSuGvxFNQ3tLHtnZDYFi0dzvNSsf9WV98z4Q==")
                    .data("__VIEWSTATEGENERATOR", "F7FE45A7")
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", "nwol/vBRfDWTLde1S4/NC/4tBd9SEGC7zvP3WfnQ77DFLvolKZwlPTlw0HlhUf5QM0joff9DV0832m4YrbEQjdWymAOxW4RjfV9kAjLNweWKaYeGhhF16zav9o52H7gB93gHxRdzYQSGVdwL5tMSCcGGOc7X/9xd7s7aZcPTgbUC5NKk9iMjkPw9BHtosdmg")
                    .data("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$SIDTextBox", mID)
                    .data("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$PasswordTextBox", mPassword)
                    .data("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$LoginButton", "تسجيل الدخول")
                    .followRedirects(true)
                    .execute();
            return LoginResponse;

        } catch (IOException e) {
            Log.v("crash", "crash from first request");
        }
        return null;
    }
}
