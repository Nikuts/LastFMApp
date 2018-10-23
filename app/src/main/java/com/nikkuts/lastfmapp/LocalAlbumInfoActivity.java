package com.nikkuts.lastfmapp;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.glide.GlideApp;
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

            GlideApp.with(mMediaImage)
                    .load(mCurrentAlbum.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .placeholder(R.drawable.ic_placeholder_gray_24dp)
                    .error(R.drawable.ic_placeholder_gray_24dp)
                    .fallback(R.drawable.ic_placeholder_gray_24dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
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
