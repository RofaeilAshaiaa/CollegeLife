package rofaeil.ashaiaa.idea.collegelife.Beans.Semester;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.StudentGradesSubject;

/**
 * Created by emad on 1/4/2017.
 */

public class Semester {
    private String Name;
    private String CGPA;
    private String GPA;
    private String EarnedHours;
    private String SemesterLoad;
    private ArrayList<StudentGradesSubject> Subjects;
    private String SubjectsDocument;

    public String getSubjectsDocument() {
        return SubjectsDocument;
    }

    public void setSubjectsDocument(String subjectsDocument) {
        SubjectsDocument = subjectsDocument;
    }

    private int BackgroundId;
    private int LogoBackgroundResource;
    private int TextBackgroundResource;
    private int TextLeftCornerBackgroundResource;
    private int IconRightCornerBackgroundResource;

    public int getLogoBackgroundResource() {
        return LogoBackgroundResource;
    }

    public void setLogoBackgroundResource(int logoBackgroundResource) {
        LogoBackgroundResource = logoBackgroundResource;
    }

    public int getTextBackgroundResource() {
        return TextBackgroundResource;
    }

    public void setTextBackgroundResource(int textBackgroundResource) {
        TextBackgroundResource = textBackgroundResource;
    }

    public int getTextLeftCornerBackgroundResource() {
        return TextLeftCornerBackgroundResource;
    }

    public void setTextLeftCornerBackgroundResource(int textLeftCornerBackgroundResource) {
        TextLeftCornerBackgroundResource = textLeftCornerBackgroundResource;
    }

    public int getIconRightCornerBackgroundResource() {
        return IconRightCornerBackgroundResource;
    }

    public void setIconRightCornerBackgroundResource(int iconRightCornerBackgroundResource) {
        IconRightCornerBackgroundResource = iconRightCornerBackgroundResource;
    }

    public int getBackgroundId() {
        return BackgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        BackgroundId = backgroundId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCGPA() {
        return CGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }

    public String getEarnedHours() {
        return EarnedHours;
    }

    public void setEarnedHours(String earnedHours) {
        this.EarnedHours = earnedHours;
    }

    public String getSemesterLoad() {
        return SemesterLoad;
    }

    public void setSemesterLoad(String academicLoad) {
        this.SemesterLoad = academicLoad;
    }

    public ArrayList<StudentGradesSubject> getSubjects() {
        return Subjects;
    }

    public void setSubjects(ArrayList<StudentGradesSubject> subjects) {
        this.Subjects = subjects;
    }
}
