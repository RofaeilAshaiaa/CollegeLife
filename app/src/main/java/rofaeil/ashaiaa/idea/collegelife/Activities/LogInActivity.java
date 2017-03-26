package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.AsyncTaskLogin;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;

import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;
import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.studentHomeDocument;

public class LogInActivity extends AppCompatActivity {


    public SharedPreferences.Editor mEditor;
    EditText mID;
    EditText mPassword;
    ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
    }

    public void login(View view) {
        TextInputLayout mIDTextInputLayout = (TextInputLayout) findViewById(R.id.id_text_input_layout);
        TextInputLayout mPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.password_text_input_layout);
        mID = (EditText) findViewById(R.id.id_edit_text);
        mPassword = (EditText) findViewById(R.id.password_edit_text);
        SharedPreferences mSharedPreferences = getSharedPreferences("log_in", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        if (mID.getText().toString().isEmpty()) {
            mIDTextInputLayout.setError("Enter ID");
        } else if (mPassword.getText().toString().isEmpty()) {
            mPasswordTextInputLayout.setError("Enter Password");
        } else {
            mProgressBar = new ProgressDialog(this);
            StaticMethods.showProgressBarSpinner(mProgressBar, "LOG IN");
            AsyncTaskLogin asyncTaskLogin = new AsyncTaskLogin(mID.getText().toString(), mPassword.getText().toString(), getBaseContext()) {
                @Override
                protected void onPostExecute(Connection.Response response) {
                    try {
                        Document mDocument = response.parse();
                        if (mDocument.getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_StMainDataFormView") != null) {
                            mEditor.putString("ID", mID.getText().toString());
                            mEditor.putString("PASSWORD", mPassword.getText().toString());
                            mEditor.commit();
                            mapLoginPageCookies = response.cookies();
                            studentHomeDocument = mDocument;
                            mProgressBar.dismiss();
                            Intent mIntent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(mIntent);
                        } else {
                            Toast.makeText(LogInActivity.this, "wrong password", Toast.LENGTH_LONG).show();
                            mProgressBar.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            asyncTaskLogin.execute();
        }

    }
}
