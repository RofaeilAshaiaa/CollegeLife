package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

/**
 * Created by emad on 1/4/2017.
 */

public class ExamTableTimeSubject extends Subject {

    private String ExamDate;
    private String Hours;
    private int BackgroundResourceIdOldCode;
    private int BackgroundResourceIdDateAndHours;

    public int getBackgroundResourceIdOldCode() {
        return BackgroundResourceIdOldCode;
    }

    public void setBackgroundResourceIdOldCode(int backgroundResourceIdOldCode) {
        BackgroundResourceIdOldCode = backgroundResourceIdOldCode;
    }

    public int getBackgroundResourceIdDateAndHours() {
        return BackgroundResourceIdDateAndHours;
    }

    public void setBackgroundResourceIdDateAndHours(int backgroundResourceIdDateAndHours) {
        BackgroundResourceIdDateAndHours = backgroundResourceIdDateAndHours;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getExamDate() {
        return ExamDate;
    }

    public void setExamDate(String examDate) {
        this.ExamDate = examDate;
    }
}
