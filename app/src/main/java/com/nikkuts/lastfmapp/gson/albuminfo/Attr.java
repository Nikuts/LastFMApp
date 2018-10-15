package com.nikkuts.lastfmapp.gson.albuminfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr {

    @SerializedName("rank")
    @Expose
    private String rank;

    public String getRank() {
        return rank;
    }
}
