package com.okhttp.Model;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by choijinjoo on 2016. 3. 13..
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class ResultTrack {
    @JsonProperty("trackmatches")
    TrackMatches trackMatches;

    public TrackMatches getTrackMatches() {
        return trackMatches;
    }

    public void setTrackMatches(TrackMatches trackMatches) {
        this.trackMatches = trackMatches;
    }
}
