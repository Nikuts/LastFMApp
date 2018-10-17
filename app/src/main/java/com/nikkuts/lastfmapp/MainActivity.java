package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nikkuts.lastfmapp.db.AlbumsDatabase;
import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        AlbumsDatabase database = AlbumsDatabase.getDatabase(this);
        mAlbumInfoLiveData = database.albumDao().getAllAlbums();
        mAlbumInfoLiveData.observe(this, new Observer<List<AlbumInfoEntity>>() {
            @Override
            public void onChanged(@Nullable List<AlbumInfoEntity> albumInfoEntities) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

    LiveData<List<AlbumInfoEntity>> mAlbumInfoLiveData;
}
