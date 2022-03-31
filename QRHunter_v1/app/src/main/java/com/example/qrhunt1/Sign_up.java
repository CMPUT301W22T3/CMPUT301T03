package com.example.qrhunt1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity {

    EditText su_username;
    EditText su_password;
    EditText conf_password;
    Button signupButton;
    FirebaseAuth mAuth;
    String string = "@gmail.com";
    String email;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("users");

        su_username = findViewById(R.id.input_su_username);
        su_password = findViewById(R.id.input_su_password);
        conf_password = findViewById(R.id.input_con_password);
        signupButton = findViewById(R.id.signup_btn);
        back = findViewById(R.id.iv_signup_icon);


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
                    return;
                }
                if(username.indexOf("@") != -1){
                    Toast.makeText(Sign_up.this, "Username can not contain @!!", Toast.LENGTH_SHORT).show();
                    su_username.setError("Username can not contain @!");
                    return;
                }
                if(username.indexOf(".") != -1){
                    Toast.makeText(Sign_up.this, "Username can not contain .!!", Toast.LENGTH_SHORT).show();
                    su_username.setError("Username can not contain .!");
                    return;
                }else {
                    email = username.concat(string);
                    //username = username + string;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Sign_up.this, "Password can not be Empty!", Toast.LENGTH_SHORT).show();
                    su_password.setError("Password can not be Empty!");
                    return;
                } else if(password.length() < 6){
                    Toast.makeText(Sign_up.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
                    su_password.setError("Password must be at least 6 characters!");
                    return;
                } else if (!password.equals(passwordAgain)){
                    Toast.makeText(Sign_up.this,"Confirm Password does not same as Password!", Toast.LENGTH_SHORT).show();
                    conf_password.setError("Confirm Password does not same as Password!");
                    return;
                }


                //register the user in firebase
                if (password.equals(passwordAgain)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Sign_up.this, "User Create Successful!", Toast.LENGTH_SHORT).show();

                                Map<String, String> user = new HashMap<>();
                                user.put("UserName", username);
                                user.put("PassWord", password);
                                user.put("ContactInfo", null);
                                collectionReference.document(username).set(user);
                                db.collection("users").document(username)
                                        .set(user)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });


                            }else {
                                Toast.makeText(Sign_up.this, "Error! The username is already in use by another account!", Toast.LENGTH_SHORT).show();

                                //Intent intent = new Intent(Sign_up.this, MainActivity.class);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));


//                            } else {
//                                Toast.makeText(Sign_up.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}