package com.nikkuts.lastfmapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nikkuts.lastfmapp.adapters.LocalAlbumsAdapter;
import com.nikkuts.lastfmapp.db.AlbumInfoFactory;
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

        mDatabaseViewModel = ViewModelProviders.of(this).get(AlbumsDatabaseViewModel.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAlbumWithTracksLiveData = mDatabaseViewModel.getAllAlbumsWithTracksLiveData(this);
        mAlbumWithTracksLiveData.observe(this, albumWithTracksList -> {
            List<Album> albums = new ArrayList<>();
            for (AlbumWithTracks album : albumWithTracksList){
                albums.add(AlbumInfoFactory.createAlbumInfoFromEntities(album.getAlbumInfoEntity(), album.getTrackEntities()));
            }
            mAdapter.setAlbums(albums);
            mAlbumWithTracksLiveData.removeObservers(this);
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.main_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LocalAlbumsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewHolder.itemView.findViewById(R.id.checkbox_like).callOnClick();
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
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

    private MutableLiveData<List<AlbumWithTracks>> mAlbumWithTracksLiveData;
    private AlbumsDatabaseViewModel mDatabaseViewModel;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LocalAlbumsAdapter mAdapter;
}
