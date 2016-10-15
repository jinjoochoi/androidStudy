package com.myapplication.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mathpresso on 2016-10-15.
 */
public class RealmTrack extends RealmObject {
    private String name;
    private RealmArtist artist;
    @PrimaryKey
    private String url;
    private RealmList<RealmImage> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmArtist getArtist() {
        return artist;
    }

    public void setArtist(RealmArtist artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmList<RealmImage> getImage() {
        return image;
    }

    public void setImage(RealmList<RealmImage> image) {
        this.image = image;
    }
}
