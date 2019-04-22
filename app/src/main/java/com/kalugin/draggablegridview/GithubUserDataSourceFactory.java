package com.kalugin.draggablegridview;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import java.util.concurrent.Executor;

public class GithubUserDataSourceFactory extends DataSource.Factory<Long, User> {

    private final GithubApi restService;
    private final Executor retryExecutor;
    private final LiveData<GithubUserDataSource> sourceLiveData = new MutableLiveData<>();

    public GithubUserDataSourceFactory(GithubApi restService, Executor retryExecutor) {
        this.restService = restService;
        this.retryExecutor = retryExecutor;
    }

    @NonNull
    @Override
    public DataSource<Long, User> create() {
        return new GithubUserDataSource(restService, retryExecutor);
    }

}
