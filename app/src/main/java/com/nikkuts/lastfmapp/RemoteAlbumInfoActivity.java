package com.nikkuts.lastfmapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nikkuts.lastfmapp.glide.GlideApp;
import com.nikkuts.lastfmapp.gson.Image;
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

            GlideApp.with(mMediaImage)
                    .load(mCurrentAlbum.getImage().get(Image.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .placeholder(R.drawable.ic_placeholder_gray_24dp)
                    .error(R.drawable.ic_placeholder_gray_24dp)
                    .fallback(R.drawable.ic_placeholder_gray_24dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
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
