package com.junmeng.android_java_example.recycler;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.junmeng.android_java_example.R;
import com.example.common.recycler.BaseSingleRecyclerAdapter;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.databinding.ActivityListTestBinding;

import java.util.List;

/**
 * 演示LinearLayoutManager.setReverseLayout()和LinearLayoutManager.setStackFromEnd不同组合在wrap_content和match_parent中的效果
 */
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
        initListView1();
        initListView2();
        initListView3();
        initListView4();
    }

    private void initListView1() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        binding.listWrap1.setLayoutManager(linearLayoutManager);
        binding.listWrap1.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager2.setStackFromEnd(true);
        binding.listMatch1.setLayoutManager(linearLayoutManager2);
        binding.listMatch1.setAdapter(adapter);
    }

    private void initListView2() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        binding.listWrap2.setLayoutManager(linearLayoutManager);
        binding.listWrap2.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager2.setStackFromEnd(false);
        binding.listMatch2.setLayoutManager(linearLayoutManager2);
        binding.listMatch2.setAdapter(adapter);
    }

    private void initListView3() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.listWrap3.setLayoutManager(linearLayoutManager);
        binding.listWrap3.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        linearLayoutManager2.setStackFromEnd(true);
        binding.listMatch3.setLayoutManager(linearLayoutManager2);
        binding.listMatch3.setAdapter(adapter);
    }

    private void initListView4() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(false);
        binding.listWrap4.setLayoutManager(linearLayoutManager);
        binding.listWrap4.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        linearLayoutManager2.setStackFromEnd(false);
        binding.listMatch4.setLayoutManager(linearLayoutManager2);
        binding.listMatch4.setAdapter(adapter);
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