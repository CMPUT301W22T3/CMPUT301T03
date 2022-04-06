package com.example.qrhunt1;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
        import android.app.Activity;
        import android.app.Dialog;
        import android.content.ContentResolver;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
        import android.widget.Toast;

        import com.budiyev.android.codescanner.CodeScanner;
        import com.budiyev.android.codescanner.CodeScannerView;
        import com.budiyev.android.codescanner.DecodeCallback;
        import com.budiyev.android.codescanner.ScanMode;
import com.example.qrhunt1.ui.gallery.GalleryFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.GeoPoint;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;
        import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.Result;
        import com.himanshurawat.hasher.HashType;
        import com.himanshurawat.hasher.Hasher;

        import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
        import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;

public class Scan extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    FusedLocationProviderClient fusedLocationProviderClient;
    double lat;
    double longitude;
    String hash;
    String qrScore;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String mCurrentPhotoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},101);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        //mCodeScanner.setScanMode(ScanMode.CONTINUOUS);

        // Two mode for scan - login - hunt
        String mode = getIntent().getStringExtra("mode");

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mode.equals("login")) {
                            // TODO - Do Something With Login
                            mCodeScanner.setScanMode(ScanMode.SINGLE);
                            List<String> loginInfo = new ArrayList<String>(Arrays.asList(String.valueOf(result).split(",")));
                            String userName = loginInfo.get(0).concat("@gmail.com");
                            String passWord = loginInfo.get(1);
                            //Toast.makeText(Scan.this, "userName: "+userName+" Password: "+ passWord, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("userName",userName);
                            intent.putExtra("passWord",passWord);
                            setResult(RESULT_OK,intent);
                            finish();
                        } else if (mode.equals("hunt")) {
                            // TODO - Do something with hunt qr code
                            hash = Hasher.Companion.hash(String.valueOf(result), HashType.SHA_256);
                            //Toast.makeText(Scan.this, hash, Toast.LENGTH_SHORT).show();
                            qrScore = String.valueOf(calculateScore(hash));

                            Dialog qrUploadDialog = new Dialog(Scan.this);

                            qrUploadDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT);
                            qrUploadDialog.getWindow().getAttributes().windowAnimations
                                    = android.R.style.Animation_Dialog;
                            qrUploadDialog.setContentView(R.layout.qrhunt_layout);
                            qrUploadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            ImageView close = qrUploadDialog.findViewById(R.id.close);
                            Button takePhoto = qrUploadDialog.findViewById(R.id.takePhoto);
                            Button recordLocation = qrUploadDialog.findViewById(R.id.recordLocation);
                            Button addButton = qrUploadDialog.findViewById(R.id.qrInfoAdd);
                            TextView showScore = qrUploadDialog.findViewById(R.id.qr_score);

                            showScore.setText("QR Score: "+qrScore);

                            // Close dialog
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    qrUploadDialog.dismiss();
                                }
                            });

                            String currentUser = mAuth.getCurrentUser().getEmail().replace("@gmail.com","");
                            DocumentReference docRef = db.collection("users")
                                    .document(currentUser)
                                    .collection("QR")
                                    .document(hash);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            Toast.makeText(Scan.this,"You Already Have This QR CODE!\n   Tap Screen To Keep Hunting!",Toast.LENGTH_LONG).show();
                                        } else {
                                            Log.d(TAG, "No such document");

                                            takePhoto.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    // TODO
                                                    try {
                                                        takingPhoto();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });

                                            recordLocation.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    // TODO
                                                    if (ContextCompat.checkSelfPermission(Scan.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                                            && ContextCompat.checkSelfPermission(Scan.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                        fusedLocationProviderClient.getLastLocation()
                                                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                                                    @Override
                                                                    public void onSuccess(Location location) {
                                                                        if (location != null) {
                                                                            lat = location.getLatitude();
                                                                            longitude = location.getLongitude();
                                                                            Toast.makeText(Scan.this,String.valueOf(lat) + ", "+String.valueOf(longitude), Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        ActivityCompat.requestPermissions(Scan.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                                                                Manifest.permission.ACCESS_COARSE_LOCATION},101);
                                                    }
                                                }
                                            });

                                            addButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    // TODO
                                                    uploadToUser();
                                                    if (lat != 0 && longitude != 0) {
                                                        uploadToMap();
                                                    }
                                                    finish();
                                                    /*
                                                    Fragment fragment = new GalleryFragment();
                                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

                                                     */
                                                }
                                            });
                                            qrUploadDialog.show();
                                        }
                                    }else{
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    Uri photoURI;

    private void takingPhoto() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // TODO- Somethings
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void uploadToMap() {
        GeoPoint geoPoint = new GeoPoint(lat, longitude);
        DocumentReference docRef = db.collection("QRCODE")
                .document("location");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.get("count") != null) {
                        String count = String.valueOf(Integer.parseInt(document.get("count").toString()) + 1);
                        Map<String, Object> location = new HashMap<>();
                        location.put("g" + count, geoPoint);
                        location.put("count", count);
                        Map<String, Object> score = new HashMap<>();
                        score.put("g" + count, qrScore);
                        db.collection("QRCODE")
                                .document("location")
                                .set(location, SetOptions.merge());
                        db.collection("QRCODE")
                                .document("score")
                                .set(score, SetOptions.merge());
                    } else {
                        Map<String, Object> setCount = new HashMap<>();
                        setCount.put("count", "0");
                        db.collection("QRCODE")
                                .document("location")
                                .set(setCount);
                        uploadToMap();
                    }
                }
            }
        });
    }


    private void uploadToUser() {
        String currentUser = mAuth.getCurrentUser().getEmail().replace("@gmail.com","");
        GeoPoint geoPoint = new GeoPoint(lat,longitude);
        Map<String, Object> qr = new HashMap<>();
        qr.put("Hashcode",hash);
        qr.put("Score",qrScore);
        qr.put("Location",geoPoint);
        //qr.put("Image",imageUri);

        db.collection("users")
                .document(currentUser)
                .collection("QR")
                .document(hash)
                .set(qr)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(Scan.this,"Successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(Scan.this,"Fail! "+e, Toast.LENGTH_SHORT).show();
                    }
                });

        if (currentPhotoPath != null) {
            StorageReference image = storageRef.child(currentUser+"/"+hash);
            image.putFile(photoURI).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    Log.d(TAG, "Upload is " + progress + "% done");
                }
            });
        }
    }

    /**
     *
     * @param hash
     * @return
     */
    private int calculateScore(String hash) {
        //get a list with repeated digits strings
        ArrayList<String> repeatedDigitsList = new ArrayList<>();
        for (int i=0; i<hash.length();i++){
            int n = i;
            String temp = "";
            char repeatedDigit = 'n';
            for (int m=i+1; m<hash.length();m++){
                if (hash.charAt(n) == hash.charAt(m) && n == m-1){
                    repeatedDigit = hash.charAt(m);
                    n++;
                } else {
                    n = i;
                    i = m-1;
                    break;
                }
            }
            int repeatedDigitLength = i-n+1;
            if (repeatedDigit != 'n'){
                for (int t=0; t<repeatedDigitLength; t++){
                    temp = temp + repeatedDigit;
                }
                repeatedDigitsList.add(temp);
            }
        }
        //calculate the score for repeated digits
        double score = 0;
        for (int i=0; i<repeatedDigitsList.size();i++){
            String repeatedDigits = repeatedDigitsList.get(i);
            int repeatedDigitsLength = repeatedDigits.length();
            String repeatedDigit = Character.toString(repeatedDigits.charAt(0));
            int decimal = Integer.parseInt(repeatedDigit,16);
            if (decimal == 0){
                decimal = 20;
            }
            //convert int to double and calculate part score
            double d = decimal;
            double c = (repeatedDigitsLength-1);
            double partScore = Math.pow(d,c);
            //add the part score to total score
            score = score + partScore;
        }
        int totalScore = (int) score;
        return totalScore;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}