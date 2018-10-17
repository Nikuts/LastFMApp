package com.nikkuts.lastfmapp.query;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.api.ILastFmApi;
import com.nikkuts.lastfmapp.query.listeners.IAlbumInfoLoadedListener;
import com.nikkuts.lastfmapp.query.listeners.IQueryErrorListener;
import com.nikkuts.lastfmapp.query.listeners.ITopAlbumsLoadedListener;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryViewModel extends ViewModel implements IAlbumInfoLoadedListener, IQueryErrorListener, ITopAlbumsLoadedListener {

    public QueryViewModel() {
        mApiManager = new ApiManager();
        mApiManager.setAlbumInfoLoadedListener(this);
        mApiManager.setQueryErrorListener(this);
        mApiManager.setTopAlbumsLoadedListener(this);
    }

    public void loadTopAlbums(String artist, int page){
        mApiManager.loadTopAlbums(artist, page);
    }

    public void loadAlbumInfo(String artist, String album) {
        mApiManager.loadAlbumInfo(artist, album);
    }

    public LiveData<String> getHTTPErrorLiveData() {
        if (mQueryError == null) {
            mQueryError = new MutableLiveData<>();
        }
        return mQueryError;
    }

    public LiveData<Topalbums> getTopAlbumsLiveData(){
        if (mTopAlbums == null) {
            mTopAlbums = new MutableLiveData<>();
        }
        return mTopAlbums;
    }

    public LiveData<Album> getAlbumInfoLiveData() {
        if (mAlbumInfo == null) {
            mAlbumInfo = new MutableLiveData<>();
        }
        return mAlbumInfo;
    }

    @Override
    public void onInfoLoaded(Album album) {
        mAlbumInfo.postValue(album);
    }

    @Override
    public void onError(String errorMsg) {
        mQueryError.postValue(errorMsg);
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

    private ApiManager mApiManager;

    private MutableLiveData<String> mQueryError;
    private MutableLiveData<Album> mAlbumInfo;
    private MutableLiveData<Topalbums> mTopAlbums;


}
