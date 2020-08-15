package com.junmeng.android_java_example.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
/**
 * BaseActivityDelegate的真正实现者
 */
public class BaseActivitySimple implements IBaseActivity, LifecycleObserver {

    private Activity mHost;

    @SuppressLint("RestrictedApi")
    public BaseActivitySimple(@NonNull Activity activity) {
        this.mHost = Preconditions.checkNotNull(activity, "activity == null");
        if (mHost instanceof AppCompatActivity) {
            ((AppCompatActivity) mHost).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //todo 释放资源
        mHost=null;
    }


    @Override
    public void showToast(String text) {
        this.mHost.runOnUiThread(() -> {
            Toast.makeText(mHost, text, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
