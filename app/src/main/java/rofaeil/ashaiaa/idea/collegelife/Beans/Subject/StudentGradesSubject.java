package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emad on 1/4/2017.
 */

public class StudentGradesSubject extends Subject implements Parcelable {

    public static final Creator<StudentGradesSubject> CREATOR = new Creator<StudentGradesSubject>() {
        @Override
        public StudentGradesSubject createFromParcel(Parcel in) {
            return new StudentGradesSubject(in);
        }

        @Override
        public StudentGradesSubject[] newArray(int size) {
            return new StudentGradesSubject[size];
        }
    };
    private String Hours;
    private String Points;
    private String Grade;
    private String Points_X_Hours;
    private String PollingUrl;

    public StudentGradesSubject() {
        super();
    }

    protected StudentGradesSubject(Parcel in) {
        Hours = in.readString();
        Points = in.readString();
        Grade = in.readString();
        Points_X_Hours = in.readString();
        PollingUrl = in.readString();
        super.setID(in.readString());
        super.setOldID(in.readString());
        super.setName(in.readString());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Hours);
        dest.writeString(Points);
        dest.writeString(Grade);
        dest.writeString(Points_X_Hours);
        dest.writeString(PollingUrl);
        dest.writeString(super.getID());
        dest.writeString(super.getOldID());
        dest.writeString(super.getName());
    }
}
