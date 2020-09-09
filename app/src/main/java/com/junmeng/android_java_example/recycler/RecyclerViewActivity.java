package com.junmeng.android_java_example.recycler;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.common.recycler.IRecyclerItemType;
import com.junmeng.android_java_example.recycler.bean.Bean1;
import com.junmeng.android_java_example.recycler.bean.Bean2;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivityDelegate {


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TestRecyclerAdapter testRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerView=findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        testRecyclerAdapter=new TestRecyclerAdapter();
        recyclerView.setAdapter(testRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        List<IRecyclerItemType> list=new ArrayList<>();
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());

        testRecyclerAdapter.addAllData(list);
        testRecyclerAdapter.notifyDataSetChanged();
    }
}