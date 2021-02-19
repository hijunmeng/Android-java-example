package com.example.common;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 可用于onRequestPermissionsResult和onActivityResult的回调监听
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

    public static void attachToActivity(FragmentActivity activity, HolderFragment holderFragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        HolderFragment fragment = (HolderFragment) fragmentManager.findFragmentByTag(holderFragment.getClass().getSimpleName());
        if (fragment != null) {
            return;
        }
        fragmentManager.beginTransaction()
                .add(holderFragment, holderFragment.getClass().getSimpleName())
                .commitNowAllowingStateLoss();
    }

    public static void detachToActivity(FragmentActivity activity, HolderFragment holderFragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(holderFragment)
                .commitNowAllowingStateLoss();
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
