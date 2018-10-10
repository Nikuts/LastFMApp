package com.nikkuts.lastfmapp.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playcount")
    @Expose
    private Integer playcount;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    public String getName() {
        return name;
    }

    public Integer getPlaycount() {
        return playcount;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public Artist getArtist() {
        return artist;
    }

    public List<Image> getImage() {
        return image;
    }
}