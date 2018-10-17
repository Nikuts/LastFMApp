package com.nikkuts.lastfmapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.nikkuts.lastfmapp.gson.albuminfo.Track;

@Entity
public class TrackEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "mbid")
    private String mbid;

    @ColumnInfo(name = "track_name")
    private String trackName;

    public TrackEntity(String mbid, String trackName){
        this.mbid = mbid;
        this.trackName = trackName;
    }

    public int getId() {
        return id;
    }

    public String getMbid() {
        return mbid;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
}
