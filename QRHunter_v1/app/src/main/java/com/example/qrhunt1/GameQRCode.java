package com.example.qrhunt1;


import com.google.firebase.firestore.GeoPoint;


public class GameQRCode {
    private String score;
    private String hashcode;
    private String comment = "";
    private GeoPoint location;
    private String QRImageURL;


    /**
     * Initialize Game QR code class.
     * @param hashcode The hashcode of the QR code
     *
     */
    public GameQRCode(String hashcode){
        //comment = "A QR code on a store poster";    //testing
        this.hashcode = hashcode;
    }

    /*
    /**
     * Calculate the score of the game QR code.
     * @param hashcode The hashcode of the QR code
     * @return totalScore The score of the QR
     */
    /*
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

     */


    public String getHashcode() {
        return this.hashcode;
    }

    /**
     * Return the score of the game QR code.
     * @return score The score of the QR
     */
    public String getScore() {
        return this.score;
    }

    public void setScore(String Ascore) {
        this.score = Ascore;
    }

    /**
     * Edit the comment of a game QR code.It includes adding and deleting.
     * @param theComment The new comment that users input
     *
     */
    public void editComment(String theComment) {
        this.comment = theComment;
    }

    /**
     * return the comment of a game QR code.
     * @return comment The comment of the QR Code
     */
    public String getComments() {
        return this.comment;
    }


    /**
     * Setting the geoLocation of a QR Code
     * @param newlocation The geolocation of a QR Code
     */
    public void setLocation(GeoPoint newlocation) { this.location = newlocation; }

    /**
     * return the location of a game QR code.
     * @return location The geolocation of a QR Code
     */
    public GeoPoint getLocation() {
        return this.location;
    }


    /**
     * Set the URL of a QR code
     * @param url
     */
    public void setURL(String url) {
        this.QRImageURL = url;

    }

    /**
     * Get the URL of a QR code
     * @return QRImageURL
     */
    public String getURL() {
        return this.QRImageURL;

    }





}
