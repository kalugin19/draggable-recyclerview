package com.kalugin.draggablegridview;

import android.app.Application;

import com.kalugin.draggablegridview.remote.GithubApi;
import com.kalugin.draggablegridview.remote.RetrofitHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GithubUserApplication extends Application {

    private static GithubApi restService;
    private static GithubUserApplication application;
    public static final Executor NETWORK_IO = Executors.newFixedThreadPool(5);

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        restService = RetrofitHelper.getInstance().newRestServicee(application);
    }


    public static GithubUserApplication getInstance() {
        return application;
    }

    public static GithubApi getRestService() {
        return restService;
    }

}
