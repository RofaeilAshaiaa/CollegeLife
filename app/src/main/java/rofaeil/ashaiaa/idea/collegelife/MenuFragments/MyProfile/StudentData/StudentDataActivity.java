package rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.StudentData;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import org.parceler.Parcels;

import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentData;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.databinding.StudentDataActivityBinding;

public class StudentDataActivity extends AppCompatActivity implements View.OnClickListener {

    public StudentDataActivityBinding binding;
    StudentData mStudentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.student_data_activity);

        initializeToolbar();
        initializeSpinner();
        putDataInStudentDataForm();

        binding.studentDataSubmit.setOnClickListener(StudentDataActivity.this);
    }

    public void initializeToolbar() {
        setSupportActionBar(binding.studentDataActivityToolBar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void putDataInStudentDataForm() {

        StudentData mStudentData = Parcels.unwrap(getIntent().getExtras().getParcelable("StudentData"));

        binding.studentDataArFirstNameEditText.setText(mStudentData.getFirstNameAR());
        binding.studentDataArSecondNameEditText.setText(mStudentData.getSecondNameAR());
        binding.studentDataArThirdNameEditText.setText(mStudentData.getThirdNameAR());
        binding.studentDataArFourthNameEditText.setText(mStudentData.getFourthNameAR());
        binding.studentDataEnFirstNameEditText.setText(mStudentData.getFirstNameEN());
        binding.studentDataEnSecondNameEditText.setText(mStudentData.getSecondNameEN());
        binding.studentDataEnThirdNameEditText.setText(mStudentData.getThirdNameEN());
        binding.studentDataEnFourthNameEditText.setText(mStudentData.getFourthNameEN());
        binding.studentDataNationalIdEditText.setText(mStudentData.getNationalNumber());
        binding.studentDataTelephoneNumberEditText.setText(mStudentData.getTelephoneNumber());
        binding.studentDataMobileNumberEditText.setText(mStudentData.getMobileNumber());
        binding.studentDataAcademicEmailEditText.setText(mStudentData.getFacEmail());
        binding.studentDataOtherEmailEditText.setText(mStudentData.getAlterEmail());
        binding.studentDataAddressEditText.setText(mStudentData.getAddress());
        if (mStudentData.getGender().equals("ذكر")) {
            binding.studentDataGender.setSelection(0);
        } else if (mStudentData.getGender().equals("أنثي")) {
            binding.studentDataGender.setSelection(1);
        }
    }

    public void initializeSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.student_gender_array, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.studentDataGender.setAdapter(arrayAdapter);
    }

    public boolean isNameAREmpty() {
        if (binding.studentDataArFirstNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArFirstNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataArSecondNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArSecondNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataArThirdNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArThirdNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataArFourthNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArFourthNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else {
            return false;
        }
    }

    public boolean isNameENEmpty() {
        if (binding.studentDataEnFirstNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnFirstNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataEnSecondNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnSecondNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataEnThirdNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnThirdNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else if (binding.studentDataEnFourthNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnFourthNameTextInputLayout.setError("ادخل الاسم");
            return true;
        } else {
            return false;
        }
    }

    public boolean isStudentDataFrameEmpty() {
        if (isNameAREmpty()) {
            return true;
        } else if (isNameENEmpty()) {
            return true;
        } else if (binding.studentDataNationalIdEditText.getText().toString().isEmpty()) {
            binding.studentDataNationalIdTextInputLayout.setError("ادخل الرقم القومي");
            return true;
        } else if (binding.studentDataTelephoneNumberEditText.getText().toString().isEmpty()) {
            binding.studentDataTelephoneNumberTextInputLayout.setError("ادخل رقم التليفون");
            return true;
        } else if (binding.studentDataMobileNumberEditText.getText().toString().isEmpty()) {
            binding.studentDataMobileNumberTextInputLayout.setError("ادخل رقم الموبايل");
            return true;
        } else if (binding.studentDataOtherEmailEditText.getText().toString().isEmpty()) {
            binding.studentDataOtherEmailTextInputLayout.setError("ادخل الايميل");
            return true;
        } else if (binding.studentDataAddressEditText.getText().toString().isEmpty()) {
            binding.studentDataAddressTextInputLayout.setError("ادخل العنوان");
            return true;
        } else {
            return false;
        }
    }

    public void showErrorIfEmpty() {

        if (binding.studentDataArFirstNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArFirstNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataArFirstNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataArSecondNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArSecondNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataArSecondNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataArThirdNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArThirdNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataArThirdNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataArFourthNameEditText.getText().toString().isEmpty()) {
            binding.studentDataArFourthNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataArFourthNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataEnFirstNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnFirstNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataEnFirstNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataEnSecondNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnSecondNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataEnSecondNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataEnThirdNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnThirdNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataEnThirdNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataEnFourthNameEditText.getText().toString().isEmpty()) {
            binding.studentDataEnFourthNameTextInputLayout.setError("ادخل الاسم");
        } else {
            binding.studentDataEnFourthNameTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataNationalIdEditText.getText().toString().isEmpty()) {
            binding.studentDataNationalIdTextInputLayout.setError("ادخل الرقم القومي");
        } else {
            binding.studentDataNationalIdTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataTelephoneNumberEditText.getText().toString().isEmpty()) {
            binding.studentDataTelephoneNumberTextInputLayout.setError("ادخل رقم التليفون");
        } else {
            binding.studentDataTelephoneNumberTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataMobileNumberEditText.getText().toString().isEmpty()) {
            binding.studentDataMobileNumberTextInputLayout.setError("ادخل رقم الموبايل");
        } else {
            binding.studentDataMobileNumberTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataOtherEmailEditText.getText().toString().isEmpty()) {
            binding.studentDataOtherEmailTextInputLayout.setError("ادخل الايميل");
        } else {
            binding.studentDataOtherEmailTextInputLayout.setErrorEnabled(false);
        }
        if (binding.studentDataAddressEditText.getText().toString().isEmpty()) {
            binding.studentDataAddressTextInputLayout.setError("ادخل العنوان");
        } else {
            binding.studentDataAddressTextInputLayout.setErrorEnabled(false);
        }

    }

    @Override
    public void onClick(View v) {
        showErrorIfEmpty();
        boolean b = isStudentDataFrameEmpty();
        if (!isStudentDataFrameEmpty()) {
            mStudentData = new StudentData();
            mStudentData.setID(getIntent().getExtras().getString("StudentID"));
            mStudentData.setFirstNameAR(binding.studentDataArFirstNameEditText.getText().toString());
            mStudentData.setSecondNameAR(binding.studentDataArSecondNameEditText.getText().toString());
            mStudentData.setThirdNameAR(binding.studentDataArThirdNameEditText.getText().toString());
            mStudentData.setFourthNameAR(binding.studentDataArFourthNameEditText.getText().toString());
            mStudentData.setFirstNameEN(binding.studentDataEnFirstNameEditText.getText().toString());
            mStudentData.setSecondNameEN(binding.studentDataEnSecondNameEditText.getText().toString());
            mStudentData.setThirdNameEN(binding.studentDataEnThirdNameEditText.getText().toString());
            mStudentData.setFourthNameEN(binding.studentDataEnFourthNameEditText.getText().toString());
            mStudentData.setGender(binding.studentDataGender.getSelectedItem().toString());
            mStudentData.setNationalNumber(binding.studentDataNationalIdEditText.getText().toString());
            mStudentData.setTelephoneNumber(binding.studentDataTelephoneNumberEditText.getText().toString());
            mStudentData.setMobileNumber(binding.studentDataMobileNumberEditText.getText().toString());
            mStudentData.setFacEmail(binding.studentDataAcademicEmailEditText.getText().toString());
            mStudentData.setAlterEmail(binding.studentDataOtherEmailEditText.getText().toString());
            mStudentData.setAddress(binding.studentDataAddressEditText.getText().toString());

            AsyncTaskStudentDataPOST asyncTaskStudentDataPOST = new AsyncTaskStudentDataPOST(mStudentData);
            asyncTaskStudentDataPOST.execute();

        }
    }
}
