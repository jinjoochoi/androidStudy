package com.myapplication.Model;


import java.util.ArrayList;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */
public abstract class TrackResponse {
    public TrackResponse() {
    }

    abstract public ArrayList<Track> getTrackList();
}
