package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

/**
 * Created by emad on 1/4/2017.
 */

public class CurrentSemesterGradesSubject extends Subject {

    private String Oral;
    private String MidTerm;
    private String Quiz1;
    private String Quiz2;
    private String TotalTerm;
    private String Lab;
    private String Final;
    private String Total;
    private int TextBackgroundResource;
    private int OldIdBackgroundResource;

    public int getTextBackgroundResource() {
        return TextBackgroundResource;
    }

    public void setTextBackgroundResource(int textBackgroundResource) {
        TextBackgroundResource = textBackgroundResource;
    }

    public int getOldIdBackgroundResource() {
        return OldIdBackgroundResource;
    }

    public void setOldIdBackgroundResource(int oldIdBackgroundResource) {
        OldIdBackgroundResource = oldIdBackgroundResource;
    }

    public String getOral() {
        return Oral;
    }

    public void setOral(String oral) {
        this.Oral = oral;
    }

    public String getMidTerm() {
        return MidTerm;
    }

    public void setMidTerm(String midTerm) {
        this.MidTerm = midTerm;
    }

    public String getQuiz1() {
        return Quiz1;
    }

    public void setQuiz1(String quiz1) {
        this.Quiz1 = quiz1;
    }

    public String getTotalTerm() {
        return TotalTerm;
    }

    public void setTotalTerm(String totalTerm) {
        this.TotalTerm = totalTerm;
    }

    public String getLab() {
        return Lab;
    }

    public void setLab(String lab) {
        this.Lab = lab;
    }

    public String getQuiz2() {
        return Quiz2;
    }

    public void setQuiz2(String quiz2) {
        this.Quiz2 = quiz2;
    }

    public String getFinal() {
        return Final;
    }

    public void setFinal(String aFinal) {
        this.Final = aFinal;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

}
