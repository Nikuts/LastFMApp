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

import com.nikkuts.lastfmapp.adaptors.TracksAdaptor;

import java.util.List;

public abstract class AlbumInfoActivity extends AppCompatActivity {
    protected static final float THUMBNAIL_SIZE = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initLayout();

        initViewModel();

        Intent intent = getIntent();
        mArtistName = intent.getExtras().getString("artist");
        mAlbumName = intent.getExtras().getString("album");
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

    protected abstract void initViewModel();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected ImageView mMediaImage;
    protected TextView mPrimaryText;
    protected TextView mSubText;
    protected ProgressBar mSpinner;

    protected RecyclerView mTracksView;
    protected TracksAdaptor mTracksAdapter;

    protected String mArtistName;
    protected String mAlbumName;
}
