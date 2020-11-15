package com.junmeng.paging3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class ExampleViewModel extends AndroidViewModel {
    public ExampleViewModel(@NonNull Application application) {
        super(application);
    }

    public Flowable<PagingData<String>> getFlowable() {
        // CoroutineScope helper provided by the lifecycle-viewmodel-ktx artifact.
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, String> pager = new Pager<>(
                new PagingConfig(/* pageSize = */ 20,20),//预取阈值不能为0
                () -> new ExamplePagingSource());

        Flowable<PagingData<String>> flowable = PagingRx.getFlowable(pager);
        return PagingRx.cachedIn(flowable, viewModelScope);

    }


}
