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

public class PlayersFragment extends Fragment {

    private FragmentPlayersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlayersViewModel playersViewModel =
                new ViewModelProvider(this).get(PlayersViewModel.class);

        binding = FragmentPlayersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final EditText editText = binding.editText;
        //playersViewModel.getText().observe(getViewLifecycleOwner(), editText::setText);

        Button searchButton = binding.searchButton;
        EditText searchUser = binding.editText;
        Button bestQRButton = binding.bestQR;
        Button totalQRsButton = binding.totalQRs;
        Button totalScoreButton = binding.totalScore;
        ListView bestQRList = binding.bestQRList;
        ListView totalQRsList = binding.totalQRsList;
        ListView totalScoreList = binding.totalScoreList;

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
                totalQRsList.setVisibility(View.GONE);
                totalScoreList.setVisibility(View.GONE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalQRsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.GONE);
                totalQRsList.setVisibility(View.VISIBLE);
                totalScoreList.setVisibility(View.GONE);
            }
        });

        //click this button and show the ranking for Total QRs
        totalScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bestQRList.setVisibility(View.GONE);
                totalQRsList.setVisibility(View.GONE);
                totalScoreList.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

