package com.kalugin.draggablegridview.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.kalugin.draggablegridview.NetworkState;
import com.kalugin.draggablegridview.User;
import com.kalugin.draggablegridview.repository.UsersRepository;

public class UserViewModel extends ViewModel {

    public final LiveData<PagedList<User>> result;
    public final LiveData<NetworkState> networkState;

    UserViewModel(UsersRepository usersRepository) {
        this.result = usersRepository.loadUsers().getPagedList();
        this.networkState = usersRepository.loadUsers().getNetworkState();
    }

}
