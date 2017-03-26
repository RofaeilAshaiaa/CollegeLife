package rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
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


public class ChangePasswordFragment extends Fragment {

    SharedPreferences mSharedPreferences;
    String savedId;
    String savedPassword;
    private ChangePasswordFragmentBinding binding;
    private ProgressDialog mProgressDialog;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.change_password_fragment, container, false);

        setOnClickListenerButton();
        mSharedPreferences = getActivity().getSharedPreferences("log_in", MODE_PRIVATE);
        savedId = mSharedPreferences.getString("ID", null);
        savedPassword = mSharedPreferences.getString("PASSWORD", null);
        binding.loggedPerson.setText(savedId);

        return binding.getRoot();
    }

    private void setOnClickListenerButton() {
        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
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
                    final String currentPassword = binding.currentPassword.getText().toString();
                    final String newPassword = binding.newPassword.getText().toString();
                    final String confirmNewPassword = binding.confirmNewPassword.getText().toString();

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

}
