package com.kalugin.draggablegridview.remote;


import android.content.Context;

import com.kalugin.draggablegridview.BuildConfig;
import com.kalugin.draggablegridview.livedata.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;public class RetrofitHelper {

    private static final int TIMEOUT = 120;
    private static GithubApi restService;
    private static RetrofitHelper retrofitHelper;

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    /**
     * Создание rest сервиса
     *
     * @return Rest сервис
     */
    public GithubApi newRestServicee(Context appContext) {

        if (restService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
            httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging);
            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.REST_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .client(client)
                    .build();
            restService = retrofit.create(GithubApi.class);
        }
        return restService;
    }
}
