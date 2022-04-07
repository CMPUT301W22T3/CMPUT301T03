package com.example.qrhunt1;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Owner extends AppCompatActivity {

    private ArrayList<UserPhoto> userPhotoDataList;
    private ArrayAdapter<UserPhoto> userPhotoAdapter;

    //layout for owners
    Button exitButton;
    Button showPhotoButton;
    Button deleteButton;
    ListView photoList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        exitButton = findViewById(R.id.exit);
        showPhotoButton = findViewById(R.id.showPhoto);
        deleteButton = findViewById(R.id.deleteButton);
        photoList = findViewById(R.id.list);

        //initialize list
        userPhotoDataList = new ArrayList<>();

        //update the listview
        userPhotoAdapter = new ArrayAdapter<>(this, R.layout.photo_row, userPhotoDataList);
        photoList.setAdapter(userPhotoAdapter);

        //retrieve data from fire store
        CollectionReference dbUser = db.collection("users/");
        dbUser.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> usersList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot user : usersList) {
                    if (user.exists()){
                        String currentUser = user.getString("UserName");
                        //get qr list for each user
                        CollectionReference dbQR = dbUser.document(currentUser).collection("QR/");
                        dbQR.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> QRList = queryDocumentSnapshots.getDocuments();
                                //when this currentUser has QR codes in their gallery
                                if (!QRList.isEmpty()) {
                                    for (DocumentSnapshot QR : QRList) {
                                        String URL = QR.getString("URL");
                                        if (QR.exists() && URL != null) {
                                            String hashcode = QR.getString("Hashcode");
                                            //add the QR image URL to current user
                                            UserPhoto userPhoto = new UserPhoto(URL,hashcode);
                                            userPhoto.setUserName(currentUser);
                                            userPhotoDataList.add(userPhoto);
                                        }
                                    }
                                    userPhotoAdapter = new photoList(getApplicationContext(),userPhotoDataList);
                                    photoList.setAdapter(userPhotoAdapter);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        //click photo button to see all the images taken by users
        showPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoList.setVisibility(View.VISIBLE);
            }
        });

        //delete selected photo
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPhoto = photoList.getCheckedItemPosition();
                String usernameToSelectedPhoto = userPhotoDataList.get(selectedPhoto).getUserName();
                String QRHashcode = userPhotoDataList.get(selectedPhoto).getHashcode();
                DocumentReference dbQR = db.collection("users/").document(usernameToSelectedPhoto).collection("QR/").document(QRHashcode);
                Map<String, Object> note = new HashMap<>();
                note.put("URL", FieldValue.delete());
                dbQR.update(note);

                //update the listview
                userPhotoDataList.remove(selectedPhoto);
                userPhotoAdapter = new photoList(getApplicationContext(),userPhotoDataList);
                photoList.setAdapter(userPhotoAdapter);
            }
        });

        //click exit button to exit this page
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}