package com.nikkuts.lastfmapp;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nikkuts.lastfmapp.adapters.ArtistsAdapter;
import com.nikkuts.lastfmapp.adapters.IBottomReachedListener;
import com.nikkuts.lastfmapp.gson.artist.ArtistSearchResults;
import com.nikkuts.lastfmapp.query.viewmodel.ArtistsViewModel;

public class ArtistSearchActivity extends AppCompatActivity implements IBottomReachedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_search);

        initToolbar();
        initSpinner();
        initRecyclerView();
        initQueryViewModel();
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mImageButton = findViewById(R.id.search_bar_icon);
        mImageButton.setOnClickListener(view -> {

            hideKeyboard();

            mSpinner.setVisibility(View.VISIBLE);

            mQueryPage = 1;

            EditText queryEditText = findViewById(R.id.search_bar_edit_text);
            mQueryText = queryEditText.getText().toString();

            mArtistsViewModel.loadArtists(mQueryText.trim(), mQueryPage);
        });

        mEditText = findViewById(R.id.search_bar_edit_text);
        mEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mImageButton.performClick();
                return true;
            }
            return false;
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.search_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ArtistsAdapter();
        mAdapter.setBottomReachedListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initQueryViewModel(){
        mArtistsViewModel = ViewModelProviders.of(this).get(ArtistsViewModel.class);
        mArtistSearchResults = mArtistsViewModel.getArtistSearchResultsLiveData();
        mArtistSearchResults.observe(this, artistSearchResults -> {
            mSpinner.setVisibility(View.GONE);
            mAdapter.setArtists(artistSearchResults.getResults().getArtistmatches().getArtist());
        });

        mQueryError = mArtistsViewModel.getHTTPErrorLiveData();
        mQueryError.observe(this, error -> {
            mSpinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        });

    }

    private void initSpinner(){
        mSpinner = findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    @Override
    public void onBottomReached(int position) {
        mQueryPage++;
        mArtistsViewModel.loadArtists(mQueryText, mQueryPage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String mQueryText;
    private int mQueryPage;

    private EditText mEditText;
    private ImageButton mImageButton;

    private ProgressBar mSpinner;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArtistsAdapter mAdapter;

    private ArtistsViewModel mArtistsViewModel;
    private LiveData<ArtistSearchResults> mArtistSearchResults;
    private LiveData<String> mQueryError;
}
