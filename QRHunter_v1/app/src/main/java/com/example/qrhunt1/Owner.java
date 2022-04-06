package com.example.qrhunt1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrhunt1.ui.profile.MyProfileFragment;
import com.google.android.gms.common.internal.constants.ListAppsActivityContract;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Owner extends ListActivity {


    private ArrayList<String> urlDataList;
    private ArrayAdapter<String> urlAdapter;
    private ArrayList<String> userNameDataList;
    private ArrayAdapter<String> userNameAdapter;
    // Reference to an image file in Cloud Storage
    // Create a storage reference from our app

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("users");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    Button exit;
    Button photo;
    ListView photoList;

    //StorageReference storageRef = "gs://qrhunter-344119.appspot.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);


        exit = findViewById(R.id.exit);
        photo = findViewById(R.id.photo);
        photoList = findViewById(R.id.photoList);

        userNameDataList = new ArrayList<>();
        urlDataList = new ArrayList<>();

        urlAdapter = new ArrayAdapter<>(this, R.layout.photo_row, urlDataList);
        photoList.setAdapter(urlAdapter);

        StorageReference listRef = FirebaseStorage.getInstance().getReference("/");

        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        //Log.d(TAG, listRef.getName());
                        for (StorageReference prefix: listResult.getPrefixes()){
                            //Log.d(TAG, prefix.getName());
                            //listAll();

                        }
                        for(StorageReference item: listResult.getItems()){

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        //userNameDataList.add(listRef);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoList.setVisibility(View.VISIBLE);

            }
        });


        //click exit button to exit this page
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Owner.this, MyProfileFragment.class);
                finish();

            }
        });
    }
}