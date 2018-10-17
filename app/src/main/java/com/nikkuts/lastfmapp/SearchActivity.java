package com.nikkuts.lastfmapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nikkuts.lastfmapp.adaptors.AlbumsAdapter;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.QueryViewModel;
import com.nikkuts.lastfmapp.query.TopAlbumsQuery;

public class SearchActivity extends AppCompatActivity implements IBottomReachedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpinner.setVisibility(View.VISIBLE);

                mQueryPage = 1;

                EditText queryEditText = findViewById(R.id.search_bar_edit_text);
                mQueryText = queryEditText.getText().toString();

                mTopAlbumsQuery.loadTopAlbums(mQueryText.trim(), mQueryPage);
            }
        });

        mEditText = findViewById(R.id.search_bar_edit_text);
        mEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mImageButton.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.search_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AlbumsAdapter();
        mAdapter.setBottomReachedListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initQueryViewModel(){
        mTopAlbumsQuery = ViewModelProviders.of(this).get(TopAlbumsQuery.class);
        mTopAlbums = mTopAlbumsQuery.getTopAlbumsLiveData();
        mTopAlbums.observe(this, new Observer<Topalbums>() {
            @Override
            public void onChanged(@Nullable Topalbums topalbums) {
                mSpinner.setVisibility(View.GONE);
                mAdapter.setAlbums(topalbums);
            }
        });

        mQueryError = mTopAlbumsQuery.getHTTPErrorLiveData();
        mQueryError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                mSpinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinner(){
        mSpinner = findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onBottomReached(int position) {
        mQueryPage++;
        mTopAlbumsQuery.loadTopAlbums(mQueryText, mQueryPage);
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
    private AlbumsAdapter mAdapter;

    private TopAlbumsQuery mTopAlbumsQuery;
    private LiveData<Topalbums> mTopAlbums;
    private LiveData<String> mQueryError;
}
