package com.junmeng.paging3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class GridAdapter extends PagingDataAdapter<String, GridAdapter.ExampleViewHolder> {
    public GridAdapter(@NotNull DiffUtil.ItemCallback<String> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gird, null, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        String item = getItem(position);
        holder.tvText.setText("pos="+position+","+item);
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
    }


    class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView tvText;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
        }
    }

    static class Diff extends DiffUtil.ItemCallback<String> {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }


}
