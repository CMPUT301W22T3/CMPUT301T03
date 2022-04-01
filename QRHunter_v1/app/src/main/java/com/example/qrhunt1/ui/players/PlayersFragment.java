package com.example.qrhunt1.ui.players;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrhunt1.MainActivity;
import com.example.qrhunt1.R;
import com.example.qrhunt1.ui.profile.OtherProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class PlayersFragment extends Fragment {

    ArrayAdapter<Rank> bestQRArrayAdapter;
    ArrayList<Rank> bestQRDataList;
    ArrayAdapter<Rank> totalQRsArrayAdapter;
    ArrayList<Rank> totalQRsDataList;
    ArrayAdapter<Rank> totalScoreArrayAdapter;
    ArrayList<Rank> totalScoreDataList;

    TextView noResult;
    Button searchButton;
    EditText searchUser;
    Button bestQRButton;
    Button totalQRsButton;
    Button totalScoreButton;
    ListView bestQRList;
    ListView totalQRsList;
    ListView totalScoreList;

    CustomList customList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players,container,false);

        noResult = view.findViewById(R.id.noResults);
        searchButton = view.findViewById(R.id.search_button);
        searchUser = view.findViewById(R.id.editText);
        bestQRButton = view.findViewById(R.id.bestQRButton);
        totalQRsButton = view.findViewById(R.id.totalQRsButton);
        totalScoreButton = view.findViewById(R.id.totalScoreButton);
        bestQRList = view.findViewById(R.id.bestQRList);
        totalQRsList = view.findViewById(R.id.totalQRsList);
        totalScoreList = view.findViewById(R.id.totalScoreList);

        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dbQR = db.collection("users/").document(user);
        dbQR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
        */

        //mock data
        String []bestQRUsers = {"user1","user2","user3","user4","user5"};
        String []totalQRsUsers = {"user6","user7","user8","user9","user10"};
        String []totalScoreUsers = {"user11","user12","user13","user14","user15"};

        int []bestQRScores = {20,23,55,34,56};
        int []totalQRsScores = {3,12,106,368,0};
        int []totalScoreScores = {26,34,68,99,101};

        bestQRDataList = new ArrayList<>();
        totalQRsDataList = new ArrayList<>();
        totalScoreDataList = new ArrayList<>();

        //add data to corresponding ranking list
        for(int i=0;i<bestQRUsers.length;i++){
            bestQRDataList.add((new Rank(bestQRUsers[i], bestQRScores[i])));
            totalQRsDataList.add((new Rank(totalQRsUsers[i], totalQRsScores[i])));
            totalScoreDataList.add((new Rank(totalScoreUsers[i], totalScoreScores[i])));
        }

        //sort the top 5 players by ascending order
        Collections.sort(bestQRDataList);
        Collections.sort(totalQRsDataList);
        Collections.sort(totalScoreDataList);

        //set the rank for the top 5 players
        for(int i=0;i<bestQRUsers.length;i++){
            bestQRDataList.get(i).setUserRank(i+1);
            totalQRsDataList.get(i).setUserRank(i+1);
            totalScoreDataList.get(i).setUserRank(i+1);
        }

        bestQRArrayAdapter = new CustomList(getActivity(),bestQRDataList);
        totalQRsArrayAdapter = new CustomList(getActivity(),totalQRsDataList);
        totalScoreArrayAdapter = new CustomList(getActivity(),totalScoreDataList);

        bestQRList.setAdapter(bestQRArrayAdapter);
        totalQRsList.setAdapter(totalQRsArrayAdapter);
        totalScoreList.setAdapter(totalScoreArrayAdapter);

        //show the search results for key word



        //click this button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass the input username to profile fragment
                String username = searchUser.getText().toString();
                Bundle args = new Bundle();
                args.putString("Username", username);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new OtherProfileFragment();
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.container, fragment, "Players");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //click this button and show the ranking for best QR
        bestQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.VISIBLE);
                totalQRsList.setVisibility(View.INVISIBLE);
                totalScoreList.setVisibility(View.INVISIBLE);
                noResult.setVisibility(View.GONE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalQRsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.INVISIBLE);
                totalQRsList.setVisibility(View.VISIBLE);
                totalScoreList.setVisibility(View.INVISIBLE);
                noResult.setVisibility(View.GONE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.INVISIBLE);
                totalQRsList.setVisibility(View.INVISIBLE);
                totalScoreList.setVisibility(View.VISIBLE);
                noResult.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

