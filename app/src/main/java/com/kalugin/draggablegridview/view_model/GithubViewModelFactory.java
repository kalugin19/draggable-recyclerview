package com.kalugin.draggablegridview.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kalugin.draggablegridview.repository.UsersRepository;

public class GithubViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(UserViewModel.class)) {
            return (T) new UserViewModel(new UsersRepository());
        }
        throw new RuntimeException("Unknown ViewModel class");
    }
}
