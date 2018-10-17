package com.nikkuts.lastfmapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nikkuts.lastfmapp.db.dao.AlbumDao;
import com.nikkuts.lastfmapp.db.dao.TrackDao;
import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.db.entity.TrackEntity;

@Database(entities = {AlbumInfoEntity.class, TrackEntity.class}, version = 3)
public abstract class AlbumsDatabase extends RoomDatabase {
    private static final String DB_NAME = "albums.db";
    private static AlbumsDatabase INSTANCE;

    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();

    public static AlbumsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlbumsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlbumsDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}