package com.nikkuts.lastfmapp.db;

import android.os.AsyncTask;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.db.entity.TrackEntity;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;

import java.util.List;

public class DatabaseActionAsyncTask extends AsyncTask<Album, Void, Void> {

    public enum Action {DELETE, INSERT, GET_TRACKS }

    public DatabaseActionAsyncTask(AlbumsDatabase albumsDatabase, Action action) {
        mDatabase = albumsDatabase;
        mAction = action;
    }

    @Override
    protected Void doInBackground(final Album... params) {
        switch (mAction){
            case DELETE:
                mDatabase.albumDao().delete(new AlbumInfoEntity(params[0]));
                mDatabase.trackDao().deleteAllTracksByMbid(params[0].getMbid());
                break;
            case INSERT:
                mDatabase.albumDao().insert(new AlbumInfoEntity(params[0]));
                for (Track track : params[0].getTracks().getTrack()) {
                    mDatabase.trackDao().insert(new TrackEntity(params[0].getMbid(), track.getName()));
                }
                break;
            case GET_TRACKS:
                mTracks = mDatabase.trackDao().getAllTracksByMbid(params[0].getMbid());
                break;
            default:
                break;
        }
        return null;
    }

    private AlbumsDatabase mDatabase;
    private Action mAction;
    private List<TrackEntity> mTracks;
}