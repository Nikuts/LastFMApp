package com.nikkuts.lastfmapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikkuts.lastfmapp.db.entity.TrackEntity;

@Dao
public interface TrackDao {
    @Insert
    void insert(TrackEntity track);

    @Query("DELETE FROM TrackEntity WHERE album_id = :albumId")
    void deleteAllTracksByAlbumId(long albumId);

}
