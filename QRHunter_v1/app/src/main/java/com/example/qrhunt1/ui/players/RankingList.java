package com.example.qrhunt1.ui.players;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * This returns a sorted list of cities
     * @return
     * Return the sorted list
     */
    public List<Rank> sortRank() {
        List<Rank> list = ranks;
        Collections.sort(list);
        return list;
    }

    /**
     * This returns a list of ranked users
     * @return
     * Return the ranking list
     */
    public List<Rank> getRanks() {
        List<Rank> list = ranks;
        return list;
    }

    /**
     * This counts how many users are in the ranking list
     * @return
     * Return the size of ranking list
     */
    public int countCities(){
        return ranks.size();
    }

}
