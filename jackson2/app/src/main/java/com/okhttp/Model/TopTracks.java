package com.okhttp.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class TopTracks {
    @JsonProperty("track")
    ArrayList<Track> track;

    public ArrayList<Track> getTrackList() {
        return track;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.track = trackList;
    }
}
