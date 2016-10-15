package com.myapplication.Realm;

import io.realm.RealmObject;

/**
 * Created by mathpresso on 2016-10-15.
 */
public class RealmArtist extends RealmObject{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
