package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.adaptors.TracksAdaptor;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;
import com.nikkuts.lastfmapp.query.AlbumInfoQuery;
import com.nikkuts.lastfmapp.query.QueryViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfoActivity extends AppCompatActivity {
    private static final float THUMBNAIL_SIZE = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initLayout();
        initQueryViewModel();

        Intent intent = getIntent();
        String artist = intent.getExtras().getString("artist");
        String album = intent.getExtras().getString("album");

        mAlbumInfoQuery.loadAlbumInfo(artist, album);
    }

    private void initLayout(){

        Toolbar toolbar = findViewById(R.id.toolbar_transparent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMediaImage = findViewById(R.id.media_image);
        mPrimaryText = findViewById(R.id.primary_text);
        mSubText = findViewById(R.id.sub_text);

        mTracksView = findViewById(R.id.recyclerview_tracks);
        mTracksView.setHasFixedSize(true);
        mTracksView.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTracksView.setLayoutManager(layoutManager);

        mTracksAdapter = new TracksAdaptor();
        mTracksView.setAdapter(mTracksAdapter);

        mSpinner = findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.VISIBLE);
    }

    private void initQueryViewModel(){
        mAlbumInfoQuery = ViewModelProviders.of(this).get(AlbumInfoQuery.class);
        mAlbumInfo = mAlbumInfoQuery.getAlbumInfoLiveData();
        mAlbumInfo.observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {
                mPrimaryText.setText(album.getName());
                mSubText.setText(album.getArtist());

                mSpinner.setVisibility(View.GONE);

                mTracksAdapter.setAlbums(album.getTracks().getTrack());

                Glide.with(mMediaImage)
                        .load(album.getImage().get(Album.EXTRALARGE_IMAGE_URL_INDEX).getText())
                        .thumbnail(THUMBNAIL_SIZE)
                        .into(mMediaImage);
            }
        });

        mQueryError = mAlbumInfoQuery.getHTTPErrorLiveData();
        mQueryError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                mSpinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private ImageView mMediaImage;
    private TextView mPrimaryText;
    private TextView mSubText;
    private ProgressBar mSpinner;

    private RecyclerView mTracksView;
    private TracksAdaptor mTracksAdapter;

    private AlbumInfoQuery mAlbumInfoQuery;
    private LiveData<Album> mAlbumInfo;
    private LiveData<String> mQueryError;
}
