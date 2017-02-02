package com.myapplication.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class RealmTrack extends RealmObject{
    String name;
    Artist artist;
    RealmList<RealmImage> image;
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

    public RealmList<RealmImage> getImage() {
        return image;
    }

    public void setImage(RealmList<RealmImage> image) {
        this.image = image;
    }
}
