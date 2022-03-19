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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrhunt1.GameQRCode;
import com.example.qrhunt1.databinding.FragmentPlayersBinding;
import com.example.qrhunt1.R;
import com.example.qrhunt1.ui.Login.CallbackFragment;
import com.example.qrhunt1.ui.profile.MyProfileFragment;

import java.util.ArrayList;

public class PlayersFragment extends Fragment {

    CallbackFragment callbackFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players,container,false);

        TextView noResult = view.findViewById(R.id.noResults);
        Button searchButton = view.findViewById(R.id.search_button);
        EditText searchUser = view.findViewById(R.id.editText);
        Button bestQRButton = view.findViewById(R.id.bestQRButton);
        Button totalQRsButton = view.findViewById(R.id.totalQRsButton);
        Button totalScoreButton = view.findViewById(R.id.totalScoreButton);
        ListView bestQRList = view.findViewById(R.id.bestQRList);
        ListView totalQRsList = view.findViewById(R.id.totalQRsList);
        ListView totalScoreList = view.findViewById(R.id.totalScoreList);


        ArrayAdapter<?> bestQRArrayAdapter;
        ArrayList<?> bestQRArrayList;
        ArrayAdapter<?> totalQRsArrayAdapter;
        ArrayList<?> totalQRsArrayList;
        ArrayAdapter<?> totalScoreArrayAdapter;
        ArrayList<?> totalScoreArrayList;

        String username = searchUser.getText().toString();
        //click this button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //direct to searched user's profile
                Fragment fragment = new MyProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
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

