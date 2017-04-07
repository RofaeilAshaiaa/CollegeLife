package rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.databinding.ChangePasswordFragmentBinding;

import static android.content.Context.MODE_PRIVATE;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.CHANGE_PASSWORD_LOADER_ID;


public class ChangePasswordFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document>, View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private String mSavedId;
    private String mSavedPassword;
    private ChangePasswordFragmentBinding mBinding;
    private ProgressDialog mProgressDialog;
    private FragmentActivity mContext;
    private String mNewPassword;
    private String mCurrentPassword;
    private String mConfirmNewPassword;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.change_password_fragment, container, false);

        getStudentData();

        mBinding.loggedPerson.setText(mSavedId);
        mBinding.changePasswordButton.setOnClickListener(this);

        return mBinding.getRoot();
    }

    public void getStudentData() {
        mSharedPreferences = getActivity().getSharedPreferences("log_in", MODE_PRIVATE);
        mSavedId = mSharedPreferences.getString("ID", null);
        mSavedPassword = mSharedPreferences.getString("PASSWORD", null);
    }

    public void initializeLoader() {
        LoaderManager loaderManager = mContext.getSupportLoaderManager();
        Loader<Document> loader = loaderManager.getLoader(103);
        if (loader == null) {
            loaderManager.initLoader(CHANGE_PASSWORD_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(CHANGE_PASSWORD_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Changing Password Please Wait");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public boolean isChangePasswordFormEmpty() {
        if (mBinding.currentPassword.getText().toString().isEmpty()) {
            mBinding.currentPasswordTextInputLayout.setError("ادخل كلمة المرور الحالية");
            return true;
        } else if (mBinding.newPassword.getText().toString().isEmpty()) {
            mBinding.newPasswordTextInputLayout.setError("ادخل كلمة المرور الجديدة");
            return true;
        } else if (mBinding.confirmNewPassword.getText().toString().isEmpty()) {
            mBinding.confirmNewPasswordTextInputLayout.setError("ادخل كلمة المرور الجديدة مرة أخرى");
            return true;
        }
        return false;
    }

    public void getChangePasswordFormData() {
        mCurrentPassword = mBinding.currentPassword.getText().toString();
        mNewPassword = mBinding.newPassword.getText().toString();
        mConfirmNewPassword = mBinding.confirmNewPassword.getText().toString();
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderChangePassword(mContext, mCurrentPassword, mNewPassword, mConfirmNewPassword);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        if (data != null) {
            Document document = data;
            Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_MSGLabel");
            String confirmMessage = target_table.text();
            if (confirmMessage.equals("تم تغيير كلمة المرور بنجاح")) {
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                mEditor.putString("PASSWORD", mNewPassword);
                mEditor.commit();
                mSavedPassword = mNewPassword;
                Snackbar.make(mBinding.getRoot(),"Password Changed Successfully",Snackbar.LENGTH_LONG).show();
            }
            mProgressDialog.dismiss();
        } else {
            Snackbar.make(mBinding.getRoot(),"Some thing went wrong, try again !",Snackbar.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

    @Override
    public void onClick(View v) {
        if (!isChangePasswordFormEmpty()) {
            initializeProgressDialog();
            if (mSavedId != null && mSavedPassword != null) {
                getChangePasswordFormData();
                if (mCurrentPassword.equals(mSavedPassword)) {
                    if (mNewPassword.equals(mConfirmNewPassword)) {
                        initializeLoader();
                    } else {
                        Snackbar.make(mBinding.getRoot(),"New Password doesn't Match",Snackbar.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Snackbar.make(mBinding.getRoot(),"Current Password incorrect",Snackbar.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            } else {
                Snackbar.make(mBinding.getRoot(),"Some thing went wrong, try again !",Snackbar.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }
    }
}
