package com.nikkuts.lastfmapp.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nikkuts.lastfmapp.gson.Topalbums;
import com.nikkuts.lastfmapp.gson.TopalbumsMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryViewModel extends ViewModel {

    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com";
    public static final String QUERY_TOPALBUMS_METHOD = "artist.gettopalbums";
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

    public void loadTopAlbums(String artist, final int page) {
        mLastFmApi.getTopAlbums(QUERY_TOPALBUMS_METHOD, artist, page, QUERY_API_KEY, QUERY_FORMAT).enqueue(new Callback<TopalbumsMsg>() {
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
    private ILastFmApi mLastFmApi;
}
