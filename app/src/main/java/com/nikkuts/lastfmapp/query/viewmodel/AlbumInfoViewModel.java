package com.nikkuts.lastfmapp.query.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.query.listeners.IAlbumInfoLoadedListener;

public class AlbumInfoViewModel extends QueryViewModel implements IAlbumInfoLoadedListener {

    public LiveData<Album> getAlbumInfoLiveData() {
        if (mAlbumInfo == null) {
            mAlbumInfo = new MutableLiveData<>();
        }
        return mAlbumInfo;
    }

    public void loadAlbumInfo(String artist, String album) {
        mApiManager.loadAlbumInfo(artist, album);
    }

    @Override
    public void setListener() {
        mApiManager.setAlbumInfoLoadedListener(this);
    }

    @Override
    public void onInfoLoaded(Album album) {
        mAlbumInfo.postValue(album);
    }

    private MutableLiveData<Album> mAlbumInfo;
}
