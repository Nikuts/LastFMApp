package com.nikkuts.lastfmapp.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    public static final int IMAGES_COUNT = 6;
    public static final int EXTRALARGE_IMAGE_URL_INDEX = 3;
    public static final int LARGE_IMAGE_URL_INDEX = 2;

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("size")
    @Expose
    private String size;

    public String getText() {
        return text;
    }

    public String getSize() {
        return size;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(String size) {
        this.size = size;
    }
}