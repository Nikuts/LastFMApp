package com.nikkuts.lastfmapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TrackDao {
    @Insert
    void insertAll(TrackEntity track);

    @Query("DELETE FROM TrackEntity WHERE mbid = :mbid")
    void deleteAllTracksByMbid(String mbid);

    @Query("SELECT * FROM TrackEntity WHERE mbid = :mbid")
    List<TrackEntity> getAllTracksByMbid(String mbid);
}
