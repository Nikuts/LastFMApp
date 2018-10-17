package com.nikkuts.lastfmapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikkuts.lastfmapp.db.entity.TrackEntity;

import java.util.List;

@Dao
public interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackEntity track);

    @Query("DELETE FROM TrackEntity WHERE mbid = :mbid")
    void deleteAllTracksByMbid(String mbid);

    @Query("SELECT * FROM TrackEntity WHERE mbid = :mbid")
    List<TrackEntity> getAllTracksByMbid(String mbid);
}
