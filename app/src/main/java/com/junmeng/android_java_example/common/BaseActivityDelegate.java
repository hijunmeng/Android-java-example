package com.junmeng.android_java_example.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.junmeng.android_java_example.R;

public class BaseActivityDelegate extends AppCompatActivity implements IBaseActivity{
    public final String TAG = this.toString();

    public static final boolean isPrintLifecycle = true;//是否打印生命周期log

    private BaseActivitySimple mBaseActivitySimple =new BaseActivitySimple(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onCreate: savedInstanceState==null?" + (savedInstanceState == null));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }

    /**
     * android:launchMode="standard"时不会回调此
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onNewIntent: ");
        }
        super.onNewIntent(intent);
    }

    /**
     * 在锁屏或home键后再回来时会回调此，然后onStart
     */
    @Override
    protected void onRestart() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onRestart: ");
        }
        super.onRestart();
    }

    @Override
    protected void onStart() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onStart: ");
        }
        super.onStart();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onAttachFragment: ");
        }
        super.onAttachFragment(fragment);
    }


    @Override
    protected void onResume() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onResume: ");
        }
        super.onResume();
    }

    /**
     * 在onResume后回调此，只会回调一次
     */
    @Override
    public void onAttachedToWindow() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onAttachedToWindow: ");
        }
        super.onAttachedToWindow();
    }


    @Override
    protected void onPause() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onPause: ");
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onStop: ");
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onDestroy: ");
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isPrintLifecycle) {
            Log.i(TAG, "onBackPressed: ");
        }
        //如果fragment没消费，则还给Activity
        if(dispatchBackPressedEvent()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (isPrintLifecycle) {
            Log.i(TAG, "onSaveInstanceState: ");
        }
        super.onSaveInstanceState(outState);
    }

    /////////////////////////////扩展方法/////////////////////////////////////////////

    @Override
    public void showToast(String text) {
        mBaseActivitySimple.showToast(text);
    }

    @Override
    public void showDebugToast(String text) {
        mBaseActivitySimple.showDebugToast(text);
    }

    @Override
    public void sleep(int ms) {
        mBaseActivitySimple.sleep(ms);
    }

    @Override
    public boolean dispatchBackPressedEvent() {
        return mBaseActivitySimple.dispatchBackPressedEvent();
    }

    @Override
    public void gotoActivity(Class<?> cls, boolean isFinishCurrent) {
        mBaseActivitySimple.gotoActivity(cls,isFinishCurrent);
    }

    @Override
    public void gotoActivity(Class<?> cls) {
        mBaseActivitySimple.gotoActivity(cls);
    }
}