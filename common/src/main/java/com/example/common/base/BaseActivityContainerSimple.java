package com.example.common.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.common.R;

import java.lang.ref.SoftReference;

/**
 * @Description: java类作用描述
 * @Author: hwj
 * @CreateDate: 2020/12/22 10:04
 */
public class BaseActivityContainerSimple implements IBaseActivityContainer, LifecycleObserver {
    private SoftReference<FragmentActivity> mHost;

    public BaseActivityContainerSimple(@NonNull FragmentActivity activity) {
        this.mHost = new SoftReference<FragmentActivity>(activity);
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //todo 释放资源
        mHost.clear();
    }

    @Override
    public int getContainerId() {
        //需要用户自己实现
        return R.id.container;
    }

    @Override
    public boolean isBackByStack() {
        return true;
    }

    @Override
    public void addFragmentWithoutAddToBackStack(@NonNull Fragment fragment) {
        turnToFragment(TYPE_ADD, fragment, fragment.getClass().getName(), false);
    }

    @Override
    public void addFragment(@NonNull Fragment fragment, boolean isAddToBackStack) {
        turnToFragment(TYPE_ADD, fragment, fragment.getClass().getName(), isAddToBackStack);
    }

    @Override
    public void replaceFragmentWithoutAddToBackStack(@NonNull Fragment fragment) {
        turnToFragment(TYPE_REPLACE, fragment, fragment.getClass().getName(), false);
    }

    @Override
    public void replaceFragment(@NonNull Fragment fragment, boolean isAddToBackStack) {
        turnToFragment(TYPE_REPLACE, fragment, fragment.getClass().getName(), isAddToBackStack);
    }

    @Override
    public void turnToFragment(int type, @NonNull Fragment fragment, boolean isAddToBackStack) {
        turnToFragment(type, fragment, fragment.getClass().getName(), isAddToBackStack);
    }

    @Override
    public void turnToFragment(int type, @NonNull Fragment fragment, @Nullable String tag, boolean isAddToBackStack) {
        if (mHost.get() == null) {
            return;
        }
        FragmentManager fragmentManager = mHost.get().getSupportFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        int containerId=getContainerId();
        if(mHost.get() instanceof IBaseActivityContainer){
            containerId=((IBaseActivityContainer)mHost.get()).getContainerId();
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        if (type == TYPE_REPLACE) {
            ft.replace(containerId, fragment, tag);

        } else {
            ft.add(containerId, fragment, tag);
        }
        ft.commit();
    }

    @Nullable
    @Override
    public Fragment getTopFragment() {
        if (mHost.get() == null) {
            return null;
        }
        FragmentManager fragmentManager = mHost.get().getSupportFragmentManager();
        int n = fragmentManager.getBackStackEntryCount();
        if (n > 0) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(n - 1);
            return fragmentManager.findFragmentByTag(entry.getName());
        }
        return null;
    }

    @Override
    public boolean popBackStack() {
        if (mHost.get() == null) {
            return false;
        }
        //如果栈中有fragment则栈顶出栈
        FragmentManager fragmentManager = mHost.get().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
            return true;
        } else if (fragmentManager.getBackStackEntryCount() == 1) {//如果栈中只有一个就销毁activity
            mHost.get().finish();
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressedDelegate() {
        //优先将返回事件传递给栈顶fragment
        Fragment fragment = getTopFragment();
        if (fragment != null && fragment instanceof BackPressListener && fragment.isVisible()) {
//            Log.i(TAG, "当前处于前台的fragment是" + fragment.getClass().getName());
            BackPressListener backPressListener = (BackPressListener) fragment;
            if (backPressListener.onFragmentBackPressed()) {
                return true;
            }
        }

        if (!isBackByStack()) {
            return false;
        }

        if (popBackStack()) {
            return true;
        }
        return false;
    }

}
