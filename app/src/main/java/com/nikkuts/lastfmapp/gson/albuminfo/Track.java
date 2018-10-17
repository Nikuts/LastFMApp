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

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
