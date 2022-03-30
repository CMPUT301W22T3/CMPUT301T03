package com.example.qrhunt1.ui.profile;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import com.example.qrhunt1.MainActivity;
import com.example.qrhunt1.R;
import com.google.firebase.firestore.CollectionReference;


import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;

public class MyProfileFragment extends Fragment {

//    FirebaseDatabase database;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile,container,false);


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

        FirebaseDatabase database;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String user1 = "User1";
        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUser = currentUser.replace("@gmail.com","");
        text1.setText(currentUser);
        DocumentReference dbQR = db.collection("users/").document(user1);
        dbQR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    //text1.setText(documentSnapshot.getString("DisplayName"));
                    text2.setText(documentSnapshot.getString(("ContactInfo")));
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                });



////  Input username
//        String user = "John";
//        text1.setText(user);
//
////  Input Contact info
//        String phoneNumber = "Tree Street";
//        text2.setText(phoneNumber);
////        CollectionReference collectionReference = db.collection("users");
//
        
//  Highest QR code
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(10);
        list.add(3);
        list.add(11);
        list.add(2);
        Collections.sort(list);
        Integer a = list.get(list.size()-1);
        String d = Integer.toString(a);
        text10.setText(d);

//  lowest QR code
        Collections.reverse(list);
        String b = list.get(list.size()-1).toString();
        text11.setText(b);

//  Sum of Scores #
        int i;
        int sum=0;
        for (i = 0; i < list.size(); i++)
            sum += list.get(i);
        String c = Integer.toString(sum);
        text12.setText(c);

//  Total number of QR codes
        Integer num = list.size();
        String string = Integer.toString(num);
        text13.setText(string);

//  Ranking in Best QR
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(11);
        list1.add(5);
        list1.add(18);
        list1.add(5);
        list1.add(15);
        Collections.sort(list1);
        Collections.reverse(list1);
        Integer index = list1.indexOf(11)+1;
        String e = Integer.toString(index);
        text14.setText(e);

//  Ranking in Total QRs
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(5);
        list2.add(2);
        list2.add(2);
        list2.add(15);
        list2.add(0);
        Collections.sort(list2);
        Collections.reverse(list2);
        Integer index1 = list2.indexOf(5)+1;
        String f = Integer.toString(index1);
        text15.setText(f);

//  Ranking in Total Score
        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(27);
        list3.add(78);
        list3.add(25);
        list3.add(67);
        list3.add(60);
        Collections.sort(list3);
        Collections.reverse(list3);
        Integer index2 = list3.indexOf(27)+1;
        String g = Integer.toString(index2);
        text16.setText(g);

//  Login QR button
        ImageView QR1 = view.findViewById(R.id.imageView);
        ImageView QR2 = view.findViewById(R.id.imageView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(QR1.getVisibility()==View.VISIBLE) {
                    QR1.setVisibility(View.INVISIBLE);
                    QR2.setVisibility(View.VISIBLE);
                }
                else{
                    QR1.setVisibility(View.VISIBLE);
                    QR2.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
