package com.nikkuts.lastfmapp.query.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nikkuts.lastfmapp.gson.artist.ArtistSearchResults;
import com.nikkuts.lastfmapp.query.listeners.IArtistsLoadedListener;

public class ArtistsViewModel extends QueryViewModel implements IArtistsLoadedListener {
    @Override
    public void setListener() {
        mApiManager.setArtistsLoadedListener(this);
    }

    public void loadArtists(String artist, int page) {
        mApiManager.loadArtists(artist, page);
    }

    public LiveData<ArtistSearchResults> getArtistSearchResultsLiveData() {
        if (mArtistSearchResults == null) {
            mArtistSearchResults = new MutableLiveData<>();
        }
        return mArtistSearchResults;
    }

    @Override
    public void onArtistsLoaded(ArtistSearchResults artistSearchResults, int page) {
        if (page > 1) {
            mArtistSearchResults.getValue().getResults().getArtistmatches().getArtist().addAll(
                    artistSearchResults.getResults().getArtistmatches().getArtist());

            mArtistSearchResults.postValue(mArtistSearchResults.getValue());
        }
        else
            mArtistSearchResults.postValue(artistSearchResults);
    }

    private MutableLiveData<ArtistSearchResults> mArtistSearchResults;
}
