package com.nikkuts.lastfmapp.gson.albuminfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Streamable {

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("fulltrack")
    @Expose
    private String fulltrack;

    public String getText() {
        return text;
    }

    public String getFulltrack() {
        return fulltrack;
    }
}
