package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

/**
 * Created by emad on 1/4/2017.
 */

public class ExamTableTimeSubject extends Subject {

    private String ExamDate;
    private String Hours;
    private int OldIdBackgroundResource;
    private int IconBackgroundResource;

    public int getOldIdBackgroundResource() {
        return OldIdBackgroundResource;
    }

    public void setOldIdBackgroundResource(int oldIdBackgroundResource) {
        OldIdBackgroundResource = oldIdBackgroundResource;
    }

    public int getIconBackgroundResource() {
        return IconBackgroundResource;
    }

    public void setIconBackgroundResource(int iconBackgroundResource) {
        IconBackgroundResource = iconBackgroundResource;
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
