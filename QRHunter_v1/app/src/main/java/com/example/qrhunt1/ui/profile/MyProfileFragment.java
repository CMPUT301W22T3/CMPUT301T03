package com.example.qrhunt1.ui.profile;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.qrhunt1.Owner;
import com.example.qrhunt1.ui.players.CustomList;
import com.example.qrhunt1.ui.players.Rank;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import com.example.qrhunt1.R;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProfileFragment extends Fragment {

    //    FirebaseDatabase database;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<String> QRStringDataList;
    private ArrayList<Integer> QRIntegerDataList;
    private ArrayList<Rank> bestQRDataList;
    private ArrayList<Rank> totalQRsDataList;
    private ArrayList<Rank> totalScoreDataList;
    private ArrayList<String> userQRStringList;
    private ArrayList<Integer> userQRIntList;
    private ArrayList<String> userNameList;
    CustomList customList;


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);


        //  My profile
        TextView text1 = view.findViewById(R.id.textView);
        TextView text2 = view.findViewById(R.id.textView2);
        TextView text10 = view.findViewById(R.id.textView10);
        TextView text11 = view.findViewById(R.id.textView11);
        TextView text12 = view.findViewById(R.id.textView12);
        TextView text13 = view.findViewById(R.id.textView13);
        TextView text14 = view.findViewById(R.id.textView14);
        TextView text15 = view.findViewById(R.id.textView15);
        TextView text16 = view.findViewById(R.id.textView16);
        Button button = view.findViewById(R.id.button);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        ImageView QR1 = view.findViewById(R.id.imageView);
        ImageView QR2 = view.findViewById(R.id.imageView3);
        TextView text18 = view.findViewById(R.id.textView18);
        Button button4 = view.findViewById(R.id.edit);


//get data from firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUser = currentUser.replace("@gmail.com", "");
        text1.setText(currentUser);
        DocumentReference dbUser = db.collection("users/").document(currentUser);
        dbUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //text1.setText(documentSnapshot.getString("DisplayName"));
                    text2.setText(documentSnapshot.getString(("ContactInfo")));
                    text18.setText(documentSnapshot.getString(("PassWord")));


                    //generate QR code base on username (use for Profile)

                    String sText = text1.getText().toString().trim();
                    //Initialize multi format writer

                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        //Initialize bit matrix
                        BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 158, 151);
                        //Initialize barcode encoder
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        //Create bitmap of the code
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        QR1.setImageBitmap(bitmap);
                    } catch (WriterException l) {
                        l.printStackTrace();
                    }

//generate login QR
                    String loginPassword = text18.getText().toString();
                    String login = sText + ',' + loginPassword;
                    //Initialize multi format writer
                    MultiFormatWriter loginWriter = new MultiFormatWriter();
                    try {
                        //Initialize bit matrix
                        BitMatrix loginMatrix = loginWriter.encode(login, BarcodeFormat.QR_CODE, 158, 151);
                        //Initialize barcode encoder
                        BarcodeEncoder loginEncoder = new BarcodeEncoder();
                        //Create bitmap of the code
                        Bitmap loginBitmap = loginEncoder.createBitmap(loginMatrix);
                        QR2.setImageBitmap(loginBitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    text18.setVisibility(View.INVISIBLE);


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });


        QRStringDataList = new ArrayList<>();
        QRIntegerDataList = new ArrayList<>();
//  Highest QR code
        CollectionReference dbQR = db.collection("users/").document(currentUser).collection("QR/");
        dbQR.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                if (snapshotList.isEmpty()) {
                    text10.setText("N/A");
                    text11.setText("N/A");
                    text12.setText("N/A");
                    text13.setText("N/A");
                } else {
                    for (DocumentSnapshot snapshot : snapshotList) {
                        if (snapshot.exists()) {
                            QRStringDataList.add(snapshot.getString("Score"));
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    for (int i = 0; i < QRStringDataList.size(); i++) {
                        Integer number = Integer.valueOf(QRStringDataList.get(i));
                        QRIntegerDataList.add(number);
                    }
                    Collections.sort(QRIntegerDataList);
                    Integer lastInt = QRIntegerDataList.get(QRIntegerDataList.size() - 1);
                    text10.setText(lastInt.toString());

                    //lowest QR
                    Integer firstInt = QRIntegerDataList.get(0);
                    text11.setText(firstInt.toString());

                    //Sum of Scores
                    Integer sum = 0;
                    for (int i = 0; i < QRIntegerDataList.size(); i++) {
                        sum += QRIntegerDataList.get(i);
                    }
                    String c = Integer.toString(sum);
                    text12.setText(c);

                    //Total number of QR codes
                    Integer num = QRIntegerDataList.size();
                    String str1 = Integer.toString(num);
                    text13.setText(str1);

                }
            }

        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        //username list from database
        userNameList = new ArrayList<>();

        //three ranking lists with rank objects
        bestQRDataList = new ArrayList<>();
        totalQRsDataList = new ArrayList<>();
        totalScoreDataList = new ArrayList<>();

        //retrieve data from fire store
        CollectionReference dbQQ = db.collection("users/");
        String finalCurrentUser1 = currentUser;
        dbQQ.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.exists()) {
                        String currentUser1 = snapshot.getString("UserName");
                        //String and Integer qr list from database
                        userQRStringList = new ArrayList<>();
                        userQRIntList = new ArrayList<>();
                        //get qr list for each user
                        CollectionReference dbQR = dbQQ.document(currentUser1).collection("QR/");
                        dbQR.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                if (snapshotList.isEmpty()){
                                    text14.setText("N/A");
                                    text15.setText("N/A");
                                    text16.setText("N/A");
                                } else {
                                    //add username to userNameList
                                    userNameList.add(currentUser1);
                                    //when this currentUser has QR codes in their gallery
                                    if (!snapshotList.isEmpty()) {
                                        userQRStringList.clear();
                                        userQRIntList.clear();
                                        for (DocumentSnapshot snapshot : snapshotList) {
                                            if (snapshot.exists()) {
                                                userQRStringList.add(snapshot.getString("Score"));
                                            } else {
                                                Toast.makeText(getActivity().getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        Integer currUserTotalScore = 0;
                                        //sort user QR score in descending order
                                        for (int i = 0; i < userQRStringList.size(); i++) {
                                            Integer number = Integer.valueOf(userQRStringList.get(i));
                                            userQRIntList.add(number);
                                            currUserTotalScore = currUserTotalScore + number;
                                        }
                                        Collections.sort(userQRIntList, Collections.reverseOrder());

                                        //add the best QR data to current user
                                        bestQRDataList.add(new Rank(currentUser1, userQRIntList.get(0)));
                                        //add the total number of QR codes the current user scanned
                                        totalQRsDataList.add(new Rank(currentUser1, userQRIntList.size()));
                                        //add the total score for current user
                                        totalScoreDataList.add(new Rank(currentUser1, currUserTotalScore));

                                        //sort the top 5 players by ascending order
                                        Collections.sort(bestQRDataList);
                                        Collections.sort(totalQRsDataList);
                                        Collections.sort(totalScoreDataList);

                                        //set the rank for the top 5 players
                                        for (int i = 0; i < bestQRDataList.size(); i++) {
                                            bestQRDataList.get(i).setUserRank(i + 1);
                                            totalQRsDataList.get(i).setUserRank(i + 1);
                                            totalScoreDataList.get(i).setUserRank(i + 1);
                                        }
                                        for (int i = 0; i < bestQRDataList.size(); i++) {
                                            String str2 = bestQRDataList.get(i).getUserName();
                                            if (str2.equals(finalCurrentUser1)) {
                                                text14.setText(bestQRDataList.get(i).getUserRank().toString());
                                            }
                                        }
                                        for (int i = 0; i < totalQRsDataList.size(); i++) {
                                            String str2 = totalQRsDataList.get(i).getUserName();
                                            if (str2.equals(finalCurrentUser1)) {
                                                text15.setText(totalQRsDataList.get(i).getUserRank().toString());
                                            }
                                        }
                                        for (int i = 0; i < totalScoreDataList.size(); i++) {
                                            String str2 = totalScoreDataList.get(i).getUserName();
                                            if (str2.equals(finalCurrentUser1)) {
                                                text16.setText(totalScoreDataList.get(i).getUserRank().toString());
                                            }
                                        }

                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                }}
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

//switch button
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(QR1.getVisibility() == View.INVISIBLE){
                    QR1.setVisibility(View.VISIBLE);
                    QR2.setVisibility(View.INVISIBLE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QR1.getVisibility() == View.VISIBLE) {
                    QR1.setVisibility(View.INVISIBLE);
                    QR2.setVisibility(View.VISIBLE);
                }
            }
        });

// Edit Contact info.
        AlertDialog dialog;
        EditText editText;

        dialog = new AlertDialog.Builder(getActivity()).create();
        editText = new EditText(getContext());

        dialog.setTitle("Edit the Contact Info");
        dialog.setView(editText);
        String finalCurrentUser = currentUser;
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE INFO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text2.setText(editText.getText());
                Map<String, Object> info = new HashMap<>();
                String str = editText.getText().toString();
                info.put("ContactInfo", str);

                db.collection("users").document(finalCurrentUser)
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
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(text2.getText());
                dialog.show();
            }
        });

        if(currentUser.equals("wen")) {
            button4.setVisibility(View.VISIBLE);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Owner.class);
                    startActivity(intent);
                }
            });
        }
        else{
            button4.setVisibility(View.INVISIBLE);
        }


        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}