package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

public class LocalAlbumInfoActivity extends AlbumInfoActivity {

    @Override
    protected void initViewModel() {
        mAlbumsDatabaseViewModel = new AlbumsDatabaseViewModel(this.getApplication());
        mAlbumLiveData = mAlbumsDatabaseViewModel.getAlbumWithTracksLiveData(mArtistName, mAlbumName);
        mAlbumLiveData.observe(this, albumWithTracks -> {

            mSpinner.setVisibility(View.GONE);

            Album album = AlbumFactory.createAlbumFromEntities(albumWithTracks.getAlbumInfoEntity(),
                    albumWithTracks.getTrackEntities());

            mPrimaryText.setText(album.getName());
            mSubText.setText(album.getArtist());
            mTracksAdapter.setTracks(album.getTracks().getTrack());

            Glide.with(mMediaImage)
                    .load(album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(mMediaImage);
        });
    }

    private AlbumsDatabaseViewModel mAlbumsDatabaseViewModel;
    private LiveData<AlbumWithTracks> mAlbumLiveData;
}
