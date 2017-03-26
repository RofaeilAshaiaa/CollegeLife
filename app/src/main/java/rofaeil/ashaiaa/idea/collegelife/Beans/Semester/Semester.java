package rofaeil.ashaiaa.idea.collegelife.Beans.Semester;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import rofaeil.ashaiaa.idea.collegelife.Beans.Subject.ExamTableTimeSubject;
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
    private int BackgroundId;

    public static ArrayList<StudentGradesSubject> extractLastSemesterReviewSubjects(Document document) {

        //gets the target table by its id
        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        if (target_table != null) {
            //gets rows of the table
            Elements rows_of_target_table = target_table.getElementsByTag("tr");


            ArrayList<StudentGradesSubject> semester_subjects_codes = new ArrayList<>();

            for (int i = 1; i < rows_of_target_table.size(); i++) {
                StudentGradesSubject subject = new StudentGradesSubject();

                subject.setType(rows_of_target_table.get(i).getElementsByTag("td").get(0).text());
                subject.setID(rows_of_target_table.get(i).getElementsByTag("td").get(1).text());
                subject.setOldID(rows_of_target_table.get(i).getElementsByTag("td").get(2).text());
                subject.setName(rows_of_target_table.get(i).getElementsByTag("td").get(3).text());
                subject.setHours(rows_of_target_table.get(i).getElementsByTag("td").get(7).text());


                semester_subjects_codes.add(subject);

            }

            return semester_subjects_codes;

        } else {
            return null;
        }

    }

    public static ArrayList<ExamTableTimeSubject> extract_last_semester_exams_table(Document document) {

        //gets the target table by its id
        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_RegisteredCoursesGridView");
        if (target_table != null) {
            //gets rows of the table
            Elements rows_of_target_table = target_table.getElementsByTag("tr");


            ArrayList<ExamTableTimeSubject> semester_subjects_codes = new ArrayList<>();

            for (int i = 1; i < rows_of_target_table.size(); i++) {
                ExamTableTimeSubject subject = new ExamTableTimeSubject();

                subject.setType(rows_of_target_table.get(i).getElementsByTag("td").get(0).text());
                subject.setID(rows_of_target_table.get(i).getElementsByTag("td").get(1).text());
                subject.setOldID(rows_of_target_table.get(i).getElementsByTag("td").get(2).text());
                subject.setName(rows_of_target_table.get(i).getElementsByTag("td").get(3).text());
                subject.setHours(rows_of_target_table.get(i).getElementsByTag("td").get(7).text());


                semester_subjects_codes.add(subject);

            }

            return semester_subjects_codes;

        } else {
            return null;
        }

    }

    public static int calculate_total_hours_of_semester(Semester semester) {

        int total_hours = 0;

        for (int i = 0; i < semester.getSubjects().size(); i++) {

            total_hours += Integer.parseInt(semester.getSubjects().get(i).getHours());

        }


        return total_hours;
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
