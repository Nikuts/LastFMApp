package com.nikkuts.lastfmapp.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nikkuts.lastfmapp.gson.Topalbums;
import com.nikkuts.lastfmapp.gson.TopalbumsMsg;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryManager {

    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com";
    public static final String QUERY_TOPALBUMS_METHOD = "artist.gettopalbums";
    public static final String QUERY_API_KEY = "1e88a3a6a8039f151b6870c55249d094";
    public static final String QUERY_FORMAT = "json";

    private static volatile QueryManager mInstance = new QueryManager();

    public static QueryManager getInstance() {
        return mInstance;
    }

    private QueryManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mLastFmApi = retrofit.create(ILastFmApi.class);
    }

    public void getTopAlbums(String artist){
        mLastFmApi.getTopAlbums(QUERY_TOPALBUMS_METHOD, artist, QUERY_API_KEY, QUERY_FORMAT).enqueue(new Callback<TopalbumsMsg>() {
            @Override
            public void onResponse(Call<TopalbumsMsg> call, Response<TopalbumsMsg> response) {
                if (response.isSuccessful()) {
                    mTopAlbums = response.body().getTopalbums();
                } else {
                    switch (response.code()) {
                        case 404:
                            Log.e(QueryManager.class.getName(), "Not found");
                            break;
                        case 500:
                            Log.e(QueryManager.class.getName(), "Server broken");
                            break;
                        default:
                            Log.e(QueryManager.class.getName(), "Unknown error");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TopalbumsMsg> call, Throwable t) {
                Log.e(QueryManager.class.getName(), "Network failure");
            }
        });
    }

    private ILastFmApi mLastFmApi;
    private Topalbums mTopAlbums;
}
