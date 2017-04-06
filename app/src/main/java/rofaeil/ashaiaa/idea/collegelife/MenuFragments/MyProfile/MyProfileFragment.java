package rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.nodes.Document;
import org.parceler.Parcels;

import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentData;
import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentHome;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.StudentData.AsyncTaskStudentDataGET;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.StudentData.StudentDataActivity;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.databinding.MyProfileFragmentBinding;

import static android.content.Context.MODE_PRIVATE;
import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.studentHomeDocument;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getStudentData;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getStudentHome;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getStudentLevel;

public class MyProfileFragment extends Fragment {


    private ProgressDialog mProgressDialog;
    private StudentData mStudentData;
    private MyProfileFragmentBinding myProfileFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myProfileFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.my_profile_fragment, container, false);

        initializeProgressDialog();
        StudentHome mStudentHome = new StudentHome();
        mStudentHome = getStudentHome(studentHomeDocument);
        insertStudentHomeInMyProfile(mStudentHome);

        AsyncTaskStudentDataGET asyncTaskStudentDataGET = new AsyncTaskStudentDataGET() {
            @Override
            protected void onPostExecute(Document document) {

                mStudentData = new StudentData();
                mStudentData = getStudentData(document);
                SharedPreferences mSharedPreferencesLogIn = getActivity().getSharedPreferences("log_in", MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharedPreferencesLogIn.edit();
                mEditor.putString("FirstNameEN", mStudentData.getFirstNameEN());
                mEditor.putString("SecondNameEN", mStudentData.getSecondNameEN());
                insertStudentDataInMyProfile(mStudentData);
                initializeFAB();
                mProgressDialog.dismiss();
            }
        };
        asyncTaskStudentDataGET.execute();

        return myProfileFragmentBinding.getRoot();
    }

    public void initializeProgressDialog(){
        mProgressDialog  = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading data");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }
    public void initializeFAB() {
        myProfileFragmentBinding.studentMyProfileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StudentDataActivity.class);
                Bundle mBundle = new Bundle();
                intent.putExtra("StudentData", Parcels.wrap(mStudentData));
                intent.putExtra("StudentID", mStudentData.getID());
                startActivity(intent);

            }
        });
    }

    public void insertStudentHomeInMyProfile(StudentHome StudentHome) {
        myProfileFragmentBinding.studentMyProfileNameArVal.setText(StudentHome.getFullName());
        myProfileFragmentBinding.studentMyProfileStudentIdNum.setText(StudentHome.getID());
        myProfileFragmentBinding.studentMyProfileBranchVal.setText(StudentHome.getBranch());
        myProfileFragmentBinding.studentMyProfileMajorVal.setText(StudentHome.getMajor());
        myProfileFragmentBinding.studentMyProfileMinorVal.setText(StudentHome.getMinor());
        myProfileFragmentBinding.studentMyProfileCgpaNum.setText(StudentHome.getCGPA());
        myProfileFragmentBinding.studentMyProfileEarnedHoursNum.setText(StudentHome.getEarnedHours());
        myProfileFragmentBinding.studentMyProfileCgpaProgressBar.setProgress((int) Double.parseDouble(StudentHome.getCGPA()));
        myProfileFragmentBinding.studentMyProfileEarnedHoursProgressBar.setProgress(Integer.parseInt(StudentHome.getEarnedHours()));
        myProfileFragmentBinding.studentMyProfileLevelNum.setText("" + getStudentLevel(StudentHome.getLevel()) + "");
        myProfileFragmentBinding.studentMyProfileLevelProgressBar.setProgress(getStudentLevel(StudentHome.getLevel()));
        myProfileFragmentBinding.studentMyProfileHeaderLevelVal.setText("" + getStudentLevel(StudentHome.getLevel()) + "");
        myProfileFragmentBinding.studentMyProfileHeaderEarnedHoursProgressBar.setProgress(Integer.parseInt(StudentHome.getEarnedHours()));
        myProfileFragmentBinding.studentMyProfileHeaderCgpaProgressBar.setProgress((int) Double.parseDouble(StudentHome.getCGPA()));
        myProfileFragmentBinding.studentMyProfileAcademicAdvisorVal.setText(StudentHome.getAcademicAdvisor());
    }

    public void insertStudentDataInMyProfile(StudentData studentData) {
        myProfileFragmentBinding.studentMyProfileNameEnVal.setText(
                studentData.getFirstNameEN() + " " +
                        studentData.getSecondNameEN() + " " +
                        studentData.getThirdNameEN() + " " +
                        studentData.getFourthNameEN());
        myProfileFragmentBinding.studentMyProfileNationalIdNum.setText(studentData.getNationalNumber());
        myProfileFragmentBinding.studentMyProfileAddressVal.setText(studentData.getAddress());
        myProfileFragmentBinding.studentMyProfileGenderVal.setText(studentData.getGender());
        myProfileFragmentBinding.studentMyProfileTelephoneNumber.setText(studentData.getTelephoneNumber());
        myProfileFragmentBinding.studentMyProfileMobileNumber.setText(studentData.getMobileNumber());
        myProfileFragmentBinding.studentMyProfileAcademicEmailVal.setText(studentData.getFacEmail());
        myProfileFragmentBinding.studentMyProfileOtherEmailVal.setText(studentData.getAlterEmail());
        myProfileFragmentBinding.studentMyProfileHeaderNameText.setText(studentData.getFirstNameEN() + " " + studentData.getSecondNameEN());
        myProfileFragmentBinding.studentMyProfileHeaderNameLogo.setText("" + Character.toUpperCase(studentData.getFirstNameEN().charAt(0)) + "");
    }


}
