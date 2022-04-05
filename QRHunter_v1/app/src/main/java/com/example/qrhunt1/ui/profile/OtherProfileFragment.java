package com.example.qrhunt1.ui.profile;

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
import com.example.qrhunt1.R;
import com.example.qrhunt1.ui.players.PlayersFragment;
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


////  Highest QR code
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(10);
//        list.add(3);
//        list.add(11);
//        list.add(2);
//        Collections.sort(list);
//        Integer a = list.get(list.size()-1);
//        String d = Integer.toString(a);
//        text10.setText(d);
//
////  lowest QR code
//        Collections.reverse(list);
//        String b = list.get(list.size()-1).toString();
//        text11.setText(b);
//
////  Sum of Scores #
//        int i;
//        int sum=0;
//        for (i = 0; i < list.size(); i++)
//            sum += list.get(i);
//        String c = Integer.toString(sum);
//        text12.setText(c);
//
////  Total number of QR codes
//        Integer num = list.size();
//        String string = Integer.toString(num);
//        text13.setText(string);
//
////  Ranking in Best QR
//        ArrayList<Integer> list1 = new ArrayList<>();
//        list1.add(11);
//        list1.add(5);
//        list1.add(18);
//        list1.add(5);
//        list1.add(15);
//        Collections.sort(list1);
//        Collections.reverse(list1);
//        Integer index = list1.indexOf(11)+1;
//        String j = Integer.toString(index);
//        text14.setText(j);
//
////  Ranking in Total QRs
//        ArrayList<Integer> list2 = new ArrayList<>();
//        list2.add(5);
//        list2.add(2);
//        list2.add(2);
//        list2.add(15);
//        list2.add(0);
//        Collections.sort(list2);
//        Collections.reverse(list2);
//        Integer index1 = list2.indexOf(5)+1;
//        String f = Integer.toString(index1);
//        text15.setText(f);
//
////  Ranking in Total Score
//        ArrayList<Integer> list3 = new ArrayList<>();
//        list3.add(27);
//        list3.add(78);
//        list3.add(25);
//        list3.add(67);
//        list3.add(60);
//        Collections.sort(list3);
//        Collections.reverse(list3);
//        Integer index2 = list3.indexOf(27)+1;
//        String g = Integer.toString(index2);
//        text16.setText(g);

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
