package com.nikkuts.lastfmapp.gson.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<Album> album = null;
    @SerializedName("@attr")
    @Expose
    private Attr attr;

    public List<Album> getAlbum() {
        return album;
    }

    public Attr getAttr() {
        return attr;
    }
}
