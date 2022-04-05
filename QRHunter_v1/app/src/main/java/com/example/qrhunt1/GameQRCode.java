package com.example.qrhunt1;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

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
        this.score = calculateScore(hashcode);
    }

    /**
     * Needs to be done: Calculate the score of the game QR code.
     */
    private int calculateScore(String hashcode){
        //get a list with repeated digits strings
        ArrayList<String> repeatedDigitsList = new ArrayList<>();
        for (int i=0; i<hashcode.length();i++){
            int n = i;
            String temp = "";
            char repeatedDigit = 'n';
            for (int m=i+1; m<hashcode.length();m++){
                if (hashcode.charAt(n) == hashcode.charAt(m) && n == m-1){
                    repeatedDigit = hashcode.charAt(m);
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
        return totalScore;
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
    public void editComment(String theComment) {
        comment = theComment;
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
