package com.kalugin.draggablegridview;

import java.util.concurrent.Executor;

public class UsersRepository {

    private final GithubApi githubApi;
    private final Executor networkExecutor;

    public UsersRepository(GithubApi githubApi, Executor networkExecutor) {
        this.githubApi = githubApi;
        this.networkExecutor = networkExecutor;
    }
}
