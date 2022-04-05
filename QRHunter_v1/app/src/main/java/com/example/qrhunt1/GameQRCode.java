package com.example.qrhunt1;


import android.graphics.Bitmap;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class GameQRCode {
    private int score;
    private String hashcode;
    private String comment = "";
    private GeoPoint location;
    private Bitmap QRImage;


    /**
     * Initialize Game QR code class.
     * @param hashcode The hashcode of the QR code
     *
     */
    public GameQRCode(String hashcode){
        //comment = "A QR code on a store poster";    //testing
        this.hashcode = hashcode;
        this.score = calculateScore(hashcode);
    }

    /**
     * Calculate the score of the game QR code.
     * @param hashcode The hashcode of the QR code
     * @return totalScore The score of the QR
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


    public String getHashcode() {
        return hashcode;
    }

    /**
     * Return the score of the game QR code.
     * @return score The score of the QR
     */
    public int getScore() {
        return score;
    }

    /**
     * Edit the comment of a game QR code.It includes adding and deleting.
     * @param theComment The new comment that users input
     *
     */
    public void editComment(String theComment) {
        comment = theComment;
    }

    /**
     * return the comment of a game QR code.
     * @return comment The comment of the QR Code
     */
    public String getComments() {
        return comment;
    }


    /**
     * Setting the geoLocation of a QR Code
     * @param newlocation The geolocation of a QR Code
     */
    public void setLocation(GeoPoint newlocation) { location = newlocation; }

    /**
     * return the location of a game QR code.
     * @return location The geolocation of a QR Code
     */
    public GeoPoint getLocation() {
        return location;
    }


    /**
     * Set the image of a QR code
     * @param newImage
     */
    public void setQRImage(Bitmap newImage) {
        QRImage = newImage;
    }

    /**
     * Get the image of a QR code
     * @return QRImage
     */
    public Bitmap getQRImage() {
        return QRImage;
    }

}
