package com.example.qrhunt1;

public class UserPhoto {

    private String userName;
    private String QRImageURL;
    private String hashcode;

    /**
     * Initialize userPhoto class.
     * @param QRImageURL The string of certain photo URL
     *
     */
    public UserPhoto(String QRImageURL, String hashcode){
        this.QRImageURL = QRImageURL;
        this.hashcode = hashcode;
    }

    public String getHashcode() {
        return this.hashcode;
    }

    /**
     * This returns the name of the user
     * @return
     * Returns name of the user
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * This sets the name of the user
     * @param inputUser
     */
    public void setUserName(String inputUser){
        this.userName = inputUser;
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
