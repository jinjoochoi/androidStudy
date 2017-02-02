package com.myapplication.Model;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class TopTracks {
    ArrayList<RealmTrack> track;

    public ArrayList<RealmTrack> getTrackList() {
        return track;
    }

    public void setTrackList(ArrayList<RealmTrack> trackList) {
        this.track = trackList;
    }
}
