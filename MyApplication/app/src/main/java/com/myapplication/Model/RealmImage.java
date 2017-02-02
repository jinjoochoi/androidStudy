package com.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class RealmImage extends RealmObject{
    @SerializedName("#text")
    String text;
    String size;

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
