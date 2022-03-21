package com.example.qrhunt1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_in extends AppCompatActivity {

    ImageView scan;
    EditText loginUsername;
    EditText loginPassword;
    CheckBox remember;
    TextView create;
    Button login;
    String string = "@gmail.com";
    String email;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        mAuth = FirebaseAuth.getInstance();

        scan = findViewById(R.id.iv_login_scan);
        loginUsername = findViewById(R.id.input_username);
        loginPassword = findViewById(R.id.input_password);
        remember = findViewById(R.id.rememberme);
        create = findViewById(R.id.createnew);
        login = findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();


                //Username Conditions
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(Log_in.this, "Username can not be Empty!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not be Empty!");
                    return;
                }
                if (username.indexOf(" ") != -1){
                    Toast.makeText(Log_in.this, "Username can not contain space!!", Toast.LENGTH_SHORT).show();
                    loginUsername.setError("Username can not contain space!");
                    //return;
                }else {
                    email = username.concat(string);
                    //username = username + string;
                }

                //Password Conditions
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Log_in.this, "Password can not be Empty!", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Password can not be Empty!");
                    //return;
                } else if(password.length() < 6){
                    Toast.makeText(Log_in.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                    loginPassword.setError("Password must be at least 6 characters!");
                }

                // authenticate the user
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Log_in.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), NaviTest.class));
                        }else{
                            Toast.makeText(Log_in.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_in.this, Sign_up.class);
                startActivity(intent);
            }
        });




    }
}