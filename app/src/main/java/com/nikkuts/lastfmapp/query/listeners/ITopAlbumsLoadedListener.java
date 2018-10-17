package com.nikkuts.lastfmapp.query.listeners;

import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;

public interface ITopAlbumsLoadedListener {
    void onAlbumsLoaded(Topalbums topalbums, int page);
}
