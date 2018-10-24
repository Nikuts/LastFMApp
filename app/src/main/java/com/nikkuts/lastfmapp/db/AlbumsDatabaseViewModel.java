package com.nikkuts.lastfmapp.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.List;

public class AlbumsDatabaseViewModel extends AndroidViewModel {


    public AlbumsDatabaseViewModel(@NonNull Application application) {
        super(application);
        mAlbumsDatabase = AlbumsDatabase.getDatabase(this.getApplication());
    }

    public MutableLiveData<List<AlbumWithTracks>> getAllAlbumsWithTracksLiveData(LifecycleOwner owner) {
        MutableLiveData<List<AlbumWithTracks>> allAlbumsWithTracksMutable = new MutableLiveData<>();
        mAlbumsDatabase.albumDao().getAllAlbumsWithTracks().observe(owner, albumWithTracks -> {
            allAlbumsWithTracksMutable.postValue(albumWithTracks);
        });
        return allAlbumsWithTracksMutable;
    }

    public MutableLiveData<AlbumWithTracks> getAlbumWithTracksLiveData(LifecycleOwner owner, String artist, String album) {
        MutableLiveData<AlbumWithTracks> albumWithTracksMutable = new MutableLiveData<>();
        mAlbumsDatabase.albumDao().getAlbumWithTracks(artist, album).observe(owner, albumWithTracks -> {
            albumWithTracksMutable.postValue(albumWithTracks);
        });
        return albumWithTracksMutable;
    }

    public MutableLiveData<List<AlbumWithTracks>> getAlbumsWithTracksLiveDataByArtist(LifecycleOwner owner, String artist) {
        MutableLiveData<List<AlbumWithTracks>> albumsWithTracksMutableByArtist = new MutableLiveData<>();
        mAlbumsDatabase.albumDao().getAlbumsWithTracksByArtist(artist).observe(owner, albumWithTracks -> {
            albumsWithTracksMutableByArtist.postValue(albumWithTracks);
        });
        return albumsWithTracksMutableByArtist;
    }

    public void deleteItem(Album album) {
        new DatabaseActionAsyncTask(mAlbumsDatabase, DatabaseActionAsyncTask.Action.DELETE).execute(album);
    }

    public void insertItem(Album album) {
        new DatabaseActionAsyncTask(mAlbumsDatabase, DatabaseActionAsyncTask.Action.INSERT).execute(album);
    }

    public void updateItem(Album album) {
        new DatabaseActionAsyncTask(mAlbumsDatabase, DatabaseActionAsyncTask.Action.UPDATE).execute(album);
    }

    private AlbumsDatabase mAlbumsDatabase;
}
