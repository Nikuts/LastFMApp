package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.query.viewmodel.AlbumInfoViewModel;

public class RemoteAlbumInfoActivity extends AlbumInfoActivity {

    @Override
    protected void initViewModel() {
        mAlbumInfoViewModel = ViewModelProviders.of(this).get(AlbumInfoViewModel.class);
        mAlbumInfo = mAlbumInfoViewModel.getAlbumInfoLiveData();
        mAlbumInfo.observe(this, album -> {

            mPrimaryText.setText(album.getName());
            mSubText.setText(album.getArtist());

            mSpinner.setVisibility(View.GONE);

            mTracksAdapter.setTracks(album.getTracks().getTrack());

            Glide.with(mMediaImage)
                    .load(album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(mMediaImage);
        });

        mQueryError = mAlbumInfoViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, error -> {
            mSpinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        });

        mAlbumInfoViewModel.loadAlbumInfo(mArtistName, mAlbumName);
    }

    private AlbumInfoViewModel mAlbumInfoViewModel;
    private LiveData<Album> mAlbumInfo;
    private LiveData<String> mQueryError;
}
