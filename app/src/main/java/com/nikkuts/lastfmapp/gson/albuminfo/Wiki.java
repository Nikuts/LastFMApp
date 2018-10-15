package com.nikkuts.lastfmapp.gson.albuminfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wiki {

    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("content")
    @Expose
    private String content;

    public String getPublished() {
        return published;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }
}
