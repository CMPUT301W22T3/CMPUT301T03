package com.example.qrhunt1.ui.players;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrhunt1.databinding.FragmentPlayersBinding;
import com.example.qrhunt1.R;

public class PlayersFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_players,container,false);

        Button searchButton = view.findViewById(R.id.search_button);
        EditText searchUser = view.findViewById(R.id.editText);
        Button bestQRButton = view.findViewById(R.id.bestQRButton);
        Button totalQRsButton = view.findViewById(R.id.totalQRsButton);
        Button totalScoreButton = view.findViewById(R.id.totalScoreButton);
        ListView bestQRList = view.findViewById(R.id.bestQRList);
        ListView totalQRsList = view.findViewById(R.id.totalQRsList);
        ListView totalScoreList = view.findViewById(R.id.totalScoreList);

        String username = searchUser.getText().toString();
        //click this button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to searched user's profile
            }
        });

        //click this button and show the ranking for best QR
        bestQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.VISIBLE);
                totalQRsList.setVisibility(View.INVISIBLE);
                totalScoreList.setVisibility(View.INVISIBLE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalQRsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.INVISIBLE);
                totalQRsList.setVisibility(View.VISIBLE);
                totalScoreList.setVisibility(View.INVISIBLE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.INVISIBLE);
                totalQRsList.setVisibility(View.INVISIBLE);
                totalScoreList.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

