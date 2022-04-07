package com.example.qrhunt1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.qrhunt1.ui.Login.SignupFragment;
import com.example.qrhunt1.ui.gallery.GalleryFragment;
import com.example.qrhunt1.ui.profile.MyProfileFragment;
import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest{
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule
            = new ActivityTestRule<>(MainActivity.class,true,true);

    /**
     * Runs before all tests and creates solo inst ance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkSignUp(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Create Here");
        solo.assertCurrentActivity("Wrong Activity",Sign_up.class);
        solo.enterText((EditText) solo.getView(R.id.input_su_username), "alcarrier");
        solo.enterText((EditText) solo.getView(R.id.input_su_password), "123456");
        solo.enterText((EditText) solo.getView(R.id.input_con_password), "123456");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

    }

    /**
     * When user creates the same username as the other's then pop up the exception.
     */
    @Test
    public void checkSignUpException(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Create Here");
        solo.assertCurrentActivity("Wrong Activity",Sign_up.class);
        solo.enterText((EditText) solo.getView(R.id.input_su_username), "alcarrier");
        solo.enterText((EditText) solo.getView(R.id.input_su_password), "123456");
        solo.enterText((EditText) solo.getView(R.id.input_con_password), "123456");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity",Sign_up.class);
    }

    /**
     * After user creates the same username as the other's then recreates a new one. Then sign in.
     */
    @Test
    public void checkSignUpThenLogin(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Create Here");
        solo.assertCurrentActivity("Wrong Activity",Sign_up.class);
        solo.enterText((EditText) solo.getView(R.id.input_su_username), "alcarrier1");
        solo.enterText((EditText) solo.getView(R.id.input_su_password), "123456");
        solo.enterText((EditText) solo.getView(R.id.input_con_password), "123456");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.input_username));
        solo.clearEditText((EditText) solo.getView(R.id.input_password));
        solo.enterText((EditText) solo.getView(R.id.input_username), "alcarrier1");
        solo.enterText((EditText) solo.getView(R.id.input_password), "123456");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
    }


    @Test
    public void RememberMe() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.input_username));
        solo.clearEditText((EditText) solo.getView(R.id.input_password));
        solo.enterText((EditText) solo.getView(R.id.input_username), "alcarrier1");
        solo.enterText((EditText) solo.getView(R.id.input_password), "123456");
            solo.clickOnCheckBox(0);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void CheckRememberMe(){
        solo.clickOnCheckBox(0);

        solo.clearEditText((EditText) solo.getView(R.id.input_username));
        solo.clearEditText((EditText) solo.getView(R.id.input_password));
        solo.enterText((EditText) solo.getView(R.id.input_username), "alcarrier1");
        solo.enterText((EditText) solo.getView(R.id.input_password), "123456");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    @Test
    public void CheckRememberMeAgain() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }



    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
