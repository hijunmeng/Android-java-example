package com.junmeng.android_java_example.recycler;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.recycler.BaseSingleRecyclerAdapter;
import com.junmeng.android_java_example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.databinding.ActivityListTestBinding;

import java.util.List;

public class ListTestActivity extends AppCompatActivity {

    private ActivityListTestBinding binding;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListView();
    }

    private void initListView() {
        adapter = new MyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        binding.listWrap.setLayoutManager(linearLayoutManager);
        binding.listWrap.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager2.setStackFromEnd(true);
        binding.listMatch.setLayoutManager(linearLayoutManager2);
        binding.listMatch.setAdapter(adapter);
    }

    public void onClickRemove(View view) {
        if (adapter.getItemCount() > 0) {
            int pos = adapter.getItemCount() - 1;
            adapter.getData().remove(adapter.getItemCount() - 1);
            adapter.notifyItemRemoved(pos);
        }
    }

    public void onClickAdd(View view) {
        adapter.addData("");
    }

    public class MyAdapter extends BaseSingleRecyclerAdapter<String> {

        @Override
        public int getItemLayoutResId() {
            return R.layout.item_simple;
        }

        @Override
        public void onBindView(RecyclerViewHolder holder, int position, String s, @NonNull List<Object> payloads) {
            holder.setText(R.id.text, "pos=" + position);
        }
    }
}