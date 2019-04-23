package com.kalugin.draggablegridview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalugin.draggablegridview.repository.UsersRepository;
import com.kalugin.draggablegridview.view_model.GithubViewModelFactory;
import com.kalugin.draggablegridview.view_model.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = getViewModel();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        initAdapter();
    }

    private void initAdapter() {
        UserAdapter userAdapter = new UserAdapter();
        recyclerView.setAdapter(userAdapter);
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(userAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.item_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.item_grid_spacing_small);
        recyclerView.addItemDecoration(new GridItemDecoration(largePadding, smallPadding));
        viewModel.result.observe(this, userAdapter::submitList);
    }


    private UserViewModel getViewModel() {
        return ViewModelProviders.of(this, new GithubViewModelFactory()).get(UserViewModel.class);
    }

}
