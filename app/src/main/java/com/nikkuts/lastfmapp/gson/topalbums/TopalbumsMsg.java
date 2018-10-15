package com.nikkuts.lastfmapp.gson.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopalbumsMsg {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    public Topalbums getTopalbums() {
        return topalbums;
    }
}
