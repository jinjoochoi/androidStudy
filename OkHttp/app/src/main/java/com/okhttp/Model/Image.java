package com.okhttp.Model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class Image {
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
