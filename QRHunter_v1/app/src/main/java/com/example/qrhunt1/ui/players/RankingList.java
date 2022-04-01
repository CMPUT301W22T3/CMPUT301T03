package com.example.qrhunt1.ui.players;

import java.util.ArrayList;
import java.util.List;

public class RankingList {

    private List<Rank> ranks = new ArrayList<>();

    /**
     * This adds a user's ranking to the list
     * @param rank
     * This is a top user to add
     */
    public void add(Rank rank) {
        if (ranks.contains(rank)) {
            throw new IllegalArgumentException();
        }
        ranks.add(rank);
    }


}
