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
