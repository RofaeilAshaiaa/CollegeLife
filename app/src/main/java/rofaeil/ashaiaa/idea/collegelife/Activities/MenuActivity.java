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

import rofaeil.ashaiaa.idea.collegelife.MenuFragments.AboutUs.AboutUsFragment;
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
        String selected_item = getIntent().getStringExtra("selected_item");

        initializeToolbar(selected_item);
        initializeSlidingPaneLayout();

        if (selected_item != null) {
            replace_selected_fragment(selected_item);
        }
    }

    public void initializeToolbar(String selected_item) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch (selected_item) {

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
                AboutUsFragment fragment = new AboutUsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menu_activity_fragment_container, fragment).commit();
                break;

            case "contact_us":
//                MyProfileFragment fragment = new MyProfileFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity_fragment_container, fragment).commit();
                break;

            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();

        }
    }

}
