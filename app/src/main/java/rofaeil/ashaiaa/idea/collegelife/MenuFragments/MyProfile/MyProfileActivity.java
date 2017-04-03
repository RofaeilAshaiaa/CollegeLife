package rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import rofaeil.ashaiaa.idea.collegelife.R;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_activity);

        initializeToolbar();
    }

    public void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_profile_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
