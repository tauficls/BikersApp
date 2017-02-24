package com.example.taufic.bikeapps;


public class User {

    public String username;
    public String description;
    public String photoURL;

    public User() {
        username = "";
        description = "";
        photoURL = "";
    }

    public User(String username, String description, String photoURL) {
        this.username = username;
        this.description = description;
        this.photoURL = photoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

}