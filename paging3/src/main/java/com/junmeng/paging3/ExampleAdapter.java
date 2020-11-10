package com.junmeng.paging3;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ExampleAdapter extends PagingDataAdapter<String, ExampleAdapter.ExampleViewHolder> {
    public ExampleAdapter(@NotNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExampleViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        String item = getItem(position);
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
       // holder.bind(item);
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
