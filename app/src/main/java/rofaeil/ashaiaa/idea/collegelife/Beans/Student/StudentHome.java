package rofaeil.ashaiaa.idea.collegelife.Beans.Student;

/**
 * Created by emad on 1/4/2017.
 */

public class StudentHome extends Student {

    private String FullName;
    private String CGPA;
    private String Minor;
    private String Level;
    private String Branch;
    private String Major;
    private String AcademicAdvisor;
    private String EarnedHours;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getCGPA() {
        return CGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getMinor() {
        return Minor;
    }

    public void setMinor(String minor) {
        this.Minor = minor;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        this.Level = level;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        this.Branch = branch;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        this.Major = major;
    }

    public String getAcademicAdvisor() {
        return AcademicAdvisor;
    }

    public void setAcademicAdvisor(String academicAdvisor) {
        this.AcademicAdvisor = academicAdvisor;
    }

    public String getEarnedHours() {
        return EarnedHours;
    }

    public void setEarnedHours(String earnedHours) {
        this.EarnedHours = earnedHours;
    }
}
