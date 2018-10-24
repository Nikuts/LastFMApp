package com.nikkuts.lastfmapp.query;

import android.util.Log;

import com.nikkuts.lastfmapp.gson.albuminfo.AlbuminfoMsg;
import com.nikkuts.lastfmapp.gson.artist.ArtistSearchResults;
import com.nikkuts.lastfmapp.gson.topalbums.TopalbumsMsg;
import com.nikkuts.lastfmapp.query.api.ILastFmApi;
import com.nikkuts.lastfmapp.query.listeners.IAlbumInfoLoadedListener;
import com.nikkuts.lastfmapp.query.listeners.IArtistsLoadedListener;
import com.nikkuts.lastfmapp.query.listeners.IQueryErrorListener;
import com.nikkuts.lastfmapp.query.listeners.ITopAlbumsLoadedListener;
import com.nikkuts.lastfmapp.query.viewmodel.QueryViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com";
    public static final String QUERY_TOPALBUMS_METHOD = "artist.gettopalbums";
    public static final String QUERY_ALBUMINFO_METHOD = "album.getInfo";
    public static final String QUERY_ARTISTSEARCH_METHOD = "artist.search";
    public static final String QUERY_API_KEY = "1e88a3a6a8039f151b6870c55249d094";
    public static final String QUERY_FORMAT = "json";

    public ApiManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mLastFmApi = retrofit.create(ILastFmApi.class);
    }

    public void loadTopAlbums(String artist, final int page) {
        mLastFmApi.getTopAlbums(QUERY_TOPALBUMS_METHOD, QUERY_API_KEY, artist, page, QUERY_FORMAT).enqueue(new Callback<TopalbumsMsg>() {
            @Override
            public void onResponse(Call<TopalbumsMsg> call, Response<TopalbumsMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTopalbums() != null) {
                        mTopAlbumsLoadedListener.onAlbumsLoaded(response.body().getTopalbums(), page);
                    }
                    else {
                        if (mQueryErrorListener != null)
                            mQueryErrorListener.onError("Albums not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<TopalbumsMsg> call, Throwable t) {
                if (mQueryErrorListener != null)
                    mQueryErrorListener.onError("Network failure");
            }
        });
    }

    public void loadAlbumInfo(String artist, String album) {
        mLastFmApi.getAlbumInfo(QUERY_ALBUMINFO_METHOD, QUERY_API_KEY, artist, album, QUERY_FORMAT).enqueue(new Callback<AlbuminfoMsg>() {
            @Override
            public void onResponse(Call<AlbuminfoMsg> call, Response<AlbuminfoMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getAlbum() != null && mAlbumInfoLoadedListener != null) {
                        mAlbumInfoLoadedListener.onInfoLoaded(response.body().getAlbum());
                    }
                    else {
                        if (mQueryErrorListener != null)
                            mQueryErrorListener.onError("Album not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbuminfoMsg> call, Throwable t) {
                if (mQueryErrorListener != null)
                    mQueryErrorListener.onError("Network failure");
            }
        });
    }

    public void loadArtists(String artist, final int page) {
        mLastFmApi.getArtists(QUERY_ARTISTSEARCH_METHOD, QUERY_API_KEY, artist, page, QUERY_FORMAT).enqueue(new Callback<ArtistSearchResults>() {
            @Override
            public void onResponse(Call<ArtistSearchResults> call, Response<ArtistSearchResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mArtistsLoadedListener.onArtistsLoaded(response.body(), page);
                    }
                    else {
                        if (mQueryErrorListener != null)
                            mQueryErrorListener.onError("Artists not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ArtistSearchResults> call, Throwable t) {
                if (mQueryErrorListener != null)
                    mQueryErrorListener.onError("Network failure");
            }
        });
    }

    private void handleHTTPError(int code){
        if (mQueryErrorListener != null) {
            switch (code) {
                case 404:
                    Log.e(QueryViewModel.class.getName(), "Not found");
                    mQueryErrorListener.onError("Not found");
                    break;
                case 500:
                    Log.e(QueryViewModel.class.getName(), "Server broken");
                    mQueryErrorListener.onError("Server broken");
                    break;
                default:
                    Log.e(QueryViewModel.class.getName(), "Unknown error");
                    mQueryErrorListener.onError("Unknown error");
                    break;
            }
        }
    }

    public void setAlbumInfoLoadedListener(IAlbumInfoLoadedListener mAlbumInfoLoadedListener) {
        this.mAlbumInfoLoadedListener = mAlbumInfoLoadedListener;
    }

    public void setTopAlbumsLoadedListener(ITopAlbumsLoadedListener mTopAlbumsLoadedListener) {
        this.mTopAlbumsLoadedListener = mTopAlbumsLoadedListener;
    }

    public void setQueryErrorListener(IQueryErrorListener mQueryErrorListener) {
        this.mQueryErrorListener = mQueryErrorListener;
    }

    public void setArtistsLoadedListener(IArtistsLoadedListener mArtistsLoadedListener) {
        this.mArtistsLoadedListener = mArtistsLoadedListener;
    }

    public void removeAlbumInfoLoadedListener(){
        this.mAlbumInfoLoadedListener = null;
    }

    private ILastFmApi mLastFmApi;
    private IAlbumInfoLoadedListener mAlbumInfoLoadedListener;
    private ITopAlbumsLoadedListener mTopAlbumsLoadedListener;
    private IQueryErrorListener mQueryErrorListener;
    private IArtistsLoadedListener mArtistsLoadedListener;
}
