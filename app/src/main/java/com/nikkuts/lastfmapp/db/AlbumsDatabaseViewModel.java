package com.nikkuts.lastfmapp.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nikkuts.lastfmapp.db.entity.TrackEntity;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.List;

public class AlbumsDatabaseViewModel extends AndroidViewModel {

    private final LiveData<List<AlbumWithTracks>> mAlbumWithTracksLiveData;

    public AlbumsDatabaseViewModel(@NonNull Application application) {
        super(application);
        mAlbumsDatabase = AlbumsDatabase.getDatabase(this.getApplication());
        mAlbumWithTracksLiveData = mAlbumsDatabase.albumDao().getAlbumWithTracks();
    }

    public LiveData<List<AlbumWithTracks>> getAlbumWithTracksLiveData() {
        return mAlbumWithTracksLiveData;
    }

    public void deleteItem(Album album) {
        new DatabaseActionAsyncTask(mAlbumsDatabase, DatabaseActionAsyncTask.Action.DELETE).execute(album);
    }

    public void insertItem(Album album) {
        new DatabaseActionAsyncTask(mAlbumsDatabase, DatabaseActionAsyncTask.Action.INSERT).execute(album);
    }

    private AlbumsDatabase mAlbumsDatabase;
}
