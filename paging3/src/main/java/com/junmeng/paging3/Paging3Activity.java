package com.junmeng.paging3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Paging3Activity extends AppCompatActivity {
    private static final String TAG = "Paging3Activity";
    private RecyclerView rvList;
    private ExampleAdapter exampleAdapter;
    private ExampleViewModel exampleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging3);
        rvList = findViewById(R.id.rvList);



        ViewModelProvider vp = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        exampleViewModel = vp.get(ExampleViewModel.class);

    }

    public void onClickVertical(View view) {
        rvList.setAdapter(exampleAdapter = new ExampleAdapter(new ExampleAdapter.Diff()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        exampleViewModel.getFlowable().subscribe(stringPagingData -> {
            exampleAdapter.submitData(getLifecycle(), stringPagingData);
        }, throwable -> {
            Toast.makeText(this, "异常" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });

        exampleAdapter.addLoadStateListener(loadStates -> {
            if(loadStates.getRefresh() instanceof LoadState.Loading){
                Log.i(TAG,"loading");
            }else if(loadStates.getRefresh() instanceof LoadState.Error){
                Log.i(TAG,"error");
            }else if(loadStates.getRefresh() instanceof LoadState.NotLoading){
                Log.i(TAG,"notloading");
            }
            return null;
        });
        exampleAdapter.withLoadStateHeaderAndFooter(new ExampleLoadStateAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }),new ExampleLoadStateAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));

    }

    public void onClickHorizontal(View view) {
        GridAdapter gridAdapter = new GridAdapter(new ExampleAdapter.Diff());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(gridAdapter);
        rvList.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        exampleViewModel.getFlowable().subscribe(stringPagingData -> {
            gridAdapter.submitData(getLifecycle(), stringPagingData);
        }, throwable -> {
            Toast.makeText(this, "异常" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void onClickGrid(View view) {
        GridAdapter gridAdapter = new GridAdapter(new ExampleAdapter.Diff());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvList.setLayoutManager(gridLayoutManager);
        rvList.setAdapter(gridAdapter);
        exampleViewModel.getFlowable().subscribe(stringPagingData -> {
            gridAdapter.submitData(getLifecycle(), stringPagingData);
        }, throwable -> {
            Toast.makeText(this, "异常" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}