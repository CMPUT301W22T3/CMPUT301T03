package com.example.qrhunt1.ui.players;

public class Rank implements Comparable<Rank>{
    private Integer userRank;
    private String userName;
    private Integer userScore;

    /**
     * This is the constructor of the Rank class
     * @param userName
     * This is the user name
     * @param userScore
     * This is the user score
     */
    Rank(String userName, Integer userScore){
        this.userRank = 0;
        this.userName = userName;
        this.userScore = userScore;
    }
    /**
     * This returns user's ranking position
     * @return
     * Returns ranking of the user
     */
    Integer getUserRank(){
        return this.userRank;
    }

    /**
     * This sets the user's ranking
     */
    public void setUserRank(int userRank) {
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
    Integer getUserScore(){
        return this.userScore;
    }

    /**
     * This compares rank score with rank score and returns an int
     * @param rank
     * This is the rank we want to compare with
     * @return
     * Returns an int
     */
    @Override
    public int compareTo(Rank rank) {
        if (this.userScore == rank.getUserScore()){
            return 0;
        } else if (this.userScore < rank.getUserScore()){
            return 1;
        } else {
            return -1;
        }
    }

}
