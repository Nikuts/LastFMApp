package com.nikkuts.lastfmapp.adapters;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nikkuts.lastfmapp.LocalAlbumInfoActivity;
import com.nikkuts.lastfmapp.RemoteAlbumInfoActivity;
import com.nikkuts.lastfmapp.db.AlbumFactory;
import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.db.AlbumsDatabaseViewModel;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.query.ApiManager;
import com.nikkuts.lastfmapp.query.listeners.IAlbumInfoLoadedListener;

import java.util.List;

public class RemoteAlbumsAdapter extends AlbumsAdapter implements IAlbumInfoLoadedListener {

    public RemoteAlbumsAdapter(Context context) {
        super(context);
        mAlbumsDatabaseViewModel = new AlbumsDatabaseViewModel((Application) context.getApplicationContext());
    }

    public void setAlbums(Topalbums albums){
        this.mAlbums = albums;
        notifyDataSetChanged();
    }

    public void setBottomReachedListener(IBottomReachedListener bottomReachedListener){

        this.mOnBottomReachedListener = bottomReachedListener;
    }

    @Override
    protected void bindAlbumsViewHolder(final AlbumsViewHolder holder, final int position) {
        if (mAlbums != null) {
            if (mOnBottomReachedListener != null && position == mAlbums.getAlbum().size() - 5){
                mOnBottomReachedListener.onBottomReached(position);
            }
            final String albumName = mAlbums.getAlbum().get(position).getName();
            final String artistName = mAlbums.getAlbum().get(position).getArtist().getName();

            holder.mPrimaryText.setText(albumName);
            holder.mSubText.setText(artistName);

            Glide.with(holder.mImage)
                    .load(mAlbums.getAlbum().get(position).getImage().get(Album.LARGE_IMAGE_URL_INDEX).getText())
                    .thumbnail(THUMBNAIL_SIZE)
                    .into(holder.mImage);

            mSavedAlbums = mAlbumsDatabaseViewModel.getAlbumsWithTracksLiveDataByArtist((LifecycleOwner) mContext,
                    mAlbums.getAlbum().get(position).getArtist().getName());
            mSavedAlbums.observe((LifecycleOwner) mContext, albumWithTracks -> {
                AlbumWithTracks currentLocalAlbum = albumWithTracks.stream().filter(p ->
                        p.getAlbumInfoEntity().getAlbumName().equals(albumName))
                        .findFirst()
                        .orElse(null);

                if (currentLocalAlbum != null){
                    holder.mSavedImage.setVisibility(View.VISIBLE);
                    holder.mSaveButton.setOnClickListener(view -> {
                        holder.mSavedImage.setVisibility(View.GONE);
                        deleteLocalAlbum(currentLocalAlbum);
                    });

                    holder.mCardView.setOnClickListener(view -> {
                        Intent infoIntent = setupInfoIntent(view, albumName, artistName, true);
                        view.getContext().startActivity(infoIntent);
                    });
                }
                else {
                    holder.mSavedImage.setVisibility(View.GONE);
                    holder.mSaveButton.setOnClickListener(view -> {
                        holder.mSavedImage.setVisibility(View.VISIBLE);
                        saveRemoteAlbum(position);
                    });

                    holder.mCardView.setOnClickListener(view -> {
                        Intent infoIntent = setupInfoIntent(view, albumName, artistName, false);
                        view.getContext().startActivity(infoIntent);
                    });
                }
            });
        }
    }

    private Intent setupInfoIntent(View view, String albumName, String artistName, boolean isLocalAlbum){
        Intent infoIntent = null;
        if (isLocalAlbum){
            infoIntent = new Intent(view.getContext(), LocalAlbumInfoActivity.class);
        }
        else {
            infoIntent = new Intent(view.getContext(), RemoteAlbumInfoActivity.class);
        }
        infoIntent.putExtra("album", albumName);
        infoIntent.putExtra("artist", artistName);
        return infoIntent;
    }

    private void deleteLocalAlbum(AlbumWithTracks localAlbum){
        mAlbumsDatabaseViewModel.deleteItem(AlbumFactory.createAlbumFromEntities(
                localAlbum.getAlbumInfoEntity(),
                localAlbum.getTrackEntities()));
    }

    private void saveRemoteAlbum(int position){
        ApiManager apiManager = new ApiManager();
        apiManager.setAlbumInfoLoadedListener(RemoteAlbumsAdapter.this);
        apiManager.loadAlbumInfo(mAlbums.getAlbum().get(position).getArtist().getName(),
                mAlbums.getAlbum().get(position).getName());
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
        mAlbumsDatabaseViewModel.insertItem(album);
    }

    private IBottomReachedListener mOnBottomReachedListener;
    private Topalbums mAlbums;
    private AlbumsDatabaseViewModel mAlbumsDatabaseViewModel;
    private LiveData<List<AlbumWithTracks>> mSavedAlbums;
}
