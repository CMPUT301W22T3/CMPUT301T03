package com.example.qrhunt1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView photo = findViewById(R.id.photo);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/qrhunter-344119.appspot.com/o/yuanhui%2F52d41c3b80a03dabe6af29a742cbf3014d75998d6b6982ddbbeba5b656be526b?alt=media&token=44dccdd8-9a24-4572-a364-1a19bda22dfc").into(photo);
    }
}