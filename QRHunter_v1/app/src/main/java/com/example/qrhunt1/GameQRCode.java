package com.example.qrhunt1;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class GameQRCode {
    private int score;
    private String hashcode;
    private String comment = "";
    private String location;
    private Bitmap QRImage;


    /**
     * Initialize Game QR code class。
     */
    public GameQRCode(String hashcode){
        //comment = "A QR code on a store poster";    //testing
        score = calculateScore(hashcode);
    }

    /**
     * Needs to be done: Calculate the score of the game QR code.
     */
    private int calculateScore(String hashcode){

        return 0;
    }
    /**
     * Return the score of the game QR code.
     */
    public int getScore() {
        return score;
    }
    /**
     * Add a comment to a game QR code.
     */
    public void addComment(String theComment) {
        comment = "Comment: " + theComment;
    }
    /**
     * Delete a comment to a game QR code.
     */
    public void deleteComment(String theComment) {
        comment = "";
    }
    /**
     * return the comment of a game QR code.
     */
    public String getComments() {
        return comment;
    }

    public void setLocation(String newlocation) { location = newlocation; }

    /**
     * return the location of a game QR code.
     */
    public String getLocation() {
        return location;
    }


    public void setQRImage(Bitmap newImage) {
        QRImage = newImage;
    }
    public Bitmap getQRImage() {
        return QRImage;
    }

}
