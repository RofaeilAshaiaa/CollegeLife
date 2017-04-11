package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

import rofaeil.ashaiaa.idea.collegelife.Beans.Student.StudentHome;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.MyProfileActivity;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.AsyncTaskLogin;
import rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods;

import static rofaeil.ashaiaa.idea.collegelife.Utils.FinalData.REQUEST_HEADER_DATA;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.getStudentLevel;
import static rofaeil.ashaiaa.idea.collegelife.Utils.StaticMethods.isNetworkAvailable;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String student_id;
    public static String student_pass;
    public static Document studentHomeDocument;
    public static Map<String, String> mapLoginPageCookies;
    public StudentHome mStudentHome;
    public ViewPager mViewPager;
    public Toolbar mToolbar;
    public SharedPreferences mSharedPreferencesLogIn;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getStudentPasswordAndID();
        //  setRequestHeaderDate();

        if (mapLoginPageCookies == null) {
            if (isNetworkAvailable(this)) {
                startAsyncTaskLogIn();
            }
        } else {
            mStudentHome = StaticMethods.getStudentHome(studentHomeDocument);
        }


        initializeToolbar();
        initializeNavigationDrawer();
        initializeViewPager();
        initializeTabs();
        initializeSlidingPaneLayout();
    }

    public void getStudentPasswordAndID() {
        mSharedPreferencesLogIn = getSharedPreferences("log_in", MODE_PRIVATE);
        mEditor = mSharedPreferencesLogIn.edit();
        student_id = mSharedPreferencesLogIn.getString("ID", null);
        student_pass = mSharedPreferencesLogIn.getString("PASSWORD", null);
    }

    public void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initializeNavigationDrawer() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle
                (this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    public void initializeViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentAdapter mFragment_adapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragment_adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 5) {
                    LinearLayout right_menu_body_root = (LinearLayout) findViewById(R.id.main_right_menu_body_root);
                    right_menu_body_root.setLayoutParams(new SlidingPaneLayout.LayoutParams(0, SlidingPaneLayout.LayoutParams.MATCH_PARENT));
                }
                if (position == 4) {
                    LinearLayout right_menu_body_root = (LinearLayout) findViewById(R.id.main_right_menu_body_root);
                    right_menu_body_root.setLayoutParams(new SlidingPaneLayout.LayoutParams(0, SlidingPaneLayout.LayoutParams.MATCH_PARENT));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initializeTabs() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void initializeSlidingPaneLayout() {
        SlidingPaneLayout mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.main_sliding_pane_layout);
        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
        mSlidingPaneLayout.setParallaxDistance(100);
    }

    public void setRequestHeaderDate() {
        REQUEST_HEADER_DATA.setVIEWSTATE(mSharedPreferencesLogIn.getString("VIEWSTATE", "0Lyyj9NPpdnddB6pakBAUYzptMJIMQbGYxPeA/ofZp6ZRiMMa7ZXUs3dz8ajVg6FEPGtqiTQe1hNxUcYxIL3/Li8FJVsm4jOKaOaqBNsf4l0D9snUwUKx/0A/H2L5zmenH7qD/I9ArVUk7bDsmHpPMBATFzydB2f2o6Zc6lj9/OOkf2zOVtxlnSrg/W+E4TPk/BVrC0R3CUl0BpGkLbLIVrxiBe1aXoAm9eYh12lspe+UHHlwSFSuGvxFNQ3tLHtnZDYFi0dzvNSsf9WV98z4Q=="));
        REQUEST_HEADER_DATA.setVIEWSTATEGENERATOR(mSharedPreferencesLogIn.getString("VIEWSTATEGENERATOR", "F7FE45A7"));
        REQUEST_HEADER_DATA.setEVENTVALIDATION(mSharedPreferencesLogIn.getString("EVENTVALIDATION", "nwol/vBRfDWTLde1S4/NC/4tBd9SEGC7zvP3WfnQ77DFLvolKZwlPTlw0HlhUf5QM0joff9DV0832m4YrbEQjdWymAOxW4RjfV9kAjLNweWKaYeGhhF16zav9o52H7gB93gHxRdzYQSGVdwL5tMSCcGGOc7X/9xd7s7aZcPTgbUC5NKk9iMjkPw9BHtosdmg"));
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
        mEditor.commit();

        REQUEST_HEADER_DATA.setVIEWSTATE(ViewState);
        REQUEST_HEADER_DATA.setVIEWSTATEGENERATOR(ViewStateGenerator);
        REQUEST_HEADER_DATA.setEVENTVALIDATION(EventValidation);
    }

    private void startAsyncTaskLogIn() {

        AsyncTaskLogin asyncTaskLogin = new AsyncTaskLogin(student_id, student_pass, getBaseContext()) {
            @Override
            protected void onPostExecute(Connection.Response response) {
                try {
                    Document mDocument = response.parse();
                    if (mDocument != null) {
                        mStudentHome = StaticMethods.getStudentHome(mDocument);
                        setNavigationDrawerHeaderData();
                        mapLoginPageCookies = response.cookies();
                        studentHomeDocument = mDocument;
                        //      getRequestHeaderDate(mDocument);
                    } else {
                        StaticMethods.showToast(getBaseContext(), "Website not Responding");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        asyncTaskLogin.execute();
    }

    private void setNavigationDrawerHeaderData() {
        TextView mStudentNameLogo = (TextView) findViewById(R.id.navigation_menu_header_name_logo);
        TextView mStudentName = (TextView) findViewById(R.id.student_my_profile_header_name_text);
        TextView mStudentLevel = (TextView) findViewById(R.id.navigation_menu_header_level_val);
        TextView mStudentID = (TextView) findViewById(R.id.navigation_menu_header_id);
        TextView mStudentMajor = (TextView) findViewById(R.id.navigation_menu_header_major);

        mStudentID.setText(mStudentHome.getID());
        mStudentMajor.setText(mStudentHome.getMajor());
        mStudentLevel.setText("" + getStudentLevel(mStudentHome.getLevel()) + "");
        ProgressBar mStudentCGPAProgressBar = (ProgressBar) findViewById(R.id.navigation_menu_header_cgpa_progress_bar);
        mStudentCGPAProgressBar.setProgress((int) Double.parseDouble(mStudentHome.getCGPA()));
        ProgressBar mStudentEarnedHoursProgressBar = (ProgressBar) findViewById(R.id.navigation_menu_header_earned_hours_progress_bar);
        mStudentEarnedHoursProgressBar.setProgress(Integer.parseInt(mStudentHome.getEarnedHours()));

        if (mSharedPreferencesLogIn.getString("FirstNameEN", null) == null) {
            mStudentName.setText(mStudentHome.getFullName());
            mStudentNameLogo.setText("" + mStudentHome.getFullName().charAt(0) + "");
        } else {
            mStudentName.setText(mSharedPreferencesLogIn.getString("FirstNameEN", null) + " " + mSharedPreferencesLogIn.getString("SecondNameEN", null));
            mStudentNameLogo.setText("" + Character.toUpperCase(mSharedPreferencesLogIn.getString("FirstNameEN", null).charAt(0)) + "");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Intent intent = new Intent(this, MenuActivity.class);

        switch (menuItem.getItemId()) {

            case R.id.sign_out:

                mEditor.putString("ID", null);
                mEditor.putString("PASSWORD", null);
                mEditor.putString("FirstNameEN", null);
                mEditor.putString("SecondNameEN", null);
                mEditor.commit();
                Intent mIntent = new Intent(this, LogInActivity.class);
                startActivity(mIntent);
                finish();
                return true;


            case R.id.graduation_document:
                intent.putExtra("selected_item", "graduation_document");
                startActivity(intent);
                return true;

//            case R.id.register_specialization:
//                intent.putExtra("selected_item", "register_specialization");
//                startActivity(intent);
//                return true;
//
//            case R.id.register_special_degree:
//                intent.putExtra("selected_item", "register_special_degree");
//                startActivity(intent);
//                return true;
//
//            case R.id.change_specialization:
//                intent.putExtra("selected_item", "change_specialization");
//                startActivity(intent);
//                return true;
//
//            case R.id.result_change_specialization:
//                intent.putExtra("selected_item", "result_change_specialization");
//                startActivity(intent);
//                return true;

            case R.id.change_password:
                intent.putExtra("selected_item", "change_password");
                startActivity(intent);
                return true;

            case R.id.about_app:
                intent.putExtra("selected_item", "about_app");
                startActivity(intent);
                return true;

            case R.id.contact_us:
                intent.putExtra("selected_item", "contact_us");
                startActivity(intent);
                return true;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;

        }

    }

    public void navigationHeaderPressed(View view) {

        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);

    }


}
