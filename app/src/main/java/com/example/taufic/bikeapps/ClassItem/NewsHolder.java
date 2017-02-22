package com.example.taufic.bikeapps.ClassItem;

import static android.R.attr.x;

/**
 * Created by taufic on 2/21/2017.
 */

public class NewsHolder {
    private String title;
    private String description;
    //private String image;
    //private String web;

    public NewsHolder(String title, String description, String image, String web) {
        this.title = title;
        this.description = description;
        //this.image = image;
        //this.web = web;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getImage() {
//        return image;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getWeb() {
//        return web;
//    }
//
//    public void setWeb(String web) {
//        this.web = web;
//    }

}
