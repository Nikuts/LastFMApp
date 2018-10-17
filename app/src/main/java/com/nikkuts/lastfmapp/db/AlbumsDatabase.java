package com.nikkuts.lastfmapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {AlbumInfoEntity.class, TrackEntity.class}, version = 1)
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
                            AlbumsDatabase.class, DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}