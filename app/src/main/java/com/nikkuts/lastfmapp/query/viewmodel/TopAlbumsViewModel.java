package com.nikkuts.lastfmapp.query.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.listeners.ITopAlbumsLoadedListener;

public class TopAlbumsViewModel extends QueryViewModel implements ITopAlbumsLoadedListener {

    @Override
    public void setListener() {
        mApiManager.setTopAlbumsLoadedListener(this);
    }

    public void loadTopAlbums(String artist, int page){
        mApiManager.loadTopAlbums(artist, page);
    }

    public MutableLiveData<Topalbums> getTopAlbumsLiveData(){
        if (mTopAlbums == null) {
            mTopAlbums = new MutableLiveData<>();
        }
        return mTopAlbums;
    }

    @Override
    public void onAlbumsLoaded(Topalbums topalbums, int page) {
        if (page > 1) {
            mTopAlbums.getValue().getAlbum().addAll(topalbums.getAlbum());
            mTopAlbums.postValue(mTopAlbums.getValue());
        }
        else
            mTopAlbums.postValue(topalbums);
    }

    private MutableLiveData<Topalbums> mTopAlbums;
}
