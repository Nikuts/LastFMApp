package com.nikkuts.lastfmapp.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.AlbumInfoActivity;
import com.nikkuts.lastfmapp.RemoteAlbumInfoActivity;
import com.nikkuts.lastfmapp.db.AlbumsDatabase;
import com.nikkuts.lastfmapp.db.DatabaseActionAsyncTask;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.ApiManager;
import com.nikkuts.lastfmapp.query.listeners.IAlbumInfoLoadedListener;

public class RemoteAlbumsAdapter extends AlbumsAdapter implements IAlbumInfoLoadedListener {

    public RemoteAlbumsAdapter(Context context) {
        super(context);
    }

    public void setAlbums(Topalbums albums){
        this.mAlbums = albums;
        notifyDataSetChanged();
    }

    public void setBottomReachedListener(IBottomReachedListener bottomReachedListener){

        this.mOnBottomReachedListener = bottomReachedListener;
    }

    @Override
    protected void bindAlbumsViewHolder(AlbumsViewHolder holder, final int position) {
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

                    Intent infoIntent = new Intent(view.getContext(), RemoteAlbumInfoActivity.class);
                    infoIntent.putExtra("album", mAlbums.getAlbum().get(position).getName());
                    infoIntent.putExtra("artist", mAlbums.getAlbum().get(position).getArtist().getName());
                    view.getContext().startActivity(infoIntent);
                }
            });

            holder.mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiManager apiManager = new ApiManager();
                    apiManager.setAlbumInfoLoadedListener(RemoteAlbumsAdapter.this);
                    apiManager.loadAlbumInfo(mAlbums.getAlbum().get(position).getArtist().getName(),
                            mAlbums.getAlbum().get(position).getName());

                }
            });
        }
    }

    @Override
    protected int getAlbumsItemCount() {
        if (mAlbums != null) {
            return mAlbums.getAlbum().size();
        }
        return 0;
    }

    @Override
    public void onInfoLoaded(final Album album) {
        new DatabaseActionAsyncTask(AlbumsDatabase.getDatabase(mContext), DatabaseActionAsyncTask.Action.INSERT).execute(album);
    }

    private IBottomReachedListener mOnBottomReachedListener;
    private Topalbums mAlbums;
}
