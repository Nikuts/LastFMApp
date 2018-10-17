package com.nikkuts.lastfmapp.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AlbumInfoEntity album);

    @Delete
    void delete(AlbumInfoEntity album);

    @Query("SELECT * FROM AlbumInfoEntity")
    LiveData<List<AlbumInfoEntity>> getAllAlbums();
}