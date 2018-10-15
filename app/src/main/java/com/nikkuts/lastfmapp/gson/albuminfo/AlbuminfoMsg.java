package com.nikkuts.lastfmapp.gson.albuminfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbuminfoMsg {
    @SerializedName("album")
    @Expose
    private Album album;

    public Album getAlbum() {
        return album;
    }
}

