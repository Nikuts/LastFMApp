package com.nikkuts.lastfmapp.gson.albuminfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nikkuts.lastfmapp.gson.Artist;
import com.nikkuts.lastfmapp.gson.topalbums.Attr;

public class Track {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("@attr")
    @Expose
    private Attr attr;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("artist")
    @Expose
    private Artist artist;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }

    public Attr getAttr() {
        return attr;
    }

    public Streamable getStreamable() {
        return streamable;
    }

    public Artist getArtist() {
        return artist;
    }
}
