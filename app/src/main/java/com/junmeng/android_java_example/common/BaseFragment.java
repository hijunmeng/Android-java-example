package com.junmeng.android_java_example.common;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junmeng.android_java_example.R;

public class BaseFragment extends Fragment implements IBaseFragment {
    public final String TAG = this.getClass().getSimpleName();
    public static final boolean isPrintLifecycle = true;//是否打印生命周期log

    private BaseFragmentDelegate mBaseFragmentDelegate = new BaseFragmentDelegate(this);

    @Override
    public void onAttach(@NonNull Context context) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onAttach: ");
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onCreate: ");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onAttachFragment: ");
        }
        super.onAttachFragment(childFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onCreateView: ");
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onActivityCreated: ");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onViewStateRestored: savedInstanceState==null?" + (savedInstanceState == null));
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onStart: ");
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onResume: ");
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onPause: ");
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onStop: ");
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onDestroyView: ");
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onDestroy: ");
        }
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onDetach: ");
        }
        super.onDetach();
    }

    /**
     * 单个fragment不会回调此
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onHiddenChanged: hidden=" + hidden);
        }
        super.onHiddenChanged(hidden);
    }

    ///////////////////////////////////扩展方法////////////////////////////////////////////
    @Override
    public void showToast(String text) {
        mBaseFragmentDelegate.showToast(text);
    }

}