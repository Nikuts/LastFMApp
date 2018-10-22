package com.nikkuts.lastfmapp.query.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nikkuts.lastfmapp.query.ApiManager;
import com.nikkuts.lastfmapp.query.listeners.IQueryErrorListener;

public abstract class QueryViewModel extends ViewModel implements IQueryErrorListener {

    public QueryViewModel() {
        mApiManager = new ApiManager();
        mApiManager.setQueryErrorListener(this);
        setListener();
    }

    public abstract void setListener();

    public MutableLiveData<String> getHTTPErrorLiveData() {
        if (mQueryError == null) {
            mQueryError = new MutableLiveData<>();
        }
        return mQueryError;
    }

    @Override
    public void onError(String errorMsg) {
        mQueryError.postValue(errorMsg);
    }

    protected ApiManager mApiManager;
    protected MutableLiveData<String> mQueryError;
}
