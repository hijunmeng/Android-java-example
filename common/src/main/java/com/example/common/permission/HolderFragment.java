package com.example.common.permission;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 可用于onRequestPermissionsResult和onActivityResult的回调监听
 * 用法可以参考{@link AbstractPermissionHandler }
 */
public class HolderFragment extends Fragment {

    public interface OnResultListener {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }

    public static class SimpleOnResultListener implements OnResultListener {
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        }
    }

    private OnResultListener mOnResultListener;

    private HolderFragment(@Nullable OnResultListener onResultListener) {
        mOnResultListener = onResultListener;
    }

    public static HolderFragment newInstance(@Nullable OnResultListener onResultListener) {
        return new HolderFragment(onResultListener);
    }

    /**
     * 需要监听result回调时调用此
     * @param activity
     */
    public void attachToActivity(@NonNull FragmentActivity activity) {
        HolderFragment fragment = getAttachHolderFragment(activity);
        if (fragment == this) {
            return;
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(this, this.getClass().getSimpleName())
                .commitNowAllowingStateLoss();
    }

    @Nullable
    public HolderFragment getAttachHolderFragment(@NonNull FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        HolderFragment fragment = (HolderFragment) fragmentManager.findFragmentByTag(this.getClass().getSimpleName());
        return fragment;
    }

    /**
     * 不再需要监听result回调时调用此
     * @param activity
     */
    public void detachToActivity(@NonNull FragmentActivity activity) {
        HolderFragment fragment = getAttachHolderFragment(activity);
        if (fragment == this) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .remove(this)
                    .commitNowAllowingStateLoss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mOnResultListener != null) {
            mOnResultListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mOnResultListener != null) {
            mOnResultListener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
