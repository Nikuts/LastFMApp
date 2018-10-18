package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.query.QueryViewModel;

public class RemoteAlbumInfoActivity extends AlbumInfoActivity {

    @Override
    protected void initViewModel() {
        mQueryViewModel = ViewModelProviders.of(this).get(QueryViewModel.class);
        mAlbumInfo = mQueryViewModel.getAlbumInfoLiveData();
        mAlbumInfo.observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {
                mPrimaryText.setText(album.getName());
                mSubText.setText(album.getArtist());

                mSpinner.setVisibility(View.GONE);

                mTracksAdapter.setTracks(album.getTracks().getTrack());

                Glide.with(mMediaImage)
                        .load(album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                        .thumbnail(THUMBNAIL_SIZE)
                        .into(mMediaImage);
            }
        });

        mQueryError = mQueryViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                mSpinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        mQueryViewModel.loadAlbumInfo(mArtistName, mAlbumName);
    }

    private QueryViewModel mQueryViewModel;
    private LiveData<Album> mAlbumInfo;
    private LiveData<String> mQueryError;
}
