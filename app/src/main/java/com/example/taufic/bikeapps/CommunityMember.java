package com.example.taufic.bikeapps;

import java.util.ArrayList;

/**
 * Created by taufic on 2/25/2017.
 */

public class CommunityMember {
    private ArrayList<String> UID;

    public CommunityMember() {
        UID = new ArrayList<String>();
    }

    public CommunityMember(ArrayList<String> UID) {
        this.UID = UID;
    }

    public ArrayList<String> getUID() {
        return UID;
    }

    public void setUID(ArrayList<String> UID) {
        this.UID = UID;
    }

    public void addUID(String id) {
        UID.add(id);
    }
}
