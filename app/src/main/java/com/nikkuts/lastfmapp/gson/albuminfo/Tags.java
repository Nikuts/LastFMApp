package com.nikkuts.lastfmapp.gson.albuminfo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {

    @SerializedName("tag")
    @Expose
    private List<Tag> tag = null;

    public List<Tag> getTag() {
        return tag;
    }
}
