package com.example.qrhunt1;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrhunt1.ui.Login.CallbackFragment;
import com.example.qrhunt1.ui.Login.LoginFragment;
import com.example.qrhunt1.ui.Login.SignupFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    ImageView scan;
    EditText loginUsername;
    EditText loginPassword;
    CheckBox remember;
    TextView create;
    Button login;
    String string = "@gmail.com";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},101);

        scan = findViewById(R.id.iv_login_scan);
        loginUsername = findViewById(R.id.input_username);
        loginPassword = findViewById(R.id.input_password);
        remember = findViewById(R.id.rememberme);
        create = findViewById(R.id.createnew);
        login = findViewById(R.id.login_btn);
//        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//        preferences.getString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();


                //Username Conditions
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(MainActivity.this, "Username can not be Empty!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not be Empty!");
                    return;
                }
                if (username.indexOf(" ") != -1){
                    Toast.makeText(MainActivity.this, "Username can not contain space, @, and .!!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not contain space!");
                    return;
                }else if(username.indexOf("@") != -1){
                    Toast.makeText(MainActivity.this, "Username can not contain @!!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not contain @!");
                    return;
                }else if(username.indexOf(".") != -1){
                    Toast.makeText(MainActivity.this, "Username can not contain .!!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not contain .!");
                    return;
                } else {
                    email = username.concat(string);
                    //username = username + string;
                }

                //Password Conditions
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Password can not be Empty!", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Password can not be Empty!");
                    return;
                } else if(password.length() < 6){
                    Toast.makeText(MainActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Password must be at least 6 characters!");
                    return;
                }

                // authenticate the user
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), NaviTest.class));
                            finish();

                        }else{
                            Toast.makeText(MainActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked()){
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("remember", "ture");
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Remembered the Username and Password!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sign_up.class);
                startActivity(intent);
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "ture");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Remembered the Username and Password!", Toast.LENGTH_SHORT).show();


                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mode = "login";
                Intent intent = new Intent(MainActivity.this, Scan.class);
                intent.putExtra("mode",mode);
                startActivity(intent);
                finish();
            }
        });
    }
}


