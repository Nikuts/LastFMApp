package com.nikkuts.lastfmapp.query.listeners;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;

public interface IAlbumInfoLoadedListener {
    void onInfoLoaded(Album album);
}
