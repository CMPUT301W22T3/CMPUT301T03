package com.example.qrhunt1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Signup extends AppCompatActivity {

    EditText su_username = findViewById(R.id.input_su_username);
    EditText su_password = findViewById(R.id.input_su_password);
    Button signup_btn = findViewById(R.id.signup_btn);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

}
