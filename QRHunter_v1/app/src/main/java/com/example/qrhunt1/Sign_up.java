package com.example.qrhunt1;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Bundle;
=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
>>>>>>> d5eeeb35ebb5c6d07fac5221d4969914c39e62fd

public class Sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
<<<<<<< HEAD
=======

        Button signUpButton = findViewById(R.id.signup_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, MainActivity.class);
                startActivity(intent);
            }
        });
>>>>>>> d5eeeb35ebb5c6d07fac5221d4969914c39e62fd
    }
}