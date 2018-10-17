package com.nikkuts.lastfmapp.query;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nikkuts.lastfmapp.gson.topalbums.Topalbums;
import com.nikkuts.lastfmapp.gson.topalbums.TopalbumsMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopAlbumsQuery extends QueryViewModel {

    public LiveData<Topalbums> getTopAlbumsLiveData(){
        if (mTopAlbums == null) {
            mTopAlbums = new MutableLiveData<>();
        }
        return mTopAlbums;
    }

    public void loadTopAlbums(String artist, final int page) {
        mLastFmApi.getTopAlbums(QUERY_TOPALBUMS_METHOD, QUERY_API_KEY, artist, page, QUERY_FORMAT).enqueue(new Callback<TopalbumsMsg>() {
            @Override
            public void onResponse(Call<TopalbumsMsg> call, Response<TopalbumsMsg> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTopalbums() != null) {
                        if (page > 1) {
                            mTopAlbums.getValue().getAlbum().addAll(response.body().getTopalbums().getAlbum());
                            mTopAlbums.postValue(mTopAlbums.getValue());
                        }
                        else
                            mTopAlbums.postValue(response.body().getTopalbums());
                    }
                    else {
                        mQueryError.postValue("Artist not found");
                    }
                } else {
                    handleHTTPError(response.code());
                }
            }

            @Override
            public void onFailure(Call<TopalbumsMsg> call, Throwable t) {
                mQueryError.postValue("Network failure");
                Log.e(QueryViewModel.class.getName(), "Network failure");
            }
        });
    }

    private MutableLiveData<Topalbums> mTopAlbums;
}
