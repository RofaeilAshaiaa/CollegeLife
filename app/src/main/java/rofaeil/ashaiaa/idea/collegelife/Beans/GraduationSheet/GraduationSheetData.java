package rofaeil.ashaiaa.idea.collegelife.Beans.GraduationSheet;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.GraduationSheetSubject;


/**
 * Created by emad on 1/3/2017.
 */

public class GraduationSheetData {
    private ArrayList<GraduationRequirements> GraduationRequirements;
    private ArrayList<GraduationSheetSubject> Subject;

    public ArrayList<GraduationRequirements> getGraduationRequirements() {
        return GraduationRequirements;
    }

    public void setGraduationRequirements(ArrayList<GraduationRequirements> graduationRequirements) {
        this.GraduationRequirements = graduationRequirements;
    }

    public ArrayList<GraduationSheetSubject> getSubject() {
        return Subject;
    }

    public void setSubject(ArrayList<GraduationSheetSubject> subject) {
        this.Subject = subject;
    }
}
