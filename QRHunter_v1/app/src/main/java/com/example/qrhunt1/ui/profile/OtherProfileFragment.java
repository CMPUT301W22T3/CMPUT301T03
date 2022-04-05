package com.example.qrhunt1.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrhunt1.MainActivity;
import com.example.qrhunt1.NaviTest;
import com.example.qrhunt1.R;
import com.example.qrhunt1.ui.players.CustomList;
import com.example.qrhunt1.ui.players.PlayersFragment;
import com.example.qrhunt1.ui.players.Rank;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherProfileFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_other_profile,container,false);



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
        ImageView imageView = view.findViewById(R.id.imageView2);

        //  Input username
        //get the input username
        String user = getArguments().getString("Username");
        //String user = "John";
        text1.setText(user);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dbUser = db.collection("users/").document(user);
        dbUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //text1.setText(documentSnapshot.getString("DisplayName"));
                    text2.setText(documentSnapshot.getString(("ContactInfo")));


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
        //  Find the path
        CollectionReference dbQR = db.collection("users/").document(user).collection("QR/");
        dbQR.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.exists()){
                        QRStringDataList.add(snapshot.getString("Score"));
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                }
        //sort the list
                for (int i = 0; i < QRStringDataList.size(); i++) {
                    Integer number = Integer.valueOf(QRStringDataList.get(i));
                    QRIntegerDataList.add(number);
                }
                Collections.sort(QRIntegerDataList);

                //Highest QR
                Integer lastInt = QRIntegerDataList.get(QRIntegerDataList.size() - 1);
                text10.setText(lastInt.toString());

                //lowest QR
                Integer firstInt = QRIntegerDataList.get(0);
                text11.setText(firstInt.toString());

                //Sum of Scores
                Integer sum = 0;
                for(int i =0; i < QRIntegerDataList.size(); i++){
                    sum += QRIntegerDataList.get(i);
                }
                String c = Integer.toString(sum);
                text12.setText(c);

                //Total number of QR codes
                Integer num = QRIntegerDataList.size();
                String str1 = Integer.toString(num);
                text13.setText(str1);

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
                                        if (str2.equals(user)){
                                            text14.setText(bestQRDataList.get(i).getUserRank().toString());
                                        }
                                    }
                                    for (int i = 0; i < totalQRsDataList.size(); i++) {
                                        String str2 = totalQRsDataList.get(i).getUserName();
                                        if (str2.equals(user)) {
                                            text15.setText(totalQRsDataList.get(i).getUserRank().toString());
                                        }
                                    }
                                    for (int i = 0; i < totalScoreDataList.size(); i++) {
                                        String str2 = totalScoreDataList.get(i).getUserName();
                                        if (str2.equals(user)) {
                                            text16.setText(totalScoreDataList.get(i).getUserRank().toString());
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


        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new PlayersFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();

            }
        });

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
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return view;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
