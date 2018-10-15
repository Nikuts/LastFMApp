package com.nikkuts.lastfmapp.gson.topalbums;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nikkuts.lastfmapp.gson.topalbums.Album;
import com.nikkuts.lastfmapp.gson.topalbums.Attr;

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
