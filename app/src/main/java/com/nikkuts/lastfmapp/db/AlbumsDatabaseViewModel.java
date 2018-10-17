package com.nikkuts.lastfmapp.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;

import java.util.List;

public class AlbumsDatabaseViewModel extends AndroidViewModel {

    private final LiveData<List<AlbumInfoEntity>> mAlbumsLiveData;

    public AlbumsDatabaseViewModel(@NonNull Application application) {
        super(application);
        mAlbumsDatabase = AlbumsDatabase.getDatabase(this.getApplication());
        mAlbumsLiveData = mAlbumsDatabase.albumDao().getAllAlbums();
    }

    public LiveData<List<AlbumInfoEntity>> getmAlbumLiveData() {
        return mAlbumsLiveData;
    }

    public void deleteItem(AlbumInfoEntity albumInfoEntity) {
        new DeleteAsyncTask(mAlbumsDatabase).execute(albumInfoEntity);
    }

    private static class DeleteAsyncTask extends AsyncTask<AlbumInfoEntity, Void, Void> {


        DeleteAsyncTask(AlbumsDatabase albumsDatabase) {
            mDatabase = albumsDatabase;
        }

        @Override
        protected Void doInBackground(final AlbumInfoEntity... params) {
            mDatabase.albumDao().delete(params[0]);
            mDatabase.trackDao().deleteAllTracksByMbid(params[0].getMbid());
            return null;
        }

        private AlbumsDatabase mDatabase;
    }

    private AlbumsDatabase mAlbumsDatabase;
}
