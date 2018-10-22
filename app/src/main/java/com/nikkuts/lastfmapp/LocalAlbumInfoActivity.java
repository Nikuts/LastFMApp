package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

public class LocalAlbumInfoActivity extends AlbumInfoActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViewModel() {
        mAlbumsDatabaseViewModel = new AlbumsDatabaseViewModel(this.getApplication());
        mAlbumLiveData = mAlbumsDatabaseViewModel.getAlbumWithTracksLiveData(this, mArtistName, mAlbumName);
        mAlbumLiveData.observe(this, albumWithTracks -> {

            mSpinner.setVisibility(View.GONE);

            mCurrentAlbum = AlbumFactory.createAlbumFromEntities(albumWithTracks.getAlbumInfoEntity(),
                    albumWithTracks.getTrackEntities());

            mToolbar.setTitle(mCurrentAlbum.getName());

            mTitle.setText(mCurrentAlbum.getName());
            mSubtitle.setText(mCurrentAlbum.getArtist());

            mTracksAdapter.setTracks(mCurrentAlbum.getTracks().getTrack());

            Glide.with(mMediaImage)
                    .load(mCurrentAlbum.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(mMediaImage);

            mAlbumLiveData.removeObservers(this);
        });
    }

    @Override
    protected void setIsLocal() {
        mIsLocal = true;
    }

    private AlbumsDatabaseViewModel mAlbumsDatabaseViewModel;
    private MutableLiveData<AlbumWithTracks> mAlbumLiveData;

}
