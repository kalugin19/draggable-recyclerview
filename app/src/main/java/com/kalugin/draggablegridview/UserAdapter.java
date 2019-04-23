package com.kalugin.draggablegridview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.Holder> {

    UserAdapter() {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getAvatarUrl().equals(newItem.getAvatarUrl()) && oldItem.getLogin().equals(newItem.getLogin()) && oldItem.getType().equals(newItem.getType());
            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        User user = getItem(position);
        holder.textView.setText(user.getLogin());
        Picasso.with(holder.avatar.getContext())
                .load(user.getAvatarUrl())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    void onItemDismiss(int position) {
        getCurrentList().snapshot().remove(position);
        notifyItemRemoved(position);
    }

    void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(getCurrentList().snapshot(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(getCurrentList().snapshot(), i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView avatar;

        Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item);
            avatar = itemView.findViewById(R.id.product_image);
        }
    }
}
