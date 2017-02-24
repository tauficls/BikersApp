package com.example.taufic.bikeapps;

/**
 * Created by taufic on 2/25/2017.
 */

public class Community {
    String name;
    String description;
    String location;
    String owner;

    public Community() {
        this.name ="";
        this.description = "";
        this.location = "";
        this.owner = "";
    }

    public Community(String name, String description, String location, String owner) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
