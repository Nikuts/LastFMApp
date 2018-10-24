package com.nikkuts.lastfmapp.db;

import android.os.AsyncTask;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.db.entity.TrackEntity;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;

import java.util.List;

public class DatabaseActionAsyncTask extends AsyncTask<Album, Void, Void> {

    public enum Action {DELETE, INSERT, UPDATE}

    public DatabaseActionAsyncTask(AlbumsDatabase albumsDatabase, Action action) {
        mDatabase = albumsDatabase;
        mAction = action;
    }

    @Override
    protected Void doInBackground(final Album... params) {

        switch (mAction){
            case DELETE: {
                List<AlbumInfoEntity> albumInfoEntity = mDatabase.albumDao().getAlbums(params[0].getArtist(), params[0].getName());
                if (albumInfoEntity != null && albumInfoEntity.size() > 0) {
                    mDatabase.albumDao().delete(albumInfoEntity.get(0));
                    mDatabase.trackDao().deleteAllTracksByAlbumId(albumInfoEntity.get(0).getId());
                }
                break;
            }
            case INSERT:
                if (mDatabase.albumDao().getAlbums(params[0].getArtist(), params[0].getName()).size() == 0) {
                    long albumId = mDatabase.albumDao().insert(new AlbumInfoEntity(params[0]));
                    if (params[0].getTracks() != null) {
                        for (Track track : params[0].getTracks().getTrack()) {
                            mDatabase.trackDao().insert(new TrackEntity(albumId, track.getName()));
                        }
                    }
                }
                break;
            case UPDATE:
            {
                List<AlbumInfoEntity> albumInfoEntity = mDatabase.albumDao().getAlbums(params[0].getArtist(), params[0].getName());
                if (albumInfoEntity != null && albumInfoEntity.size() > 0) {
                    AlbumInfoEntity updatedAlbumInfoEntity = new AlbumInfoEntity(params[0]);
                    updatedAlbumInfoEntity.setId(albumInfoEntity.get(0).getId());
                    mDatabase.albumDao().update(updatedAlbumInfoEntity);
                    if (params[0].getTracks().getTrack() != null) {
                        for (Track track : params[0].getTracks().getTrack()) {
                            mDatabase.trackDao().insert(new TrackEntity(albumInfoEntity.get(0).getId(), track.getName()));
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
        return null;
    }

    private AlbumsDatabase mDatabase;
    private Action mAction;
}