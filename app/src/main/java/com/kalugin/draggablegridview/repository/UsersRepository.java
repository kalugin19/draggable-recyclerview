package com.kalugin.draggablegridview.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.kalugin.draggablegridview.GithubUserApplication;
import com.kalugin.draggablegridview.GithubUserDataSourceFactory;
import com.kalugin.draggablegridview.Listing;
import com.kalugin.draggablegridview.MainExecutor;
import com.kalugin.draggablegridview.NetworkState;
import com.kalugin.draggablegridview.User;
import com.kalugin.draggablegridview.remote.GithubApi;

import java.util.concurrent.Executor;

public class UsersRepository {

    private final int PAGE_SIZE = 30;
    private final GithubApi githubApi;
    private final Executor networkExecutor;

    public UsersRepository() {
        this.githubApi = GithubUserApplication.getRestService();
        this.networkExecutor = GithubUserApplication.NETWORK_IO;
    }

    public Listing<User> loadUsers() {
        GithubUserDataSourceFactory factory = new GithubUserDataSourceFactory(githubApi, networkExecutor);
        LiveData<NetworkState> networkState = Transformations.switchMap(factory.sourceLiveData, dataSource -> dataSource.networkStateLiveData);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<User>> pagedList = new LivePagedListBuilder<>(factory, config)
                .setFetchExecutor(new MainExecutor())
                .build();

        return new Listing<>(pagedList, networkState);
    }
}
