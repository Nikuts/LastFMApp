package com.nikkuts.lastfmapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TrackEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "album_id")
    private long albumId;

    @ColumnInfo(name = "track_name")
    private String trackName;

    public TrackEntity(long albumId, String trackName){
        this.albumId = albumId;
        this.trackName = trackName;
    }

    public long getId() {
        return id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
}
