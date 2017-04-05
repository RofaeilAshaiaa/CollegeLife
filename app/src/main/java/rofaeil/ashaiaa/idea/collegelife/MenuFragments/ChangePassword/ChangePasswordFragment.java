package rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.FinalData;
import rofaeil.ashaiaa.idea.collegelife.databinding.ChangePasswordFragmentBinding;

import static android.content.Context.MODE_PRIVATE;
import static rofaeil.ashaiaa.idea.collegelife.Activities.MainActivity.mapLoginPageCookies;


public class ChangePasswordFragment extends Fragment implements LoaderManager.LoaderCallbacks<Document> {

    private SharedPreferences mSharedPreferences;
    private String mSavedId;
    private String mSavedPassword;
    private ChangePasswordFragmentBinding mBinding;
    private ProgressDialog mProgressDialog;
    private FragmentActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.change_password_fragment, container, false);

        setOnClickListenerButton();
        getStudentData();
        mBinding.loggedPerson.setText(mSavedId);

        return mBinding.getRoot();
    }

    public void getStudentData(){
        mSharedPreferences = getActivity().getSharedPreferences("log_in", MODE_PRIVATE);
        mSavedId = mSharedPreferences.getString("ID", null);
        mSavedPassword = mSharedPreferences.getString("PASSWORD", null);
    }

    public void initializeLoader(){
        LoaderManager loaderManager = mContext.getSupportLoaderManager();
        Loader<Document> loader = loaderManager.getLoader(103);
        if (loader == null){
            loaderManager.initLoader(103,null,this).forceLoad();
        }else {
            loaderManager.restartLoader(103,null,this).forceLoad();
        }
    }

    private void setOnClickListenerButton() {
        mBinding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Changing Password Please Wait");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();


                final SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("log_in", MODE_PRIVATE);
                String saved_id = mSharedPreferences.getString("ID", null);
                String saved_password = mSharedPreferences.getString("PASSWORD", null);

                if (saved_id != null && saved_password != null) {
                    final String currentPassword = mBinding.currentPassword.getText().toString();
                    final String newPassword = mBinding.newPassword.getText().toString();
                    final String confirmNewPassword = mBinding.confirmNewPassword.getText().toString();

                    if (currentPassword.matches(saved_password) == true) {

                        if (newPassword.matches(confirmNewPassword) == true) {

                            AsyncTaskLoader loader = new AsyncTaskLoader(getActivity()) {
                                @Override
                                public Object loadInBackground() {

                                    try {

                                        Connection.Response response =
                                                Jsoup.connect(FinalData.ChangePasswordURL)
                                                        .userAgent("Mozilla/5.0")
                                                        .timeout(10 * 1000)
                                                        .followRedirects(true)
                                                        .method(Connection.Method.POST)
                                                        .cookies(mapLoginPageCookies)
                                                        .data("__EVENTTARGET", "")
                                                        .data("__EVENTARGUMENT", "")
                                                        .data("__VIEWSTATE", getString(R.string.__VIEWSTATE))
                                                        .data("__VIEWSTATEGENERATOR", getString(R.string.__VIEWSTATEGENERATOR))
                                                        .data("__VIEWSTATEENCRYPTED", "")
                                                        .data("__EVENTVALIDATION", getString(R.string.__EVENTVALIDATION))
                                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$CurrentPasswordTextBox", currentPassword)
                                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$NewPasswordTextBox", newPassword)
                                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ConfirmPasswordTextBox", confirmNewPassword)
                                                        .data("ctl00$ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ContentPlaceHolder1$ChngeButton", "تغيير")
                                                        .execute();

                                        return response.parse();

                                    } catch (IOException e) {
                                        e.printStackTrace();

                                    }
                                    return null;
                                }

                                @Override
                                public void deliverResult(Object data) {

                                    if (data != null) {

                                        Document document = (Document) data;
                                        Element target_table = document.body().getElementById("ContentPlaceHolder1_ContentPlaceHolder1_ContentPlaceHolder1_MSGLabel");
                                        String confirmMessage = target_table.text();
                                        if (confirmMessage.matches("تم تغيير كلمة المرور بنجاح") == true) {

                                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                                            mEditor.putString("PASSWORD", newPassword);
                                            mEditor.commit();
                                            Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                                        }


                                        mProgressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Some thing went wrong, try again !", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.dismiss();

                                    }

                                }
                            };

                            loader.forceLoad();

                        } else {
                            Toast.makeText(getActivity(), "New Password doesn't Match", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();

                        }
                    } else {
                        Toast.makeText(getActivity(), "Current Password incorrect", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();

                    }

                } else {
                    Toast.makeText(getActivity(), "Some thing went wrong, try again !", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();

                }


            }
        });
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {

    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }
}
