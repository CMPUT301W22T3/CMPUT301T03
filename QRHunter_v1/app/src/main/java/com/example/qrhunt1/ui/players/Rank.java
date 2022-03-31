package com.example.qrhunt1.ui.players;

public class Rank implements Comparable<Rank>{
    private String userRank;
    private String userName;
    private String userScore;

    /**
     * This is the constructor of the Rank class
     * @param userName
     * This is the user name
     * @param userScore
     * This is the user score
     */
    Rank(String userRank, String userName, String userScore){
        this.userRank = userRank;
        this.userName = userName;
        this.userScore = userScore;
    }
    /**
     * This returns user's ranking position
     * @return
     * Returns ranking of the user
     */
    String getUserRank(){
        return this.userRank;
    }

    /**
     * This sets the user's ranking
     */
    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    /**
     * This returns the name of the user
     * @return
     * Returns name of the user
     */
    String getUserName(){
        return this.userName;
    }

    /**
     * This returns user's score
     * @return
     * Returns score of the user
     */
    String getUserScore(){
        return this.userScore;
    }
    /**
     * This compares city with city and returns an int
     * @param rank
     * This is the city we want to compare with
     * @return
     * Returns an int
     */
    @Override
    public int compareTo(Rank rank) {
        return this.userScore.compareTo(rank.getUserScore());
    }

}
