package com.nikkuts.lastfmapp.query;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.AlbuminfoMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumInfoQuery extends QueryViewModel {

    public LiveData<Album> getAlbumInfoLiveData() {
        if (mAlbumInfo == null) {
            mAlbumInfo = new MutableLiveData<>();
        }
        return mAlbumInfo;
    }

    public void loadAlbumInfo(String artist, String album){
        mLastFmApi.getAlbumInfo(QUERY_ALBUMINFO_METHOD, QUERY_API_KEY, artist, album, QUERY_FORMAT).enqueue(new Callback<AlbuminfoMsg>() {
            @Override
            public void onResponse(Call<AlbuminfoMsg> call, Response<AlbuminfoMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getAlbum() != null) {
                        mAlbumInfo.postValue(response.body().getAlbum());
                    }
                    else {
                        mQueryError.postValue("Album not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<AlbuminfoMsg> call, Throwable t) {
                mQueryError.postValue("Network failure");
                Log.e(QueryViewModel.class.getName(), "Network failure");
            }
        });
    }
    private MutableLiveData<Album> mAlbumInfo;
}
