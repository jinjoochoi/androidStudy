package com.okhttp.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class Track {
    String name;
    Artist artist;
    ArrayList<Image> image;
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
}
