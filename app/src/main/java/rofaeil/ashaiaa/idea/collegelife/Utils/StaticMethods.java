package rofaeil.ashaiaa.idea.collegelife.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

import rofaeil.ashaiaa.idea.collegelife.Beans.Semester.Semester;
import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentHome;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.CurrentSemesterGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.ExamTableTimeSubject;
import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;
import rofaeil.ashaiaa.idea.collegelife.R;


public class StaticMethods {

    public static ArrayList<CurrentSemesterGradesSubject> getCurrentSemesterSubjects(Document document) {
        ArrayList<CurrentSemesterGradesSubject> mSubjects = new ArrayList<>();

        Element mSubject_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        Elements mSubjects_raws = mSubject_table.getElementsByTag("tr");

        for (int i = 1; i < mSubjects_raws.size(); i++) {
            int BackgroundId = new Random().nextInt(9);
            CurrentSemesterGradesSubject mSubject = new CurrentSemesterGradesSubject();
            mSubject.setID(mSubjects_raws.get(i).getAllElements().get(1).text());
            mSubject.setOldID(mSubjects_raws.get(i).getAllElements().get(2).text());
            mSubject.setName(mSubjects_raws.get(i).getAllElements().get(3).text());
            mSubject.setQuiz1(mSubjects_raws.get(i).getAllElements().get(6).text());
            mSubject.setMidTerm(mSubjects_raws.get(i).getAllElements().get(7).text());
            mSubject.setQuiz2(mSubjects_raws.get(i).getAllElements().get(8).text());
            mSubject.setOral(mSubjects_raws.get(i).getAllElements().get(9).text());
            mSubject.setTotalTerm(mSubjects_raws.get(i).getAllElements().get(10).text());
            mSubject.setLab(mSubjects_raws.get(i).getAllElements().get(11).text());
            mSubject.setFinal(mSubjects_raws.get(i).getAllElements().get(12).text());
            mSubject.setTotal(mSubjects_raws.get(i).getAllElements().get(13).text());
            mSubject.setBackgroundId(BackgroundId);
            mSubject.setOldIdBackgroundResource(getSubjectOldIdBackgroundResource(BackgroundId));
            mSubject.setTextBackgroundResource(getTextBackgroundResource(BackgroundId));
            mSubjects.add(mSubject);
        }
        return mSubjects;
    }

    public static ArrayList<ExamTableTimeSubject> getCurrentSemesterSubjectsFinalTable(Document document) {

        ArrayList<ExamTableTimeSubject> mSubjects = new ArrayList<>();

        Element subject_table = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        Elements subjects_rows = subject_table.getElementsByTag("tr");

        for (int i = 1; i < subjects_rows.size(); i++) {
            ExamTableTimeSubject mSubject = new ExamTableTimeSubject();
            mSubject.setID(subjects_rows.get(i).getAllElements().get(1).text());
            mSubject.setOldID(subjects_rows.get(i).getAllElements().get(2).text());
            mSubject.setName(subjects_rows.get(i).getAllElements().get(3).text());

            mSubjects.add(mSubject);
        }
        return mSubjects;
    }

    public static void showProgressBarSpinner(ProgressDialog progressBar, String message) {

        progressBar.setMessage(message);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    public static void showProgressBarHorizontalPercentage(ProgressDialog progressBar, String message) {

        progressBar.setMessage(message);
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setMax(100);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    public static ArrayList<StudentGradesSubject> extractLastSemesterReviewSubjects(Document document) {

        //gets the target table by its id
        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        if (target_table != null) {
            //gets rows of the table
            Elements rows_of_target_table = target_table.getElementsByTag("tr");


            ArrayList<StudentGradesSubject> semester_subjects_codes = new ArrayList<>();

            for (int i = 1; i < rows_of_target_table.size(); i++) {
                StudentGradesSubject subject = new StudentGradesSubject();

                subject.setType(rows_of_target_table.get(i).getElementsByTag("td").get(0).text());
                subject.setID(rows_of_target_table.get(i).getElementsByTag("td").get(1).text());
                subject.setOldID(rows_of_target_table.get(i).getElementsByTag("td").get(2).text());
                subject.setName(rows_of_target_table.get(i).getElementsByTag("td").get(3).text());
                subject.setHours(rows_of_target_table.get(i).getElementsByTag("td").get(7).text());


                semester_subjects_codes.add(subject);

            }

            return semester_subjects_codes;

        } else {
            return null;
        }

    }

    public static ArrayList<ExamTableTimeSubject> extract_last_semester_exams_table(Document document) {

        //gets the target table by its id
        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        if (target_table != null) {
            //gets rows of the table
            Elements rows_of_target_table = target_table.getElementsByTag("tr");


            ArrayList<ExamTableTimeSubject> semester_subjects_codes = new ArrayList<>();

            for (int i = 1; i < rows_of_target_table.size(); i++) {
                ExamTableTimeSubject subject = new ExamTableTimeSubject();

                subject.setType(rows_of_target_table.get(i).getElementsByTag("td").get(0).text());
                subject.setID(rows_of_target_table.get(i).getElementsByTag("td").get(1).text());
                subject.setOldID(rows_of_target_table.get(i).getElementsByTag("td").get(2).text());
                subject.setName(rows_of_target_table.get(i).getElementsByTag("td").get(3).text());
                subject.setHours(rows_of_target_table.get(i).getElementsByTag("td").get(7).text());


                semester_subjects_codes.add(subject);

            }

            return semester_subjects_codes;

        } else {
            return null;
        }

    }

    public static int calculateTotalHoursOfSemester(Semester semester) {

        int total_hours = 0;

        for (int i = 0; i < semester.getSubjects().size(); i++) {

            total_hours += Integer.parseInt(semester.getSubjects().get(i).getHours());

        }


        return total_hours;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isResponseSuccess(Connection.Response response) {
        if (response.statusCode() == 200) {
            return true;
        } else {
            return false;
        }
    }

    public static String getResponseDescription(Connection.Response response) {
        if (response.statusCode() == 100) {
            return "The server has received the request headers, and the client should proceed to send the request body";
        } else if (response.statusCode() == 101) {
            return "The requester has asked the server to switch protocols";
        } else if (response.statusCode() == 103) {
            return "Used in the resumable requests proposal to resume aborted PUT or POST requests";
        } else if (response.statusCode() == 200) {
            return "The request is OK (this is the standard response for successful HTTP requests)";
        } else if (response.statusCode() == 201) {
            return "The request has been fulfilled, and a new resource is created ";
        } else if (response.statusCode() == 202) {
            return "The request has been accepted for processing, but the processing has not been completed";
        } else if (response.statusCode() == 203) {
            return "The request has been successfully processed, but is returning information that may be from another source";
        } else if (response.statusCode() == 204) {
            return "The request has been successfully processed, but is not returning any content";
        } else if (response.statusCode() == 205) {
            return "The request has been successfully processed, but is not returning any content, and requires that the requester reset the document view";
        } else if (response.statusCode() == 206) {
            return "The server is delivering only part of the resource due to a range header sent by the client";
        } else if (response.statusCode() == 300) {
            return "A link list. The user can select a link and go to that location. Maximum five addresses";
        } else if (response.statusCode() == 301) {
            return "The requested page has moved to a new URL";
        } else if (response.statusCode() == 302) {
            return "The requested page has moved temporarily to a new URL";
        } else if (response.statusCode() == 303) {
            return "The requested page can be found under a different URL";
        } else if (response.statusCode() == 304) {
            return "Indicates the requested page has not been modified since last requested";
        } else if (response.statusCode() == 306) {
            return "No longer used";
        } else if (response.statusCode() == 307) {
            return "The requested page has moved temporarily to a new URL";
        } else if (response.statusCode() == 308) {
            return "Used in the resumable requests proposal to resume aborted PUT or POST requests";
        } else if (response.statusCode() == 400) {
            return "The request cannot be fulfilled due to bad syntax";
        } else if (response.statusCode() == 401) {
            return "The request was a legal request, but the server is refusing to respond to it. For use when authentication is possible but has failed or not yet been provided";
        } else if (response.statusCode() == 402) {
            return "Reserved for future use";
        } else if (response.statusCode() == 403) {
            return "The request was a legal request, but the server is refusing to respond to it";
        } else if (response.statusCode() == 404) {
            return "The requested page could not be found but may be available again in the future";
        } else if (response.statusCode() == 405) {
            return "A request was made of a page using a request method not supported by that page";
        } else if (response.statusCode() == 406) {
            return "The server can only generate a response that is not accepted by the client";
        } else if (response.statusCode() == 407) {
            return "The client must first authenticate itself with the proxy";
        } else if (response.statusCode() == 408) {
            return "The server timed out waiting for the request";
        } else if (response.statusCode() == 409) {
            return "The request could not be completed because of a conflict in the request";
        } else if (response.statusCode() == 410) {
            return "The requested page is no longer available";
        } else if (response.statusCode() == 411) {
            return "The \"Content-Length\" is not defined. The server will not accept the request without it";
        } else if (response.statusCode() == 412) {
            return "The precondition given in the request evaluated to false by the server";
        } else if (response.statusCode() == 413) {
            return "The server will not accept the request, because the request entity is too large";
        } else if (response.statusCode() == 414) {
            return "The server will not accept the request, because the URL is too long. Occurs when you convert a POST request to a GET request with a long query information";
        } else if (response.statusCode() == 415) {
            return "The server will not accept the request, because the media type is not supported";
        } else if (response.statusCode() == 416) {
            return "The client has asked for a portion of the file, but the server cannot supply that portion";
        } else if (response.statusCode() == 417) {
            return "The server cannot meet the requirements of the Expect request-header field";
        } else if (response.statusCode() == 500) {
            return "A generic error message, given when no more specific message is suitable";
        } else if (response.statusCode() == 501) {
            return "The server either does not recognize the request method, or it lacks the ability to fulfill the request";
        } else if (response.statusCode() == 502) {
            return "The server was acting as a gateway or proxy and received an invalid response from the upstream server";
        } else if (response.statusCode() == 503) {
            return "The server is currently unavailable (overloaded or down)";
        } else if (response.statusCode() == 504) {
            return "The server was acting as a gateway or proxy and did not receive a timely response from the upstream server";
        } else if (response.statusCode() == 505) {
            return "The server does not support the HTTP protocol version used in the request";
        } else if (response.statusCode() == 511) {
            return "The client needs to authenticate to gain network access";
        } else {
            return "NO Description";
        }
    }

    public static StudentData getStudentData(Document document) {
        StudentData mStudentData = new StudentData();
        mStudentData.setID(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistSIDLabel").text());
        mStudentData.setFirstNameAR(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistArFNameTextBox").val());
        mStudentData.setSecondNameAR(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistArMName1TextBox").attr("value"));
        mStudentData.setThirdNameAR(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistArMName2TextBox").attr("value"));
        mStudentData.setFourthNameAR(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistArLNameTextBox").attr("value"));
        mStudentData.setFirstNameEN(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistEnFNameTextBox").attr("value"));
        mStudentData.setSecondNameEN(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistEnMName1TextBox").attr("value"));
        mStudentData.setThirdNameEN(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistEnMName2TextBox").attr("value"));
        mStudentData.setFourthNameEN(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistEnLNameTextBox").attr("value"));
        mStudentData.setGender(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistGenderDropDownList").getElementsByAttribute("selected").text());
        mStudentData.setNationalNumber(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistNationalNumberTextBox").attr("value"));
        mStudentData.setTelephoneNumber(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistPhoneTextBox").attr("value"));
        mStudentData.setMobileNumber(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistMobileTextBox").attr("value"));
        mStudentData.setFacEmail(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistFacEmailTextBox").attr("value"));
        mStudentData.setAlterEmail(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegistAlterEmailTextBox").attr("value"));
        mStudentData.setAddress(document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_AddressTextBox").text());
        return mStudentData;
    }

    public static StudentHome getStudentHome(Document document) {
        StudentHome mStudent = new StudentHome();
        Element mStudentDataTable = document.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView");
        Elements mStudentDataRows = mStudentDataTable.child(0).child(0).child(0).child(0).child(0).children();

        mStudent.setFullName(mStudentDataRows.get(0).children().get(1).text());
        mStudent.setID(mStudentDataRows.get(1).children().get(1).text());
        mStudent.setBranch(mStudentDataRows.get(2).children().get(1).text());
        mStudent.setMajor(mStudentDataRows.get(3).children().get(1).text());
        mStudent.setMinor(mStudentDataRows.get(4).children().get(1).text());
        mStudent.setLevel(mStudentDataRows.get(6).children().get(1).text());
        mStudent.setEarnedHours(mStudentDataRows.get(7).children().get(1).text());
        mStudent.setCGPA(mStudentDataRows.get(8).children().get(1).text());
        mStudent.setAcademicAdvisor(mStudentDataRows.get(10).children().get(1).text());

        return mStudent;
    }

    public static int getStudentLevel(String level) {
        int mStudentLevel;
        if (level.equals("المستوى الاول")) {
            mStudentLevel = 1;
            return mStudentLevel;
        } else if (level.equals("المستوى الثاني")) {
            mStudentLevel = 2;
            return mStudentLevel;
        } else if (level.equals("المستوى الثالث")) {
            mStudentLevel = 3;
            return mStudentLevel;
        } else if (level.equals("المستوى الرابع")) {
            mStudentLevel = 4;
            return mStudentLevel;
        }
        return 0;
    }

    public static int getSubjectOldIdBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.old_id_logo_blue_background;
                break;
            case 1:
                backgroundResource = R.drawable.old_id_logo_blue_grey_background;
                break;
            case 2:
                backgroundResource = R.drawable.old_id_logo_brown_background;
                break;
            case 3:
                backgroundResource = R.drawable.old_id_logo_deep_purple_background;
                break;
            case 4:
                backgroundResource = R.drawable.old_id_logo_green_background;
                break;
            case 5:
                backgroundResource = R.drawable.old_id_logo_indigo_background;
                break;
            case 6:
                backgroundResource = R.drawable.old_id_logo_pink_background;
                break;
            case 7:
                backgroundResource = R.drawable.old_id_logo_purple_background;
                break;
            case 8:
                backgroundResource = R.drawable.old_id_logo_red_background;
                break;
            case 9:
                backgroundResource = R.drawable.old_id_logo_teal_background;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    public static int getTextBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.text_logo_blue_background;
                break;
            case 1:
                backgroundResource = R.drawable.text_logo_blue_grey_background;
                break;
            case 2:
                backgroundResource = R.drawable.text_logo_brown_background;
                break;
            case 3:
                backgroundResource = R.drawable.text_logo_deep_purple_background;
                break;
            case 4:
                backgroundResource = R.drawable.text_logo_green_background;
                break;
            case 5:
                backgroundResource = R.drawable.text_logo_indigo_background;
                break;
            case 6:
                backgroundResource = R.drawable.text_logo_pink_background;
                break;
            case 7:
                backgroundResource = R.drawable.text_logo_purple_background;
                break;
            case 8:
                backgroundResource = R.drawable.text_logo_red_background;
                break;
            case 9:
                backgroundResource = R.drawable.text_logo_teal_background;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    public static int getTextLeftCornerBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.text_logo_blue_background_left_corner;
                break;
            case 1:
                backgroundResource = R.drawable.text_logo_blue_grey_background_left_corner;
                break;
            case 2:
                backgroundResource = R.drawable.text_logo_brown_background_left_corner;
                break;
            case 3:
                backgroundResource = R.drawable.text_logo_deep_purple_background_left_corner;
                break;
            case 4:
                backgroundResource = R.drawable.text_logo_green_background_left_corner;
                break;
            case 5:
                backgroundResource = R.drawable.text_logo_indigo_background_left_corner;
                break;
            case 6:
                backgroundResource = R.drawable.text_logo_pink_background_left_corner;
                break;
            case 7:
                backgroundResource = R.drawable.text_logo_purple_background_left_corner;
                break;
            case 8:
                backgroundResource = R.drawable.text_logo_red_background_left_corner;
                break;
            case 9:
                backgroundResource = R.drawable.text_logo_teal_background_left_corner;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    public static int getIconBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.ic_logo_blue_background;
                break;
            case 1:
                backgroundResource = R.drawable.ic_logo_blue_grey_background;
                break;
            case 2:
                backgroundResource = R.drawable.ic_logo_brown_background;
                break;
            case 3:
                backgroundResource = R.drawable.ic_logo_deep_purple_background;
                break;
            case 4:
                backgroundResource = R.drawable.ic_logo_green_background;
                break;
            case 5:
                backgroundResource = R.drawable.ic_logo_indigo_background;
                break;
            case 6:
                backgroundResource = R.drawable.ic_logo_pink_background;
                break;
            case 7:
                backgroundResource = R.drawable.ic_logo_purple_background;
                break;
            case 8:
                backgroundResource = R.drawable.ic_logo_red_background;
                break;
            case 9:
                backgroundResource = R.drawable.ic_logo_teal_background;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    public static int getIconRightCornerBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.ic_logo_blue_background_right_corner;
                break;
            case 1:
                backgroundResource = R.drawable.ic_logo_blue_grey_background_right_corner;
                break;
            case 2:
                backgroundResource = R.drawable.ic_logo_brown_background_right_corner;
                break;
            case 3:
                backgroundResource = R.drawable.ic_logo_deep_purple_background_right_corner;
                break;
            case 4:
                backgroundResource = R.drawable.ic_logo_green_background_right_corner;
                break;
            case 5:
                backgroundResource = R.drawable.ic_logo_indigo_background_right_corner;
                break;
            case 6:
                backgroundResource = R.drawable.ic_logo_pink_background_right_corner;
                break;
            case 7:
                backgroundResource = R.drawable.ic_logo_purple_background_right_corner;
                break;
            case 8:
                backgroundResource = R.drawable.ic_logo_red_background_right_corner;
                break;
            case 9:
                backgroundResource = R.drawable.ic_logo_teal_background_right_corner;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    public static int getSemesterLogoBackgroundResource(int backgroundId) {
        int backgroundResource = 0;
        switch (backgroundId) {
            case 0:
                backgroundResource = R.drawable.semester_logo_blue_background;
                break;
            case 1:
                backgroundResource = R.drawable.semester_logo_blue_grey_background;
                break;
            case 2:
                backgroundResource = R.drawable.semester_logo_brown_background;
                break;
            case 3:
                backgroundResource = R.drawable.semester_logo_deep_purple_background;
                break;
            case 4:
                backgroundResource = R.drawable.semester_logo_green_background;
                break;
            case 5:
                backgroundResource = R.drawable.semester_logo_indigo_background;
                break;
            case 6:
                backgroundResource = R.drawable.semester_logo_pink_background;
                break;
            case 7:
                backgroundResource = R.drawable.semester_logo_purple_background;
                break;
            case 8:
                backgroundResource = R.drawable.semester_logo_red_background;
                break;
            case 9:
                backgroundResource = R.drawable.semester_logo_teal_background;
                break;
            default:
                break;
        }
        return backgroundResource;
    }

    //check if network available and connected to one
    public static boolean isNetworkAvailable(FragmentActivity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void inflateOfflineLayout(Context context,FrameLayout frameLayout){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.offline_layout,frameLayout,false);
        frameLayout.addView(view);
    }

    public static void showSnackbarWithAction(View view , String message, int duration,
                                              String actionString , View.OnClickListener listener) {

        Snackbar.make(view , message, duration)
                .setAction(actionString, listener )
                .show();
    }


    public static void deleteOfflineLayout(FrameLayout frameLayout) {
        frameLayout.removeViewAt(1);
    }
}
