package com.example.qrhunt1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrhunt1.ui.profile.MyProfileFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Owner extends AppCompatActivity {

    private ArrayList<Uri> photoDataList;
    private ArrayAdapter<Uri> photoListAdapter;
    private ArrayList<String> userNameDataList;
    private ArrayAdapter<String> userNameAdapter;
    // Reference to an image file in Cloud Storage
    // Create a storage reference from our app


    //StorageReference storageRef = "gs://qrhunter-344119.appspot.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Button exit = findViewById(R.id.exit);
        Button photo = findViewById(R.id.photo);
        ListView photoList = findViewById(R.id.photoList);


        userNameDataList = new ArrayList<>();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Owner.this, MyProfileFragment.class);
                finish();

            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoList.setVisibility(View.VISIBLE);

            }
        });


    }
}