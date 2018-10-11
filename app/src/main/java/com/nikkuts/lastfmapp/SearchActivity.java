package com.nikkuts.lastfmapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nikkuts.lastfmapp.api.QueryManager;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        initRecyclerView();
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.include_toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mImageButton = findViewById(R.id.search_bar_icon);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText queryEditText = findViewById(R.id.search_bar_edit_text);
                mQueryManager.getTopAlbums(queryEditText.getText().toString(), mAdapter);
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
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueryManager = new QueryManager();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private EditText mEditText;
    private ImageButton mImageButton;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AlbumsAdapter mAdapter;

    private QueryManager mQueryManager;
}
