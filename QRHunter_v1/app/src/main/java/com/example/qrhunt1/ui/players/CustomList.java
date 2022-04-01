package com.example.qrhunt1.ui.players;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qrhunt1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomList extends ArrayAdapter<Rank> {

    private ArrayList<Rank> ranks;
    private Context context;

    public CustomList(Context context, ArrayList<Rank> ranks){
        super(context,0, ranks);
        this.ranks = ranks;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ranking_list, parent, false);
        }

        Rank rank = ranks.get(position);

        TextView userRank = view.findViewById(R.id.user_rank);
        TextView userName = view.findViewById(R.id.username_text);
        TextView userScore = view.findViewById(R.id.user_score_text);

        //convert int to string
        String userRankString = Integer.toString(rank.getUserRank());
        String userScoreString = Integer.toString(rank.getUserScore());

        userRank.setText(userRankString);
        userName.setText(rank.getUserName());
        userScore.setText(userScoreString);

        return view;

    }

}
