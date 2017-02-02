package com.myapplication.Model;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */
public class Track2 {
    String name;
    String artist;
    ArrayList<RealmImage> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ArrayList<RealmImage> getImage() {
        return image;
    }

    public void setImage(ArrayList<RealmImage> image) {
        this.image = image;
    }
}
