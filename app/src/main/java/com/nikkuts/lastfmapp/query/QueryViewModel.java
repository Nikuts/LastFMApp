package com.nikkuts.lastfmapp.query;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.nikkuts.lastfmapp.query.api.ILastFmApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueryViewModel extends ViewModel {

    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com";
    public static final String QUERY_TOPALBUMS_METHOD = "artist.gettopalbums";
    public static final String QUERY_ALBUMINFO_METHOD = "album.getInfo";
    public static final String QUERY_API_KEY = "1e88a3a6a8039f151b6870c55249d094";
    public static final String QUERY_FORMAT = "json";

    public QueryViewModel() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QUERY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mLastFmApi = retrofit.create(ILastFmApi.class);
    }

    public LiveData<String> getHTTPErrorLiveData() {
        if (mQueryError == null) {
            mQueryError = new MutableLiveData<>();
        }
        return mQueryError;
    }

    protected void handleHTTPError(int code){
        switch (code) {
            case 404:
                Log.e(QueryViewModel.class.getName(), "Not found");
                mQueryError.postValue("Not found");
                break;
            case 500:
                Log.e(QueryViewModel.class.getName(), "Server broken");
                mQueryError.postValue("Server broken");
                break;
            default:
                Log.e(QueryViewModel.class.getName(), "Unknown error");
                mQueryError.postValue("Unknown error");
                break;
        }
    }

    protected MutableLiveData<String> mQueryError;
    protected ILastFmApi mLastFmApi;
}
