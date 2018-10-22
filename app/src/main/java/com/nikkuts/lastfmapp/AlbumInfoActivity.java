package com.nikkuts.lastfmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nikkuts.lastfmapp.adapters.TracksAdaptor;
import com.nikkuts.lastfmapp.db.AlbumsDatabase;
import com.nikkuts.lastfmapp.db.DatabaseActionAsyncTask;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

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

        setIsLocal();
        setFloatButtonImage();
        mSaveButton.setOnClickListener(view -> {
            onSaveClick();
        });
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

        mSaveButton = findViewById(R.id.floating_action_button);
    }

    protected abstract void initViewModel();
    protected abstract void setIsLocal();

    protected void setFloatButtonImage(){
        if (mIsLocal){
            mSaveButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
        else {
            mSaveButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }
    protected void onSaveClick() {
        if (mCurrentAlbum != null) {
            if (mIsLocal) {
                mSaveButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                new DatabaseActionAsyncTask(AlbumsDatabase.getDatabase(this), DatabaseActionAsyncTask.Action.DELETE).execute(mCurrentAlbum);
            } else {
                mSaveButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                new DatabaseActionAsyncTask(AlbumsDatabase.getDatabase(this), DatabaseActionAsyncTask.Action.INSERT).execute(mCurrentAlbum);
            }
            mIsLocal = !mIsLocal;
        }
    }

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
    protected ImageButton mSaveButton;

    protected RecyclerView mTracksView;
    protected TracksAdaptor mTracksAdapter;

    protected String mArtistName;
    protected String mAlbumName;

    protected boolean mIsLocal;
    protected Album mCurrentAlbum;
}
