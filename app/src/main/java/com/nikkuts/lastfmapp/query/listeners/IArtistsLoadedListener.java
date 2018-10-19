package com.nikkuts.lastfmapp.query.listeners;

import com.nikkuts.lastfmapp.gson.artist.ArtistSearchResults;

public interface IArtistsLoadedListener {
    void onArtistsLoaded(ArtistSearchResults artistSearchResults, int page);
}
