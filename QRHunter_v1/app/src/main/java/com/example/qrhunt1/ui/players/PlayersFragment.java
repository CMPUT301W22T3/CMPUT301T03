package com.example.qrhunt1.ui.players;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrhunt1.R;
import com.example.qrhunt1.ui.profile.OtherProfileFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayersFragment extends Fragment {

    private ArrayAdapter<Rank> bestQRArrayAdapter;
    private ArrayList<Rank> bestQRDataList;
    private ArrayAdapter<Rank> totalQRsArrayAdapter;
    private ArrayList<Rank> totalQRsDataList;
    private ArrayAdapter<Rank> totalScoreArrayAdapter;
    private ArrayList<Rank> totalScoreDataList;
    private ArrayList<String> userDataList;
    private ArrayList<String> searchResultDataList;
    private ArrayAdapter<String> searchResultAdapter;

    TextView hint;
    Button searchButton;
    EditText searchUser;
    Button bestQRButton;
    Button totalQRsButton;
    Button totalScoreButton;
    ListView bestQRList;
    ListView totalQRsList;
    ListView totalScoreList;
    ListView searchResultListview;

    CustomList customList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players,container,false);

        hint = view.findViewById(R.id.hint);
        searchButton = view.findViewById(R.id.search_button);
        searchUser = view.findViewById(R.id.editText);
        bestQRButton = view.findViewById(R.id.bestQRButton);
        totalQRsButton = view.findViewById(R.id.totalQRsButton);
        totalScoreButton = view.findViewById(R.id.totalScoreButton);
        bestQRList = view.findViewById(R.id.bestQRList);
        totalQRsList = view.findViewById(R.id.totalQRsList);
        totalScoreList = view.findViewById(R.id.totalScoreList);
        searchResultListview = view.findViewById(R.id.searchResultList);
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

        String []mockUserName = {"promise","wenxin","wencin","emma","emmapixiv"};
        userDataList = new ArrayList<>();

        userDataList.addAll(Arrays.asList(mockUserName));

        searchResultDataList = new ArrayList<>();
        //update the listview
        searchResultAdapter = new ArrayAdapter<>(getActivity(),R.layout.search_result_list,searchResultDataList);
        searchResultListview.setAdapter(searchResultAdapter);
        //show matching search result list
        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //once enter text in edit text
                if (charSequence.toString().length()>0) {
                    //clear previous list
                    searchResultDataList.clear();
                    //add all matching search result to searchResultDataList
                    for (int v=0; v<userDataList.size();v++){
                        if (userDataList.get(v).contains(charSequence.toString())) {
                            searchResultDataList.add(userDataList.get(v));
                        }
                    }
                    //show the search result matching list
                    searchResultListview.setVisibility(View.VISIBLE);
                    //set shown buttons and lists to invisible
                    bestQRButton.setVisibility(View.INVISIBLE);
                    totalQRsButton.setVisibility(View.INVISIBLE);
                    totalScoreButton.setVisibility(View.INVISIBLE);
                    hint.setVisibility(View.GONE);
                    if (bestQRList.getVisibility() == View.VISIBLE || totalQRsList.getVisibility() == View.VISIBLE || totalScoreList.getVisibility() == View.VISIBLE) {
                        if (bestQRList.getVisibility() == View.VISIBLE){
                            bestQRList.setVisibility(View.GONE);
                        } else if (totalQRsList.getVisibility() == View.VISIBLE) {
                            totalQRsList.setVisibility(View.GONE);
                        } else {
                            totalScoreList.setVisibility(View.GONE);
                        }
                    }
                    //update searchResultDataList
                    searchResultListview.setAdapter(searchResultAdapter);

                } else if (charSequence.toString().length()==0){//empty edit text view
                    //let search result list be invisible
                    searchResultListview.setVisibility(View.INVISIBLE);
                    //reshow the hidden buttons and list/hint
                    bestQRButton.setVisibility(View.VISIBLE);
                    totalQRsButton.setVisibility(View.VISIBLE);
                    totalScoreButton.setVisibility(View.VISIBLE);
                    if (bestQRList.getVisibility() == View.GONE || totalQRsList.getVisibility() == View.GONE || totalScoreList.getVisibility() == View.GONE) {
                        hint.setVisibility(View.GONE);
                        if (bestQRList.getVisibility() == View.GONE){
                            bestQRList.setVisibility(View.VISIBLE);
                        } else if (totalQRsList.getVisibility() == View.GONE) {
                            totalQRsList.setVisibility(View.VISIBLE);
                        } else {
                            totalScoreList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        hint.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //set the text in edit text with the matching result we chose from the search result list
       /* searchResultListview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


        //click three buttons to show three ranking lists
        //click this button
        searchButton.setOnClickListener(view1 -> {
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
        });

        //click this button and show the ranking for best QR
        bestQRButton.setOnClickListener(view12 -> {
            bestQRList.setVisibility(View.VISIBLE);
            totalQRsList.setVisibility(View.INVISIBLE);
            totalScoreList.setVisibility(View.INVISIBLE);
            hint.setVisibility(View.GONE);
        });

        //click this button and show the ranking for Total QRs
        totalQRsButton.setOnClickListener(view13 -> {
            bestQRList.setVisibility(View.INVISIBLE);
            totalQRsList.setVisibility(View.VISIBLE);
            totalScoreList.setVisibility(View.INVISIBLE);
            hint.setVisibility(View.GONE);
        });

        //click this button and show the ranking for Total QRs
        totalScoreButton.setOnClickListener(view14 -> {
            bestQRList.setVisibility(View.INVISIBLE);
            totalQRsList.setVisibility(View.INVISIBLE);
            totalScoreList.setVisibility(View.VISIBLE);
            hint.setVisibility(View.GONE);
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

        /*
        Mock calculating score

        String hash = "8227ad000b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32";
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
        //test
        System.out.println(repeatedDigitsList);
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
        //test
        System.out.println(totalScore);

         */