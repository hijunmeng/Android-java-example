package com.junmeng.android_java_example;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
    //推荐在此处创建线程池
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public Handler mainThreadHandler =  HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
