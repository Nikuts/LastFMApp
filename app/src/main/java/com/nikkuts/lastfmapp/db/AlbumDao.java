package com.nikkuts.lastfmapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert
    void insert(AlbumInfoEntity album);

    @Delete
    void delete(AlbumInfoEntity album);

    @Query("SELECT * FROM AlbumInfoEntity")
    LiveData<List<AlbumInfoEntity>> getAllAlbums();
}