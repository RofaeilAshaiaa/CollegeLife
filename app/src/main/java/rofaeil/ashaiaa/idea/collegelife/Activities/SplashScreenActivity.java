package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.TimerTask;

import rofaeil.ashaiaa.idea.collegelife.R;

public class SplashScreenActivity extends AppCompatActivity {
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        SharedPreferences mSharedPreferences = getSharedPreferences("log_in", MODE_PRIVATE);
        if (mSharedPreferences.getString("ID", null) == null || mSharedPreferences.getString("PASSWORD", null) == null) {
            mIntent = new Intent(this, LogInActivity.class);
            new Handler().postDelayed(new TimerTask() {
                @Override
                public void run() {
                    startActivity(mIntent);
                    finish();
                }
            }, 1000);
        } else {
            mIntent = new Intent(this, MainActivity.class);
            new Handler().postDelayed(new TimerTask() {
                @Override
                public void run() {
                    startActivity(mIntent);
                    finish();
                }
            }, 1000);
        }
    }
}
