package com.example.taufic.bikeapps;

/**
 * Created by Asus on 2/25/2017.
 */

public class Event {
    String communityId;
    String name;
    String location;
    String description;
    String date;

    public Event() {
        communityId = "";
        name = "";
        location= "";
        description= "";
        date= "";
    }

    public Event(String name, String communityId, String location, String description, String date) {
        this.name = name;
        this.communityId = communityId;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
