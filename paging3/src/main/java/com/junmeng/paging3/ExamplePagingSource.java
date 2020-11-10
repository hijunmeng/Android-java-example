package com.junmeng.paging3;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ExamplePagingSource extends RxPagingSource<Integer, String> {
    @NotNull
    @Override
    public Single<LoadResult<Integer, String>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        // Start refresh at page 1 if undefined.
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }
        //模拟从网络加载数据
        Observable<ExampleResponse> source = Observable.create(emitter -> {
            ExampleResponse res = new ExampleResponse();
            res.pageNumber = 0;
            res.datas = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                res.datas.add("str" + i);
            }
            emitter.onNext(res);
            emitter.onComplete();
        });

        return source
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .singleOrError()
                .onErrorReturn(LoadResult.Error::new);

    }

    private LoadResult<Integer, String> toLoadResult(
            @NonNull ExampleResponse response) {
        return new LoadResult.Page<>(
                response.datas,
                null, // Only paging forward.
                response.pageNumber,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

}
