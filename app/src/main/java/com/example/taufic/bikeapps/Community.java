package com.example.taufic.bikeapps;

/**
 * Created by taufic on 2/25/2017.
 */

public class Community {
    String name;
    String description;
    String location;
    String owner;
    String id;

    public Community() {
        this.name ="";
        this.description = "";
        this.location = "";
        this.owner = "";
    }

    public Community(String name, String description, String location, String owner, String id) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.owner = owner;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
