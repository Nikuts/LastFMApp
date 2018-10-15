package com.nikkuts.lastfmapp.gson.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr {

    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("perPage")
    @Expose
    private String perPage;
    @SerializedName("totalPages")
    @Expose
    private String totalPages;
    @SerializedName("total")
    @Expose
    private String total;

    public String getArtist() {
        return artist;
    }

    public String getPage() {
        return page;
    }

    public String getPerPage() {
        return perPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public String getTotal() {
        return total;
    }
}
