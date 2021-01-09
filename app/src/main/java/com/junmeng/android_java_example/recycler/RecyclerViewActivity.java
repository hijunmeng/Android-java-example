package com.junmeng.android_java_example.recycler;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.recycler.RecyclerItemClickListenerExt;
import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.recycler.bean.Bean1;
import com.junmeng.android_java_example.recycler.bean.Bean2;
import com.junmeng.android_java_example.recycler.bindview.Bean1BindView;
import com.junmeng.android_java_example.recycler.bindview.Bean2BindView;
import com.junmeng.android_java_example.recycler.bindview.Bean3BindView;
import com.junmeng.android_java_example.recycler.section.PinnedSectionDecoration;

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
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        testRecyclerAdapter = new TestRecyclerAdapter();
        testRecyclerAdapter.register(new Bean1BindView());
        testRecyclerAdapter.register( new Bean2BindView());
        testRecyclerAdapter.register( new Bean3BindView());
//        testRecyclerAdapter.register(Bean1.class, new Bean1BindView());
//        testRecyclerAdapter.register(Bean2.class, new Bean2BindView());
        recyclerView.setAdapter(testRecyclerAdapter);

        initGridRecyclerView();
//        initSectionRecyclerView();


    }

    public void initGridRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this));
//        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.addItemDecoration(new ViewItemDecoration(this));
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                showToast("onItemClick position" + position);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                showToast("onItemLongClick position" + position);
//            }
//        }));

//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener2<IRecyclerItemType>(recyclerView, new RecyclerItemClickListenerExt.OnItemClickListener<IRecyclerItemType>() {
//            @Override
//            public void onItemClick(View view, int position, IRecyclerItemType item) {
//                showToast("onItemClick item" + item.getItemType());
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position, IRecyclerItemType item) {
//                showToast("onItemLongClick item" + item.getItemType());
//            }
//        }));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListenerExt(recyclerView, new RecyclerItemClickListenerExt.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                showToast("onItemClick item123:" + item);
            }
        }));

//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.SimpleOnItemClickListener(){
//            @Override
//            public void onItemClick(View view, int position) {
//                showToast("onItemClick item123");
//            }
//        }));

        List list = new ArrayList<>();
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Bean2());
        list.add(new Bean1());
        list.add(new Object());
        list.add(new Object());

        testRecyclerAdapter.addAllData(list);
        testRecyclerAdapter.notifyDataSetChanged();
    }

    public void initSectionRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new PinnedSectionDecoration(this, new PinnedSectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return position % 2;
            }

            @Override
            public String getGroupFirstLine(int position) {
                return "" + position;
            }
        }));

        List list = new ArrayList<>();
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

    public void onClickList(View view) {
        gotoActivity(ListTestActivity.class);
    }

    public void onClickChatList(View view) {
        gotoActivity(ChatListActivity.class);
    }
}