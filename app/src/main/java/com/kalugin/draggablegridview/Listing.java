package com.kalugin.draggablegridview;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class Listing<T> {
    private LiveData<PagedList<T>> pagedList;
    private LiveData<NetworkState> networkState;

    public Listing(LiveData<PagedList<T>> pagedList, LiveData<NetworkState> networkState) {
        this.pagedList = pagedList;
        this.networkState = networkState;
    }

    public LiveData<PagedList<T>> getPagedList() {
        return pagedList;
    }

    public void setPagedList(LiveData<PagedList<T>> pagedList) {
        this.pagedList = pagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public void setNetworkState(LiveData<NetworkState> networkState) {
        this.networkState = networkState;
    }
}
