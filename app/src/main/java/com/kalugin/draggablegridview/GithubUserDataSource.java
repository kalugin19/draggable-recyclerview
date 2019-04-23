package com.kalugin.draggablegridview;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.ItemKeyedDataSource;

import com.kalugin.draggablegridview.remote.ApiResponse;
import com.kalugin.draggablegridview.remote.GithubApi;

import java.util.List;
import java.util.concurrent.Executor;

public class GithubUserDataSource extends ItemKeyedDataSource<Long, User> {

    @SuppressWarnings("FieldCanBeLocal")
    private final Long INITIAL_ID = 0L;
    private final GithubApi restService;
    private final Executor retryExecutor;
    private IRetry iRetry;
    public final MutableLiveData<NetworkState> networkStateLiveData = new MutableLiveData<>();

    GithubUserDataSource(GithubApi restService, Executor retryExecutor) {
        this.restService = restService;
        this.retryExecutor = retryExecutor;
    }

    private void fetchUsers(Long id, LoadCallback<User> callback, IRetry iRetry) {
        LiveData<ApiResponse<List<User>>> responseLiveData = restService.getUsers(id);
        responseLiveData.observeForever(new Observer<ApiResponse<List<User>>>() {
            @Override
            public void onChanged(ApiResponse<List<User>> listApiResponse) {
                ApiResponse<List<User>> response = responseLiveData.getValue();
                if (response != null && response.isSuccessful() && TextUtils.isEmpty(response.errorMessage) && response.body != null) {
                    callback.onResult(response.body);
                    GithubUserDataSource.this.iRetry = null;
                } else {
                    GithubUserDataSource.this.iRetry = iRetry;
                }
                responseLiveData.removeObserver(this);
            }
        });
    }


    public void retry() {
        if (retryExecutor != null) {
            retryExecutor.execute(() -> {
                if (iRetry != null) {
                    iRetry.retry();
                }
            });
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<User> callback) {
        fetchUsers(INITIAL_ID, callback, () -> loadInitial(params, callback));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        fetchUsers(params.key, callback, () -> loadAfter(params, callback));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.getId();
    }

    interface IRetry {
        void retry();
    }
}
