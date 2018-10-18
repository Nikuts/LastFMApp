package com.nikkuts.lastfmapp.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.AlbumInfoActivity;
import com.nikkuts.lastfmapp.LocalAlbumInfoActivity;
import com.nikkuts.lastfmapp.db.AlbumsDatabase;
import com.nikkuts.lastfmapp.db.DatabaseActionAsyncTask;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;

import java.util.List;

public class LocalAlbumsAdapter extends AlbumsAdapter {

    public LocalAlbumsAdapter(Context context) {
        super(context);
    }

    public void setAlbums(List<Album> albums){
        this.mAlbums = albums;
        notifyDataSetChanged();
    }

    @Override
    protected void bindAlbumsViewHolder(AlbumsViewHolder holder, final int position) {
        if (mAlbums != null) {

            holder.mPrimaryText.setText(mAlbums.get(position).getName());
            holder.mSubText.setText(mAlbums.get(position).getArtist());

            Glide.with(holder.mImage)
                    .load(mAlbums.get(position).getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(holder.mImage);

            holder.mDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent infoIntent = new Intent(view.getContext(), LocalAlbumInfoActivity.class);
                    infoIntent.putExtra("album", mAlbums.get(position).getName());
                    infoIntent.putExtra("artist", mAlbums.get(position).getArtist());
                    view.getContext().startActivity(infoIntent);
                }
            });

            holder.mSavedImage.setVisibility(View.VISIBLE);
            holder.mSaveButton.setOnClickListener(view ->
                    new DatabaseActionAsyncTask(AlbumsDatabase.getDatabase(mContext), DatabaseActionAsyncTask.Action.DELETE).execute(mAlbums.get(position)));
        }
    }

    @Override
    protected int getAlbumsItemCount() {
        if (mAlbums != null) {
            return mAlbums.size();
        }
        return 0;
    }

    private List<Album> mAlbums;
}
