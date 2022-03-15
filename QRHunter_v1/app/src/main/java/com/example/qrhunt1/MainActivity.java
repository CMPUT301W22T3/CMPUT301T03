package com.example.qrhunt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qrhunt1.ui.Login.CallbackFragment;
import com.example.qrhunt1.ui.Login.LoginFragment;
import com.example.qrhunt1.ui.Login.SignupFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qrhunt1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {// implements CallbackFragment {
<<<<<<< HEAD
=======

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private ActivityMainBinding binding;
>>>>>>> 3db3f4dbfce39ea1e828e149541f3898f3e05016

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

<<<<<<< HEAD
        loginTest();
    }

    public void loginTest() {
=======
        //addLoginFragment();
        loginTest();


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_gallery, R.id.navigation_map, R.id.navigation_players)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void loginTest() {
        Button loginButton = findViewById(R.id.login_btn);
        TextView createButton = findViewById(R.id.createnew);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NaviTest.class);
                startActivity(intent);


            }

        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);

            }
        });
>>>>>>> 3db3f4dbfce39ea1e828e149541f3898f3e05016

        // Todo - Try to block the MainActivity Once Login Successful
        // Todo - In Create Account Activity back to MainActivity is possible.
        // Todo - Click On Login Button, Compare With DataBase. Fail: Re prompt Users

        Button loginButton = findViewById(R.id.login_btn);
        TextView create = findViewById(R.id.createnew);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NaviTest.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sign_up.class);
                startActivity(intent);
            }
        });
    }
<<<<<<< HEAD
=======





//    public void addLoginFragment(){
//        Fragment fragment = new LoginFragment();
//        ((LoginFragment) fragment).setCallbackFragment(this);
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
//        fragmentTransaction.commit();
//
//    }

//    public void replaceSignupFragment(){
//        Fragment fragment = new SignupFragment();
//        ((SignupFragment) fragment).setCallbackFragment(this);
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        //fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
//        fragmentTransaction.commit();
//
//    }
//
//    @Override
//    public void toSignupFragment() {
//        replaceSignupFragment();
//    }
//    @Override
//    public void toLoginFragment(){
//        addLoginFragment();
//    }



>>>>>>> 3db3f4dbfce39ea1e828e149541f3898f3e05016
}