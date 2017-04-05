package rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentData;
import rofaeil.ashaiaa.idea.collegelife.R;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.ReviewRegistrationSubjectsURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentDataURL;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getStudentData;

public class MyAsyncTaskLoaderReviewRegisteredSubjects extends AsyncTaskLoader<Document> {

    StudentData mStudentData;
    private Context mContext;

    public MyAsyncTaskLoaderReviewRegisteredSubjects(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Document loadInBackground() {


        try {

            Connection.Response StudentDataGET = Jsoup.connect(StudentDataURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(mContext.getResources().getInteger(R.integer.time_out) * 1000)
                    .method(Connection.Method.GET)
                    .cookies(mapLoginPageCookies)
                    .followRedirects(true)
                    .execute();

            Document mStudentDataDocument = StudentDataGET.parse();

            mStudentData = getStudentData(mStudentDataDocument);

            Connection.Response responseStudentData = Jsoup.connect(StudentDataURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(mContext.getResources().getInteger(R.integer.time_out) * 1000)
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", mContext.getString(R.string.__VIEWSTATE))
                    .data("__VIEWSTATEGENERATOR", mContext.getString(R.string.__VIEWSTATEGENERATOR))
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", mContext.getString(R.string.__EVENTVALIDATION))
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistArFNameTextBox", mStudentData.getFirstNameAR())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistArMName1TextBox", mStudentData.getSecondNameAR())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistArMName2TextBox", mStudentData.getThirdNameAR())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistArLNameTextBox", mStudentData.getFourthNameAR())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistEnLNameTextBox", mStudentData.getFourthNameEN())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistEnMName2TextBox", mStudentData.getThirdNameEN())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistEnMName1TextBox", mStudentData.getSecondNameEN())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistEnFNameTextBox", mStudentData.getFirstNameEN())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistGenderDropDownList", mStudentData.getGender())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistNationalNumberTextBox", mStudentData.getNationalNumber())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistPhoneTextBox", mStudentData.getTelephoneNumber())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistMobileTextBox", mStudentData.getMobileNumber())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistAlterEmailTextBox", mStudentData.getAlterEmail())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$AddressTextBox", mStudentData.getAddress())
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ConfirmCheckBox", "on")
                    .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$RegistSaveButton", "حفظ البيانات")
                    .followRedirects(true)
                    .execute();

//            Document document1 = responseStudentData.parse() ;
//            Log.d("mymy", document1.toString());

            Connection.Response response_of_getting_data =
                    Jsoup.connect(ReviewRegistrationSubjectsURL)
                            .userAgent("Mozilla/5.0")
                            .timeout(mContext.getResources().getInteger(R.integer.time_out) * 1000)
                            .method(Connection.Method.GET)
                            .cookies(mapLoginPageCookies)
                            .execute();

            Document document = response_of_getting_data.parse();
//            Log.d("mymy", document.toString());
            return document;

        } catch (IOException e) {
            e.printStackTrace();
//            Log.v("crash", "crash from first request 2");
        }

        return null;
    }

}
