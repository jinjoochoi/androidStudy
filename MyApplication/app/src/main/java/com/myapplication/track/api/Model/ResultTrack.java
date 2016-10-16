package com.myapplication.track.api.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */
public class ResultTrack {
    @SerializedName("trackmatches")
    TrackMatches trackMatches;

    public TrackMatches getTrackMatches() {
        return trackMatches;
    }

    public void setTrackMatches(TrackMatches trackMatches) {
        this.trackMatches = trackMatches;
    }
}
