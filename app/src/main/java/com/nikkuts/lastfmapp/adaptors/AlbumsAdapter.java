package com.nikkuts.lastfmapp.adaptors;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.AlbumInfoActivity;
import com.nikkuts.lastfmapp.IBottomReachedListener;
import com.nikkuts.lastfmapp.R;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.AlbumInfoQuery;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {
    private static final float THUMBNAIL_SIZE = 0.2f;

    public void setAlbums(Topalbums albums){
        this.mAlbums = albums;
        notifyDataSetChanged();
    }

    public void setBottomReachedListener(IBottomReachedListener bottomReachedListener){

        this.mOnBottomReachedListener = bottomReachedListener;
    }

    @NonNull
    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_album, parent, false);
        AlbumsViewHolder viewHolder = new AlbumsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumsViewHolder holder, final int position) {
        if (mAlbums != null) {
            if (mOnBottomReachedListener != null && position == mAlbums.getAlbum().size() - 5){
                mOnBottomReachedListener.onBottomReached(position);
            }

            holder.mPrimaryText.setText(mAlbums.getAlbum().get(position).getName());
            holder.mSubText.setText(mAlbums.getAlbum().get(position).getArtist().getName());

            Glide.with(holder.mImage)
                    .load(mAlbums.getAlbum().get(position).getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(holder.mImage);

            holder.mDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent infoIntent = new Intent(view.getContext(), AlbumInfoActivity.class);
                    infoIntent.putExtra("album", mAlbums.getAlbum().get(position).getName());
                    infoIntent.putExtra("artist", mAlbums.getAlbum().get(position).getArtist().getName());
                    view.getContext().startActivity(infoIntent);
                }
            });

            holder.mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
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
            mPrimaryText = itemView.findViewById(R.id.primary_text);
            mSubText = itemView.findViewById(R.id.sub_text);
            mImage = itemView.findViewById(R.id.media_image);
            mDetailsButton = itemView.findViewById(R.id.action_button_1);
            mSaveButton = itemView.findViewById(R.id.action_button_2);
        }

        private TextView mPrimaryText;
        private TextView mSubText;
        private ImageView mImage;
        private Button mDetailsButton;
        private ImageButton mSaveButton;
    }

    private IBottomReachedListener mOnBottomReachedListener;
    private Topalbums mAlbums;
}
