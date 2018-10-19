package com.nikkuts.lastfmapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikkuts.lastfmapp.R;

public abstract class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {
    protected static final float THUMBNAIL_SIZE = 0.2f;

    public AlbumsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_album, parent, false);
        AlbumsViewHolder viewHolder = new AlbumsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsViewHolder holder, int position) {
        bindAlbumsViewHolder(holder, position);

    }

    @Override
    public int getItemCount() {
        return getAlbumsItemCount();
    }

    protected abstract void bindAlbumsViewHolder(AlbumsViewHolder holder, int position);
    protected abstract int getAlbumsItemCount();

    public static class AlbumsViewHolder extends RecyclerView.ViewHolder {

        public AlbumsViewHolder(View itemView) {
            super(itemView);
            mPrimaryText = itemView.findViewById(R.id.primary_text);
            mSubText = itemView.findViewById(R.id.sub_text);
            mImage = itemView.findViewById(R.id.media_image);
            mDetailsButton = itemView.findViewById(R.id.action_button_1);
            mSaveButton = itemView.findViewById(R.id.action_button_2);
            mSavedImage = itemView.findViewById(R.id.action_button_2_saved);
        }

        protected TextView mPrimaryText;
        protected TextView mSubText;
        protected ImageView mImage;
        protected Button mDetailsButton;
        protected ImageButton mSaveButton;
        protected ImageView mSavedImage;
    }

    protected Context mContext;
}
