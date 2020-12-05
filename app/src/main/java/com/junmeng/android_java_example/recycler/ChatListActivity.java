package com.junmeng.android_java_example.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.junmeng.android_java_example.R;
import com.example.common.recycler.BaseSingleRecyclerAdapter;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.databinding.ActivityChatListBinding;

import java.util.List;

/**
 * 模仿聊天列表的效果
 */
public class ChatListActivity extends AppCompatActivity {

    ActivityChatListBinding binding;
    MyAdapter adapter;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainHandler = new Handler();
        initListView();
    }

    private void initListView() {
        adapter = new MyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.list.setLayoutManager(linearLayoutManager);
        binding.list.setAdapter(adapter);
        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                handlerLinearLayoutManagerStack(recyclerView);
            }
        });

        //初始化数据
        for (int i = 0; i < 20; i++) {
            adapter.addData("");
        }
        //滚到底部
        adapter.smoothScrollToBottom(binding.list);
    }

    /**
     * 解决item高度动态变化后下移的问题
     *
     * @param recyclerView
     */
    private void handlerLinearLayoutManagerStack(@NonNull RecyclerView recyclerView) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int latestVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (firstVisibleItemPosition != 0) { //说明超出一屏
                    if (!linearLayoutManager.getStackFromEnd()) {
                        linearLayoutManager.setStackFromEnd(true);
                    }
                } else {
                    if (latestVisibleItemPosition == adapter.getItemCount() - 1) {//说明目前item个数没撑满一屏
                        if (linearLayoutManager.getStackFromEnd()) {
                            linearLayoutManager.setStackFromEnd(false);
                        }
                    }

                }
            }
        });
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

    /**
     * 如果没有handlerLinearLayoutManagerStack的处理，你将看到内容高度变化后本来已经滚到底的列表被下移了
     * 如果有handlerLinearLayoutManagerStack的处理，你将看到内容高度变化后已经滚到底的列表仍然在底部
     *
     * @param view
     */
    public void onClickChange(View view) {
        try {
            adapter.getData().set(adapter.getItemCount() - 2, "如果列表不满一屏，item高度变化后会被撑开导致下移，" +
                    "如果列表超过一屏，你可以看到item被撑开，但是却是上移，这都是LinearLayoutManager.setStackFromEnd()的功劳！" +
                    "聊天列表基本都要这种效果。");
            adapter.notifyItemChanged(adapter.getItemCount() - 2);
        } catch (Exception ignore) {
        }
    }

    public class MyAdapter extends BaseSingleRecyclerAdapter<String> {

        @Override
        public int getItemLayoutResId() {
            return R.layout.item_simple_large;
        }

        @Override
        public void onBindView(RecyclerViewHolder holder, int position, String s, @NonNull List<Object> payloads) {
            holder.setText(R.id.text, "pos=" + position + ",content=" + s);
        }
    }
}