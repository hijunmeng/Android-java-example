package com.example.common.base;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.common.BuildConfig;


/**
 * BaseFragmentDelegate的真正实现者
 */
public class BaseFragmentSimple implements IBaseFragment, LifecycleObserver {

    private Fragment mHost;

    @SuppressLint("RestrictedApi")
    public BaseFragmentSimple(@NonNull Fragment fragment) {
        this.mHost = Preconditions.checkNotNull(fragment, "fragment == null");
        mHost.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //todo 释放资源
        mHost = null;
    }


    @Override
    public void showToast(final String text) {
        if (mHost.getActivity() == null) {
            return;
        }
        mHost.getActivity().runOnUiThread(() -> {
            Toast.makeText(mHost.getActivity(), text, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void showDebugToast(final String text) {
        if (mHost.getActivity() == null || !BuildConfig.DEBUG) {
            return;
        }
        mHost.getActivity().runOnUiThread(() -> {
            Toast.makeText(mHost.getActivity(), text, Toast.LENGTH_SHORT).show();
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
