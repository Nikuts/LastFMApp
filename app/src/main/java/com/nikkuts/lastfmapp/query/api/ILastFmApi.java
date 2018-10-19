package com.nikkuts.lastfmapp.query.api;

import com.nikkuts.lastfmapp.gson.albuminfo.AlbuminfoMsg;
import com.nikkuts.lastfmapp.gson.artist.ArtistSearchResults;
import com.nikkuts.lastfmapp.gson.topalbums.TopalbumsMsg;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ILastFmApi {
    @GET("/2.0/")
    Call<ArtistSearchResults> getArtists(@Query("method") String method,
                                         @Query("api_key") String apiKey,
                                         @Query("artist") String artist,
                                         @Query("page")  int page,
                                         @Query("format") String format);

    @GET("/2.0/")
    Call<TopalbumsMsg> getTopAlbums(@Query("method") String method,
                                    @Query("api_key") String apiKey,
                                    @Query("artist") String artist,
                                    @Query("page")  int page,
                                    @Query("format") String format);

    @GET("/2.0/")
    Call<AlbuminfoMsg> getAlbumInfo(@Query("method") String method,
                                    @Query("api_key") String apiKey,
                                    @Query("artist") String artist,
                                    @Query("album")  String album,
                                    @Query("format") String format);
}
