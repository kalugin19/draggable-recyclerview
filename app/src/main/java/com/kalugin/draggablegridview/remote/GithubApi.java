package com.kalugin.draggablegridview.remote;

import androidx.lifecycle.LiveData;

import com.kalugin.draggablegridview.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("users")
    LiveData<ApiResponse<List<User>>> getUsers(@Query("since") long since);

}
