package com.nikkuts.lastfmapp.gson.albuminfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tracks {

    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public List<Track> getTrack() {
        return track;
    }
}