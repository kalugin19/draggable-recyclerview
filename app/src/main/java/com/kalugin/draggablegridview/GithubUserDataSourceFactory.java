package com.kalugin.draggablegridview;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.kalugin.draggablegridview.remote.GithubApi;

import java.util.concurrent.Executor;

public class GithubUserDataSourceFactory extends DataSource.Factory<Long, User> {

    private final GithubApi restService;
    private final Executor retryExecutor;
    public final MutableLiveData<GithubUserDataSource> sourceLiveData = new MutableLiveData<>();

    public GithubUserDataSourceFactory(GithubApi restService, Executor retryExecutor) {
        this.restService = restService;
        this.retryExecutor = retryExecutor;
    }

    @NonNull
    @Override
    public DataSource<Long, User> create() {
        GithubUserDataSource source = new GithubUserDataSource(restService, retryExecutor);
        sourceLiveData.postValue(source);
        return source;
    }

}
