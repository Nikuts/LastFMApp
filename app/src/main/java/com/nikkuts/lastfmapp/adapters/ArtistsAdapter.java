package com.nikkuts.lastfmapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nikkuts.lastfmapp.R;
import com.nikkuts.lastfmapp.TopAlbumsActivity;
import com.nikkuts.lastfmapp.glide.GlideApp;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.artist.Artist;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    public void setArtists(List<Artist> artists){
        this.mArtists = artists;
        notifyDataSetChanged();
    }

    public void setBottomReachedListener(IBottomReachedListener bottomReachedListener){
        this.mOnBottomReachedListener = bottomReachedListener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_artist, parent, false);
        ArtistsAdapter.ArtistViewHolder viewHolder = new ArtistsAdapter.ArtistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        if (mArtists != null) {
            if (mOnBottomReachedListener != null && position == mArtists.size() - 5){
                mOnBottomReachedListener.onBottomReached(position);
            }
            holder.mText.setText(mArtists.get(position).getName());

            holder.mCardView.setOnClickListener(view -> {
                loadTopAlbums(holder.mCardView.getContext(), mArtists.get(position).getName());
            });

            GlideApp.with(holder.mImage)
                    .load(mArtists.get(position).getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText())
                    .placeholder(R.drawable.ic_placeholder_gray_24dp)
                    .error(R.drawable.ic_placeholder_gray_24dp)
                    .fallback(R.drawable.ic_placeholder_gray_24dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mArtists != null){
            return mArtists.size();
        }
        return 0;
    }

    private void loadTopAlbums(Context context, String name){
        Intent topAlbumsIntent = new Intent(context, TopAlbumsActivity.class);
        topAlbumsIntent.putExtra("artist", name);
        context.startActivity(topAlbumsIntent);
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        public ArtistViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardview_artist);
            mText = itemView.findViewById(R.id.supporting_text);
            mImage = itemView.findViewById(R.id.media_image);
        }
        private TextView mText;
        private ImageView mImage;
        private CardView mCardView;
    }

    private IBottomReachedListener mOnBottomReachedListener;
    private List<Artist> mArtists;
}
