package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nikkuts.lastfmapp.adapters.IBottomReachedListener;
import com.nikkuts.lastfmapp.adapters.RemoteAlbumsAdapter;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.viewmodel.TopAlbumsViewModel;

public class TopAlbumsActivity extends AppCompatActivity implements IBottomReachedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_search);

        Intent intent = getIntent();
        mArtistName = intent.getExtras().getString("artist");

        initToolbar();
        initSpinner();
        initRecyclerView();
        initQueryViewModel();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle(mArtistName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.search_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RemoteAlbumsAdapter(this);
        mAdapter.setBottomReachedListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initQueryViewModel(){
        mTopAlbumsViewModel = ViewModelProviders.of(this).get(TopAlbumsViewModel.class);
        mTopAlbums = mTopAlbumsViewModel.getTopAlbumsLiveData();
        mTopAlbums.observe(this, topalbums -> {
            mSpinner.setVisibility(View.GONE);
            mAdapter.setAlbums(topalbums);
        });

        mQueryError = mTopAlbumsViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, error -> {
            mSpinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        });

        mQueryPage = 1;
        mTopAlbumsViewModel.loadTopAlbums(mArtistName, mQueryPage);
    }

    private void initSpinner(){
        mSpinner = findViewById(R.id.progressBar);
    }

    @Override
    public void onBottomReached(int position) {
        mQueryPage++;
        mTopAlbumsViewModel.loadTopAlbums(mArtistName, mQueryPage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String mArtistName;

    private int mQueryPage;

    private ProgressBar mSpinner;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RemoteAlbumsAdapter mAdapter;

    private TopAlbumsViewModel mTopAlbumsViewModel;
    private LiveData<Topalbums> mTopAlbums;
    private LiveData<String> mQueryError;
}
