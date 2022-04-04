package com.example.qrhunt1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.Result;
import com.himanshurawat.hasher.HashType;
import com.himanshurawat.hasher.Hasher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scan extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    FusedLocationProviderClient fusedLocationProviderClient;
    double lat;
    double longitude;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
                            String hash = Hasher.Companion.hash(String.valueOf(result), HashType.SHA_256);
                            //Toast.makeText(Scan.this, hash, Toast.LENGTH_SHORT).show();
                            String qrScore = String.valueOf(calculateScore(hash));

                            //upload the value to database
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String currentUser = mAuth.getCurrentUser().getEmail();
                            currentUser = currentUser.replace("@gmail.com", "");
                            Map<String, Object> info = new HashMap<>();
                            info.put("QRScore", qrScore);
                            db.collection("users").document(currentUser)
                                    .set(info, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });

                            Dialog qrUploadDialog = new Dialog(Scan.this);
//                            qrUploadDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
//                                    WindowManager.LayoutParams.WRAP_CONTENT);
//                            qrUploadDialog.getWindow().getAttributes().windowAnimations
//                                    = android.R.style.Animation_Dialog;
                            qrUploadDialog.setContentView(R.layout.qrhunt_layout);

                            Button takePhoto = qrUploadDialog.findViewById(R.id.takePhoto);
                            Button recordLocation = qrUploadDialog.findViewById(R.id.recordLocation);
                            Button addButton = qrUploadDialog.findViewById(R.id.qrInfoAdd);
                            TextView showScore = qrUploadDialog.findViewById(R.id.qr_score);
                            showScore.setText("QR Score: "+qrScore);

                            takePhoto.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // TODO
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivity(cameraIntent);
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
                                }
                            });
                            qrUploadDialog.show();
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