package com.nikkuts.lastfmapp.gson.albuminfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nikkuts.lastfmapp.gson.Image;

public class Album {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("tracks")
    @Expose
    private Tracks tracks;
    @SerializedName("tags")
    @Expose
    private Tags tags;
    @SerializedName("wiki")
    @Expose
    private Wiki wiki;

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public List<Image> getImage() {
        return image;
    }

    public String getListeners() {
        return listeners;
    }

    public String getPlaycount() {
        return playcount;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public Tags getTags() {
        return tags;
    }

    public Wiki getWiki() {
        return wiki;
    }
}
