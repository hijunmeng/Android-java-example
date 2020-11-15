package com.junmeng.paging3;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ExamplePagingSource extends RxPagingSource<Integer, String> {
    private static final String TAG = "ExamplePagingSource";
    @NotNull
    @Override
    public Single<LoadResult<Integer, String>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        // Start refresh at page 1 if undefined.
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 0;
        }
        Log.i(TAG,"loadSingle:nextPageNumber="+nextPageNumber);
        //模拟从网络加载数据
        int finalNextPageNumber = nextPageNumber;
        finalNextPageNumber++;
        int finalNextPageNumber1 = finalNextPageNumber;
        Observable<ExampleResponse> source = Observable.create(emitter -> {
            ExampleResponse res = new ExampleResponse();
            res.pageNumber = finalNextPageNumber1;
            if(finalNextPageNumber1==8){
//                throw new Exception("xxx");
                emitter.onError(new Exception("xxx"));
            }
            res.datas = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                res.datas.add("str" + i);
            }
            Thread.sleep(2000);
            emitter.onNext(res);
            emitter.onComplete();
        });

        return source
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .singleOrError()
                .onErrorReturn(LoadResult.Error::new);

    }

    /**
     * false表示不支持重复的key,即LoadParams.getKey()重复的话就会报异常
     * @return
     */
    @Override
    public boolean getKeyReuseSupported() {
        return true;
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
