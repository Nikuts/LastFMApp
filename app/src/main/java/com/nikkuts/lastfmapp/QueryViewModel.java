package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nikkuts.lastfmapp.api.ILastFmApi;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.AlbuminfoMsg;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.gson.topalbums.TopalbumsMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryViewModel extends ViewModel {

    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com";
    public static final String QUERY_TOPALBUMS_METHOD = "artist.gettopalbums";
    public static final String QUERY_ALBUMINFO_METHOD = "album.getInfo";
    public static final String QUERY_API_KEY = "1e88a3a6a8039f151b6870c55249d094";
    public static final String QUERY_FORMAT = "json";

    public QueryViewModel() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mLastFmApi = retrofit.create(ILastFmApi.class);
    }

    public LiveData<Topalbums> getTopAlbumsLiveData(){
        if (mTopAlbums == null) {
            mTopAlbums = new MutableLiveData<>();
        }
        return mTopAlbums;
    }

    public LiveData<String> getHTTPErrorLiveData() {
        if (mQueryError == null) {
            mQueryError = new MutableLiveData<>();
        }
        return mQueryError;
    }

    public LiveData<Album> getAlbumInfoLiveData() {
        if (mAlbumInfo == null) {
            mAlbumInfo = new MutableLiveData<>();
        }
        return mAlbumInfo;
    }

    public void loadTopAlbums(String artist, final int page) {
        mLastFmApi.getTopAlbums(QUERY_TOPALBUMS_METHOD, QUERY_API_KEY, artist, page, QUERY_FORMAT).enqueue(new Callback<TopalbumsMsg>() {
            @Override
            public void onResponse(Call<TopalbumsMsg> call, Response<TopalbumsMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTopalbums() != null) {
                        if (page > 1) {
                            mTopAlbums.getValue().getAlbum().addAll(response.body().getTopalbums().getAlbum());
                            mTopAlbums.postValue(mTopAlbums.getValue());
                        }
                        else
                            mTopAlbums.postValue(response.body().getTopalbums());
                    }
                    else {
                        mQueryError.postValue("Artist not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<TopalbumsMsg> call, Throwable t) {
                mQueryError.postValue("Network failure");
                Log.e(QueryViewModel.class.getName(), "Network failure");
            }
        });
    }

    public void loadAlbumInfo(String artist, String album){
        mLastFmApi.getAlbumInfo(QUERY_ALBUMINFO_METHOD, QUERY_API_KEY, artist, album, QUERY_FORMAT).enqueue(new Callback<AlbuminfoMsg>() {
            @Override
            public void onResponse(Call<AlbuminfoMsg> call, Response<AlbuminfoMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getAlbum() != null) {
                        mAlbumInfo.postValue(response.body().getAlbum());
                    }
                    else {
                        mQueryError.postValue("Album not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbuminfoMsg> call, Throwable t) {
                mQueryError.postValue("Network failure");
                Log.e(QueryViewModel.class.getName(), "Network failure");
            }
        });
    }

    private void handleHTTPError(int code){
        switch (code) {
            case 404:
                Log.e(QueryViewModel.class.getName(), "Not found");
                mQueryError.postValue("Not found");
                break;
            case 500:
                Log.e(QueryViewModel.class.getName(), "Server broken");
                mQueryError.postValue("Server broken");
                break;
            default:
                Log.e(QueryViewModel.class.getName(), "Unknown error");
                mQueryError.postValue("Unknown error");
                break;
        }
    }

    private MutableLiveData<Topalbums> mTopAlbums;
    private MutableLiveData<String> mQueryError;
    private MutableLiveData<Album> mAlbumInfo;
    private ILastFmApi mLastFmApi;
}
