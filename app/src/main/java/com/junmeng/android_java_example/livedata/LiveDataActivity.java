package com.junmeng.android_java_example.livedata;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.livedata.object.TestObject;
import com.junmeng.android_java_example.livedata.single.EventObserver;

public class LiveDataActivity extends BaseActivityDelegate {

    private LiveDataViewModel mLiveDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        mLiveDataViewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(LiveDataViewModel.class);

        //以下可以翻转屏幕看看toast的弹出情况，只有toastLiveData会再次弹出toast

        mLiveDataViewModel.toastLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });

        mLiveDataViewModel.toast2LiveData.observe(this, new EventObserver(new EventObserver.Listener<String>() {
            @Override
            public void onEventUnhandledContent(String s) {
                showToast(s);
            }
        }));

        mLiveDataViewModel.toast3LiveData.observe(this, new EventObserver(new EventObserver.Listener<String>() {
            @Override
            public void onEventUnhandledContent(String s) {
                showToast(s);
            }
        }));

        mLiveDataViewModel.toast4LiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
        mLiveDataViewModel.objectLiveData.observe(this, new Observer<TestObject>() {
            @Override
            public void onChanged(TestObject testObject) {
                showToast(testObject.toString());
            }
        });

    }


    public void onClickBtn(View view) {
        mLiveDataViewModel.onClickToast();
    }
}