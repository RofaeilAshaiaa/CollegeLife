package rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.StudentData;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentData;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.StudentDataURL;

/**
 * Created by Matrix on 12/31/2016.
 */

public class AsyncTaskStudentDataPOST extends AsyncTask<String, Void, Document> {
    StudentData mStudentData;

    public AsyncTaskStudentDataPOST() {
        super();
    }

    public AsyncTaskStudentDataPOST(StudentData studentData) {
        mStudentData = studentData;
    }

    @Override
    protected Document doInBackground(String... params) {

        try {
            Connection.Response StudentData = Jsoup.connect(StudentDataURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(50 * 1000)
                    .method(Connection.Method.POST)
                    .cookies(mapLoginPageCookies)
                    .data("__EVENTTARGET", "")
                    .data("__EVENTARGUMENT", "")
                    .data("__VIEWSTATE", "DReX57nCVu7K53zTf8LwUjcUWJXAZJDbKurIYGHfQIdrc8+f3qgQLhCl8TtvAtXCPbmhABrsMjSHOfxsCoIVmb1Knc0wsBWS7GJ7O5Rpd/t5R3+yJna/GQrZ/IS7/pGTcwnRDmqKOl7Q5z7I54DbjS4OwrX/TskvINdiR3TOOLiU+f3uceqQKHEp1Vc9yjux7BlpR38SyWRLEtL5Mv9sDbRu1WQqSIg2leAdOt62PA9Jt4Z97YZh4LF7mzOjkaiWUIw5Xi6PFRcfzzjZK2Tr33e2PJtF9W0zJiEkh/WFhnxXGT7W4TmAVHuu7vavTPTXw7sqV2zWb1G13LzJqJNDKnwyKPigWtGvsyalskprWs9barpTK6QFwPy7ly5Hce+kBDcWvKZzEe0CRYNIf6gNYHBP+plCeKmPL/kl2eKM0UtvhZd1ZyAfmvMUxOu+FtCk3O9h0DlrOT+5Tn0/nXktT48VyHRZJZXVoBZir65TbV0jrYepTPhX4RRQ5MRhv+y+3jMnjyrxvYdAYHmNYOhgGvsBTNuicZCSiFi/4mQIFgIMjVWspSzdi+bzJL/U2Gdg5yjceR6AwNy0N6loBxd2Xqjhi50ZTh+z5s/PpSinadq3szlBjI5T1C0gd0EhbtPgzzl5nRzroYaIRhWM5aYweFYFS8EPlTJtORxdbBJZnRMIC7V9GODEUpek0mBERn2LqBKc2xO9dkUuJsBwi526Fw==")
                    .data("__VIEWSTATEGENERATOR", "F7FE45A7")
                    .data("__VIEWSTATEENCRYPTED", "")
                    .data("__EVENTVALIDATION", "peJ3GyEOhDRyY3hHbyBGr6wx0mKPS/6dXK8/te1l3EaopSRodlPjZ4P5Txh9xYnkYo3bJryYJNf2LSBQKgRTZrVL1UrAzz7M+6oM9tmxXlRZAfraXqjngzjP+jd1xccZk0tzCbidkzhMvYeRtKiRSqXWyyOgBMrBjVa4CnlGlak3qIooAMpFgOakEspYz7cjZ4chKwH8othXXW6wHT8OpPBnRS8l1fdEIESNy6oZpqjadawlhZY8EzSqhzqrybFDWTrtJSwHL15bLuhGpmDPdOUI4WHnyPvO4DpIAfVALGOwNZe4yrHibZB/mxU7Ix6ePsIbp84bAODKRayFJZwzCh5tCF6kB6Wimk5nLgBHbMSD4k+xkBj2OPWdbPDsAznWaKuyJDQyddc8mIYMr+cGK04cAepApgVQzqZVKW6aVG+oNaA7H48CXweWLk/hS+jpjDdF2UnyLbCLcz8pMOQzzXZsS1F6eberu00t5OMqVe1HM+MF7aD9cX1ofdHOBqLV2fctveEQzjGImyGMZuYgkDpjuaJ6FjbLsh1bOTihYAL3QgCZOu80fZHJEnflOcIacMs0i1fTG/3kMuzrJGmPKA==")
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

            Document document = StudentData.parse();
            return document;


        } catch (IOException e) {
            Log.v("crash", "crash from first request");
        }


        return null;
    }
}
