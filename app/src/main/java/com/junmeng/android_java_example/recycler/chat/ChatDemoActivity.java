package com.junmeng.android_java_example.recycler.chat;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.recycler.BaseRecyclerAdapter;
import com.junmeng.android_java_example.databinding.ActivityChatDemoBinding;

public class ChatDemoActivity extends BaseActivityDelegate {
    ActivityChatDemoBinding binding;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        binding.recyclerView.setAdapter(myAdapter = new MyAdapter());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        myAdapter.register(new BindViewOne());
        myAdapter.register(new BindViewTwo());
        myAdapter.setOnItemClickLitener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Object o) {
                showToast("position="+position);
            }
        });

        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                myAdapter.addData(new BindViewOne.Item(0, i + ""));
                myAdapter.addData(new BindViewTwo.Item(1, i + ""));
            } else {
                myAdapter.addData(new BindViewTwo.Item(0, i + ""));
                myAdapter.addData(new BindViewOne.Item(1, +i + ""));
            }
        }
        myAdapter.notifyDataSetChanged();
    }
}