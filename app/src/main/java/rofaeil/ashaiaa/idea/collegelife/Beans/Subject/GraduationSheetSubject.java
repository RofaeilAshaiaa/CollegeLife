package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

/**
 * Created by emad on 1/4/2017.
 */

public class GraduationSheetSubject extends Subject {

    private String Nodes;
    private String Points;
    private String EarnedGrade;
    private String ActualGrade;
    private String Hours;
    private String Semester;
    private String Category;

    public String getNodes() {
        return Nodes;
    }

    public void setNodes(String nodes) {
        this.Nodes = nodes;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        this.Points = points;
    }

    public String getEarnedGrade() {
        return EarnedGrade;
    }

    public void setEarnedGrade(String earnedGrade) {
        this.EarnedGrade = earnedGrade;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        this.Hours = hours;
    }

    public String getActualGrade() {
        return ActualGrade;
    }

    public void setActualGrade(String actualGrade) {
        this.ActualGrade = actualGrade;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        this.Semester = semester;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }
}
