package com.kalugin.draggablegridview;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("users")
    LiveData<ApiResponse<List<User>>> getUsers(@Query("since") long since);

}
