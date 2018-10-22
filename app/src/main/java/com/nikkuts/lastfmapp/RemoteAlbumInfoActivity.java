package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.query.viewmodel.AlbumInfoViewModel;

public class RemoteAlbumInfoActivity extends AlbumInfoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViewModel() {
        mAlbumInfoViewModel = ViewModelProviders.of(this).get(AlbumInfoViewModel.class);
        mAlbumInfo = mAlbumInfoViewModel.getAlbumInfoLiveData();
        mAlbumInfo.observe(this, album -> {

            mCurrentAlbum = album;

            mToolbar.setTitle(mCurrentAlbum.getName());

            mTitle.setText(mCurrentAlbum.getName());
            mSubtitle.setText(mCurrentAlbum.getArtist());

            mSpinner.setVisibility(View.GONE);

            mTracksAdapter.setTracks(mCurrentAlbum.getTracks().getTrack());

            Glide.with(mMediaImage)
                    .load(mCurrentAlbum.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(mMediaImage);

            mAlbumInfo.removeObservers(this);
        });

        mQueryError = mAlbumInfoViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, error -> {
            mSpinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        });

        mAlbumInfoViewModel.loadAlbumInfo(mArtistName, mAlbumName);
    }

    @Override
    protected void setIsLocal() {
        mIsLocal = false;
    }

    private AlbumInfoViewModel mAlbumInfoViewModel;
    private MutableLiveData<Album> mAlbumInfo;
    private MutableLiveData<String> mQueryError;
}
