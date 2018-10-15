package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;

public class AlbumInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.include_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initQueryViewModel(){
        mQueryViewModel = ViewModelProviders.of(this).get(QueryViewModel.class);
        mAlbumInfo = mQueryViewModel.getAlbumInfoLiveData();
        mAlbumInfo.observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {

            }
        });

        mQueryError = mQueryViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private QueryViewModel mQueryViewModel;
    private LiveData<Album> mAlbumInfo;
    private LiveData<String> mQueryError;

}
