package com.junmeng.android_java_example.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.List;

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

    @Override
    public boolean dispatchBackPressedEvent() {
        if(!(mHost instanceof FragmentActivity)){
            return false;
        }
        FragmentManager fm=(( FragmentActivity)mHost).getSupportFragmentManager();
        if(fm==null){
            return false;
        }
        List<Fragment> frags=fm.getFragments();
        if(frags==null){
            return false;
        }
        for(Fragment it:frags){
            if(it!=null&& it instanceof IFragmentBackPressed&&it.isVisible()){//事件分发给当前可见的实现了IFragmentBackPressed接口的fragment
                return ((IFragmentBackPressed)it).onFragmentBackPressed();
            }
        }
        return false;
    }
}
