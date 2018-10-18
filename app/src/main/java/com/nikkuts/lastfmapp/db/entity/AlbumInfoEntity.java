package com.nikkuts.lastfmapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;

@Entity
public class AlbumInfoEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "mbid")
    private String mbid;

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
        this.mbid = album.getMbid();
        this.albumName = album.getName();
        this.artistName = album.getArtist();
        this.url = album.getUrl();
        this.largeImage = album.getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText();
        this.extralargeImage = album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText();

        if (album.getWiki() != null)
            this.wiki = album.getWiki().getSummary();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
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

    public String getMbid() {

        return mbid;
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