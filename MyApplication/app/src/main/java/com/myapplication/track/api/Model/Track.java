package com.myapplication.track.api.Model;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class Track {
    String name;
    Artist artist;
    ArrayList<Image> image;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
