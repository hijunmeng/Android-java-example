package com.example.common.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragmentDelegate extends Fragment implements IBaseFragment {
    public final String TAG = this.toString();
    public static final boolean isPrintLifecycle = true;//是否打印生命周期log

    private BaseFragmentSimple mBaseFragmentSimple = new BaseFragmentSimple(this);

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
     * 当在事务中使用show或hide时，会回调此
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onSaveInstanceState: ");
        }
        super.onSaveInstanceState(outState);
    }

    ///////////////////////////////////扩展方法////////////////////////////////////////////
    @Override
    public void showToast(String text) {
        mBaseFragmentSimple.showToast(text);
    }

    @Override
    public void showDebugToast(String text) {
        mBaseFragmentSimple.showDebugToast(text);
    }

    @Override
    public void sleep(int ms) {
        mBaseFragmentSimple.sleep(ms);
    }

}