package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.List;

public class LocalAlbumInfoActivity extends AlbumInfoActivity {

    @Override
    protected void initViewModel() {
        mAlbumsDatabaseViewModel = new AlbumsDatabaseViewModel(this.getApplication());
        mAlbumsLiveData = mAlbumsDatabaseViewModel.getAlbumsWithTracksLiveData(mArtistName, mAlbumName);
        mAlbumsLiveData.observe(this, new Observer<List<AlbumWithTracks>>() {
            @Override
            public void onChanged(@Nullable List<AlbumWithTracks> albumWithTracks) {

                mSpinner.setVisibility(View.GONE);

                Album album = AlbumFactory.createAlbumFromEntities(albumWithTracks.get(0).getAlbumInfoEntity(),
                        albumWithTracks.get(0).getTrackEntities());

                mTracksAdapter.setTracks(album.getTracks().getTrack());

                Glide.with(mMediaImage)
                        .load(album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                        .thumbnail(THUMBNAIL_SIZE)
                        .into(mMediaImage);
            }
        });
    }

    private AlbumsDatabaseViewModel mAlbumsDatabaseViewModel;
    private LiveData<List<AlbumWithTracks>> mAlbumsLiveData;
}
