package com.example.qrhunt1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up extends AppCompatActivity {

    EditText su_username;
    EditText su_password;
    EditText conf_password;
    Button signupButton;
    FirebaseAuth mAuth;
    String string = "@gmail.com";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        su_username = findViewById(R.id.input_su_username);
        su_password = findViewById(R.id.input_su_password);
        conf_password = findViewById(R.id.input_con_password);
        signupButton = findViewById(R.id.signup_btn);


//        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = su_username.getText().toString().trim();
                String password = su_password.getText().toString().trim();
                String passwordAgain = conf_password.getText().toString().trim();



                if (TextUtils.isEmpty(username)){
                    Toast.makeText(Sign_up.this, "Username can not be Empty!", Toast.LENGTH_SHORT).show();
                    su_username.setError("Username can not be Empty!");
                    return;
                }

                if (username.indexOf(" ") != -1){
                    Toast.makeText(Sign_up.this, "Username can not contain space!!", Toast.LENGTH_SHORT).show();
                    su_username.setError("Username can not contain space!");
                    //return;
                }else {
                    email = username.concat(string);
                    //username = username + string;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Sign_up.this, "Password can not be Empty!", Toast.LENGTH_SHORT).show();
                    su_password.setError("Password can not be Empty!");
                    //return;
                } else if(password.length() < 6){
                    Toast.makeText(Sign_up.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                    su_password.setError("Password must be at least 6 characters!");
                } else if (!password.equals(passwordAgain)){
                    Toast.makeText(Sign_up.this,"Confirm Password does not same as Password!", Toast.LENGTH_SHORT).show();
                    conf_password.setError("Confirm Password does not same as Password!");
                }


                //register the user in firebase
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Sign_up.this,"User Create Successful!", Toast.LENGTH_SHORT).show();

                            //Intent intent = new Intent(Sign_up.this, MainActivity.class);
                            startActivity(new Intent(getApplicationContext(), Log_in.class));

                        }else {
                            Toast.makeText(Sign_up.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });
    }
}