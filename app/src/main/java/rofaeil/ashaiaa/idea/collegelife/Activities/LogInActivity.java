package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.AsyncTaskLoaderLogin;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;
import rofaeil.ashaiaa.idea.collegelife.databinding.LogInActivityBinding;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.studentHomeDocument;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.LOGIN_LOADER_ID;
import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.REQUEST_HEADER_DATA;

public class LogInActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Connection.Response>, View.OnClickListener {


    private LogInActivityBinding mBinding;
    private SharedPreferences.Editor mEditor;
    private String mID;
    private String mPassword;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.log_in_activity);
        mBinding.login.setOnClickListener(this);
    }

    public boolean isLoginFormEmpty() {
        if (mBinding.idEditText.getText().toString().isEmpty()) {
            mBinding.idTextInputLayout.setError("Enter ID");
            return true;
        } else if (mBinding.passwordEditText.getText().toString().isEmpty()) {
            mBinding.idTextInputLayout.setErrorEnabled(false);
            mBinding.passwordTextInputLayout.setError("Enter Password");
            return true;
        }
        return false;
    }

    public void initializeLoader() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Connection.Response> loader = loaderManager.getLoader(LOGIN_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(LOGIN_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(LOGIN_LOADER_ID, null, this).forceLoad();
        }
    }

    public void initializeSharedPreferences() {
        SharedPreferences mSharedPreferences = getSharedPreferences("log_in", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void removeFormErrorMassages() {
        mBinding.idTextInputLayout.setErrorEnabled(false);
        mBinding.passwordTextInputLayout.setErrorEnabled(false);
    }

    public void getFormData() {
        mID = mBinding.idEditText.getText().toString();
        mPassword = mBinding.passwordEditText.getText().toString();
    }

    public void getRequestHeaderDate(Document document) {
        Element VIEWSTATE = document.getElementById("__VIEWSTATE");
        Element VIEWSTATEGENERATOR = document.getElementById("__VIEWSTATEGENERATOR");
        Element EVENTVALIDATION = document.getElementById("__EVENTVALIDATION");

        String ViewState = VIEWSTATE.val();
        String ViewStateGenerator = VIEWSTATEGENERATOR.val();
        String EventValidation = EVENTVALIDATION.val();

        mEditor.putString("VIEWSTATE", ViewState);
        mEditor.putString("VIEWSTATEGENERATOR", ViewStateGenerator);
        mEditor.putString("EVENTVALIDATION", EventValidation);

        REQUEST_HEADER_DATA.setVIEWSTATE(ViewState);
        REQUEST_HEADER_DATA.setVIEWSTATEGENERATOR(ViewStateGenerator);
        REQUEST_HEADER_DATA.setEVENTVALIDATION(EventValidation);
    }

    @Override
    public Loader<Connection.Response> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderLogin(this, mPassword, mID);
    }

    @Override
    public void onLoadFinished(Loader<Connection.Response> loader, Connection.Response data) {
        try {
            Document mDocument = data.parse();
            if (mDocument.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView") != null) {
                mapLoginPageCookies = data.cookies();
                studentHomeDocument = mDocument;
                mEditor.putString("ID", mID);
                mEditor.putString("PASSWORD", mPassword);
                getRequestHeaderDate(mDocument);
                mEditor.commit();
                mProgressBar.dismiss();
                Intent mIntent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            } else {
                Snackbar.make(mBinding.getRoot(), "Wrong Password or ID", Snackbar.LENGTH_LONG).show();
                mProgressBar.dismiss();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<Connection.Response> loader) {

    }

    @Override
    public void onClick(View v) {
        initializeSharedPreferences();
        if (!isLoginFormEmpty()) {
            removeFormErrorMassages();
            getFormData();
            mProgressBar = new ProgressDialog(this);
            StaticMethods.showProgressBarSpinner(mProgressBar, "LOG IN");
            initializeLoader();
        }
    }
}
