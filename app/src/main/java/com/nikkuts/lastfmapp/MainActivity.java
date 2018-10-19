package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nikkuts.lastfmapp.adapters.LocalAlbumsAdapter;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        initRecyclerView();

        mDatabaseViewModel = new AlbumsDatabaseViewModel(this.getApplication());
        mAlbumWithTracksLiveData = mDatabaseViewModel.getAllAlbumsWithTracksLiveData();
        mAlbumWithTracksLiveData.observe(this, albumWithTracksList -> {
            if (mLastLocalAlbumsCount < albumWithTracksList.size()){ //item removing is handled by adaptor
                List<Album> albums = new ArrayList<>();
                for (AlbumWithTracks album : albumWithTracksList){
                    albums.add(AlbumFactory.createAlbumFromEntities(album.getAlbumInfoEntity(), album.getTrackEntities()));
                }
                mAdapter.setAlbums(albums);
            }
            mLastLocalAlbumsCount = albumWithTracksList.size();
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.main_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LocalAlbumsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                startSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSearch() {
        Intent searchIntent = new Intent(this, ArtistSearchActivity.class);
        startActivity(searchIntent);
    }

    private LiveData<List<AlbumWithTracks>> mAlbumWithTracksLiveData;
    private AlbumsDatabaseViewModel mDatabaseViewModel;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LocalAlbumsAdapter mAdapter;

    private int mLastLocalAlbumsCount = 0;
}
