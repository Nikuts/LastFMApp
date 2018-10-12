package com.nikkuts.lastfmapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.gson.Topalbums;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {
    private static final int LARGE_IMAGE_URL_INDEX = 2;
    private static final float THUMBNAIL_SIZE = 0.2f;

    public void setAlbums(Topalbums mAlbums){
        this.mAlbums = mAlbums;
        notifyDataSetChanged();
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
        if (mAlbums != null) {
            holder.mPrimaryText.setText(mAlbums.getAlbum().get(position).getName());
            holder.mSubText.setText(mAlbums.getAlbum().get(position).getArtist().getName());

            Glide.with(holder.mImage)
                    .load(mAlbums.getAlbum().get(position).getImage().get(LARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(holder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mAlbums != null) {
            return mAlbums.getAlbum().size();
        }
        return 0;
    }

    public static class AlbumsViewHolder extends RecyclerView.ViewHolder {
        public AlbumsViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardview_album);
            mPrimaryText = itemView.findViewById(R.id.primary_text);
            mSubText = itemView.findViewById(R.id.sub_text);
            mImage = itemView.findViewById(R.id.media_image);
            mButton = itemView.findViewById(R.id.action_button_1);
        }

        private CardView mCardView;
        private TextView mPrimaryText;
        private TextView mSubText;
        private ImageView mImage;
        private Button mButton;
    }


    private Topalbums mAlbums;
}
