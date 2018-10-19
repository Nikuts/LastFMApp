package com.nikkuts.lastfmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nikkuts.lastfmapp.adapters.TracksAdaptor;

public abstract class AlbumInfoActivity extends AppCompatActivity {
    protected static final float THUMBNAIL_SIZE = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        mArtistName = intent.getExtras().getString("artist");
        mAlbumName = intent.getExtras().getString("album");

        initLayout();
        initViewModel();
    }

    private void initLayout(){

        mToolbar = findViewById(R.id.toolbar_transparent);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mMediaImage = findViewById(R.id.media_image);
        mTitle = findViewById(R.id.title);
        mSubtitle = findViewById(R.id.subtitle);

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

    protected abstract void initViewModel();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected ImageView mMediaImage;
    protected Toolbar mToolbar;
    protected TextView mTitle;
    protected TextView mSubtitle;
    protected ProgressBar mSpinner;

    protected RecyclerView mTracksView;
    protected TracksAdaptor mTracksAdapter;

    protected String mArtistName;
    protected String mAlbumName;
}
