package rofaeil.ashaiaa.idea.collegelife.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Map;

import rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangePassword.ChangePasswordFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.ChangeSpecialization.ChangeSpecializationFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.GraduationSheet.GraduationSheetFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.MyProfile.MyProfileFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.RegisterSpecialDegree.RegisterSpecialDegreeFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.RegisterSpecialization.RegisterSpecializationFragment;
import rofaeil.ashaiaa.idea.collegelife.MenuFragments.ResultChangeSpecialization.ResultChangeSpecializationFragment;
import rofaeil.ashaiaa.idea.collegelife.R;
import rofaeil.ashaiaa.idea.collegelife.Utils.Student;
import rofaeil.ashaiaa.idea.collegelife.databinding.MenuActivityBinding;

public class MenuActivity extends AppCompatActivity {

    public static Student student;
    public static boolean finished_loading_student_data = false;
    public static Map<String, String> mapLoginPageCookies;
    DrawerLayout mDrawerLayout;
    MenuActivityBinding menuActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuActivityBinding = DataBindingUtil.setContentView(this, R.layout.menu_activity);

        initializeToolbar();
        initializeSlidingPaneLayout();

        String selected_item = getIntent().getStringExtra("selected_item");
        if (selected_item != null) {
            replace_selected_fragment(selected_item);
        }
    }

    public void initializeToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String selected_item = getIntent().getStringExtra("selected_item");
        switch (selected_item) {
            case "my_profile":
                getSupportActionBar().setTitle("My Profile");
                break;

            case "graduation_document":
                getSupportActionBar().setTitle("Graduation Document");
                break;

            case "register_specialization":
                getSupportActionBar().setTitle("Register Specialization");
                break;

            case "register_special_degree":
                getSupportActionBar().setTitle("Register Special Degree");
                break;

            case "change_specialization":
                getSupportActionBar().setTitle("Change Specialization");
                break;

            case "result_change_specialization":
                getSupportActionBar().setTitle("Result Change Specialization");
                break;

            case "change_password":
                getSupportActionBar().setTitle("Change Password");
                break;

            case "about_app":
                getSupportActionBar().setTitle("About App");
                break;

            case "contact_us":
                getSupportActionBar().setTitle("Contact Us");
                break;
            default:


        }

    }

    public void initializeSlidingPaneLayout() {
        SlidingPaneLayout mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.menu_activity_sliding_pane_layout);
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

    private void replace_selected_fragment(String selected_item) {

        switch (selected_item) {

            //Replacing the main content with ContentFragment Which is our Inbox View;
            case "my_profile":
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, myProfileFragment).commit();
                break;

            case "graduation_document":
                GraduationSheetFragment graduationSheetFragment = new GraduationSheetFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, graduationSheetFragment).commit();
                break;

            case "register_specialization":
                RegisterSpecializationFragment registerSpecializationFragment = new RegisterSpecializationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, registerSpecializationFragment).commit();
                break;

            case "register_special_degree":
                RegisterSpecialDegreeFragment registerSpecialDegreeFragment = new RegisterSpecialDegreeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, registerSpecialDegreeFragment).commit();
                break;

            case "change_specialization":
                ChangeSpecializationFragment changeSpecializationFragment = new ChangeSpecializationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, changeSpecializationFragment).commit();
                break;

            case "result_change_specialization":
                ResultChangeSpecializationFragment resultChangeSpecializationFragment = new ResultChangeSpecializationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, resultChangeSpecializationFragment).commit();
                break;

            case "change_password":
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, changePasswordFragment).commit();
                break;

            case "about_app":
//                MyProfileFragment fragment = new MyProfileFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, fragment).commit();
                break;

            case "contact_us":
//                MyProfileFragment fragment = new MyProfileFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, fragment).commit();
                break;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();

        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        //Checking if the item is in checked state or not, if not make it in checked state
//        if (menuItem.isChecked()) menuItem.setChecked(false);
//        else menuItem.setChecked(true);
//        //Closing menu_drawer on item click
//        mDrawerLayout.closeDrawers();
//
//        //Check to see which item was being clicked and perform appropriate action
//        switch (menuItem.getItemId()) {
//
//            case R.id.home_page:
//                Intent intent = new Intent(this,MainActivity.class) ;
//                startActivity(intent);
//                return true;
//
//
//            case R.id.graduation_document:
//                replace_selected_fragment("graduation_document");
//                return true;
//
//            case R.id.register_specialization:
//                replace_selected_fragment("register_specialization");
//                return true;
//
//            case R.id.register_special_degree:
//                replace_selected_fragment("register_special_degree");
//
//                return true;
//            case R.id.change_specialization:
//                replace_selected_fragment("change_specialization");
//                return true;
//
//            case R.id.result_change_specialization:
//                replace_selected_fragment("result_change_specialization");
//                return true;
//
//            case R.id.change_password:
//                replace_selected_fragment("change_password");
//                return true;
//
//            case R.id.about_app:
//                replace_selected_fragment("about_app");
//                return true;
//
//            case R.id.contact_us:
//                replace_selected_fragment("contact_us");
//                return true;
//
//            default:
//                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
//                return true;
//
//        }
//
//    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

//    private void start_AsyncTask_log_in() {
//        AsyncTaskLogin asyncTaskLogin = new AsyncTaskLogin() {
//            @Override
//            protected void onPostExecute(Document document_review_subject) {
//                student = extract_student_data_from_document(document_review_subject);
//                finished_loading_student_data = true ;
//                set_drawer_data();
//            }
//        };
//        asyncTaskLogin.execute();
//    }

//    private void set_drawer_data() {
//        TextView temp;
//        temp = (TextView) menuActivityBinding.navigationView.findViewById(R.id.person_name);
//        temp.setText(student.getFull_name());
//        temp = (TextView) menuActivityBinding.navigationView.findViewById(R.id.student_id_drawer);
//        temp.setText(student.getId());
//        temp = (TextView) menuActivityBinding.navigationView.findViewById(R.id.student_departments_drawer);
//        temp.setText(student.getMajor()+"/"+student.getMinor());
//    }

//    public void myProfileLayoutPressed(View view) {
//
//        replace_selected_fragment("my_profile");
//        mDrawerLayout.closeDrawers();
//    }

}
