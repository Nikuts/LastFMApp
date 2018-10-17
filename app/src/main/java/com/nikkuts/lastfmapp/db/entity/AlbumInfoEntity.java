package com.nikkuts.lastfmapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import com.nikkuts.lastfmapp.gson.Image;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;
import com.nikkuts.lastfmapp.gson.albuminfo.Tracks;
import com.nikkuts.lastfmapp.gson.albuminfo.Wiki;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AlbumInfoEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "album_name")
    private String albumName;

    @ColumnInfo(name = "artist_name")
    private String artistName;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "large_image")
    private String largeImage;

    @ColumnInfo(name = "extralarge_image")
    private String extralargeImage;

    @ColumnInfo(name = "wiki")
    private String wiki;

    public AlbumInfoEntity() {
    }

    public AlbumInfoEntity(Album album) {
        this.id = album.getMbid();
        this.albumName = album.getName();
        this.artistName = album.getArtist();
        this.url = album.getUrl();
        this.largeImage = album.getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText();
        this.extralargeImage = album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText();

        if (album.getWiki() != null)
            this.wiki = album.getWiki().getSummary();
    }

    public void setId(String mbid) {
        this.id = mbid;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void setExtralargeImage(String extralargeImage) {
        this.extralargeImage = extralargeImage;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getId() {

        return id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getUrl() {
        return url;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getExtralargeImage() {
        return extralargeImage;
    }

    public String getWiki() {
        return wiki;
    }
}