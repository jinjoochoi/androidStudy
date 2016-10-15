package com.myapplication.Realm;

import io.realm.RealmObject;

/**
 * Created by mathpresso on 2016-10-15.
 */
public class RealmImage extends RealmObject {
    private String text;
    private String size;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
