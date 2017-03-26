package rofaeil.ashaiaa.idea.collegelife.Utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Matrix on 12/31/2016.
 */

public class Student {

    private String full_name;
    private String first_name;
    private String second_name;
    private String third_name;
    private String fourth_name;
    private String major;
    private String cgpa;
    private String total_hours;
    private String academic_advisor;
    private String minor;
    private String level;
    private String id;
    private String branch;
    private String department;

    public Student() {
    }

    public static Student extract_student_data_from_document(Document document) {

        Student student = new Student();
        //gets the target table by its id
        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView")
                .getElementsByTag("table").get(0);

        Element full_name_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_NameLabel");
        student.setFull_name(full_name_element.text());

        Element id_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_SIDLabel");
        student.setId(id_element.text());

        Element level_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_LevelNameLabel");
        student.setLevel(level_element.text());

        Element hours_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_CreditLabel");
        student.setTotal_hours(hours_element.text());

        Element cgpa_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_CGPALabel");
        student.setCgpa(cgpa_element.text());

        Element advisor_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_AdvisorNameLabel");
        student.setAcademic_advisor(advisor_element.text());

        Element department_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_AdvisorDepartmentLabel");
        student.setDepartment(department_element.text());

        Element branch_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_BranchNameLabel");
        student.setBranch(branch_element.text());

        Element major_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_MajorLabel");
        student.setMajor(major_element.text());

        Element minor_element = target_table.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView_MinorLabel");
        student.setMinor(minor_element.text());

        return student;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getThird_name() {
        return third_name;
    }

    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    public String getFourth_name() {
        return fourth_name;
    }

    public void setFourth_name(String fourth_name) {
        this.fourth_name = fourth_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(String total_hours) {
        this.total_hours = total_hours;
    }

    public String getAcademic_advisor() {
        return academic_advisor;
    }

    public void setAcademic_advisor(String academic_advisor) {
        this.academic_advisor = academic_advisor;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
