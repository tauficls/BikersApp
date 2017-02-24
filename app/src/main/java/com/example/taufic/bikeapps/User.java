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

    public String getUsername () {
        return username;
    }

    public String getDescription() {
        return description;
    }
}