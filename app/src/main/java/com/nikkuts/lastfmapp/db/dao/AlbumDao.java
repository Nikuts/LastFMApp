package com.nikkuts.lastfmapp.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;

import java.util.List;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AlbumInfoEntity album);

    @Delete
    void delete(AlbumInfoEntity album);

    @Query("SELECT * from AlbumInfoEntity")
    LiveData<List<AlbumWithTracks>> getAllAlbumsWithTracks();

    @Query("SELECT * from AlbumInfoEntity WHERE artist_name = :artistName AND album_name = :albumName")
    List<AlbumInfoEntity> getAlbums(String artistName, String albumName);

    @Query("SELECT * from AlbumInfoEntity WHERE artist_name = :artistName AND album_name = :albumName")
    LiveData<List<AlbumWithTracks>> getAlbumsWithTracks(String artistName, String albumName);
}