package com.okhttp.Model;



import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class Image {
    @JsonProperty("#text")
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
