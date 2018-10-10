package com.nikkuts.lastfmapp.api;

import com.nikkuts.lastfmapp.gson.TopalbumsMsg;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ILastFmApi {
    @GET("/2.0/")
    Call<TopalbumsMsg> getTopAlbums(@Query("method") String method,
                                    @Query("artist") String artist,
                                    @Query("api_key") String apiKey,
                                    @Query("format") String format);
}
