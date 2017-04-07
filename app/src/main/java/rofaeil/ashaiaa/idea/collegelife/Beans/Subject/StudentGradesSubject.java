package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emad on 1/4/2017.
 */

public class StudentGradesSubject extends Subject {

    private String Hours;
    private String Points;
    private String Grade;
    private String Points_X_Hours;
    private String PollingUrl;

    public StudentGradesSubject() {
        super();
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        this.Hours = hours;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        this.Points = points;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        this.Grade = grade;
    }

    public String getPoints_X_Hours() {
        return Points_X_Hours;
    }

    public void setPoints_X_Hours(String points_X_Hours) {
        this.Points_X_Hours = points_X_Hours;
    }

    public String getPollingUrl() {
        return PollingUrl;
    }

    public void setPollingUrl(String pollingUrl) {
        this.PollingUrl = pollingUrl;
    }

}
