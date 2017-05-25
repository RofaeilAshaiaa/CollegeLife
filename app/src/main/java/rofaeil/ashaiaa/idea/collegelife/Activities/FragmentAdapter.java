package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import rofaeil.ashaiaa.idea.collegelife.MainFragments.AddRemoveSubjects.AddRemoveSubjectsFragment;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.CurrentSemesterGrades.CurrentSemesterGradesFragment;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.FinalExamsTable.ExamTableTimeFragment;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.ReviewRegisteredSubjects.ReviewRegisteredSubjectsFragment;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.StudentGrades.StudentGradesFragment;
import rofaeil.ashaiaa.idea.collegelife.MainFragments.SubjectsRegistration.SubjectsRegistrationFragment;


public class FragmentAdapter extends FragmentStatePagerAdapter {

    private Fragment[] mFragments = {
            new CurrentSemesterGradesFragment(),
            new StudentGradesFragment(),
            new ReviewRegisteredSubjectsFragment(),
            new ExamTableTimeFragment(),
            new SubjectsRegistrationFragment(),
            new AddRemoveSubjectsFragment()
    };

    private String[] mTitle = {
            "Grades",
            "Semester Grades",
            "Review Subjects",
            "Exams Table",
            "Registration Subjects",
            "Add & Remove Subjects"
    };

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

}
