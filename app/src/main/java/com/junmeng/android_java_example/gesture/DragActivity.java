package com.junmeng.android_java_example.gesture;

import android.os.Bundle;
import android.view.View;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

public class DragActivity extends BaseActivityDelegate {

    View maskView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        maskView=findViewById(R.id.fl_mask);
    }

    public void onClickImage(View view) {
        showToast("click image");
    }

    public void onClickLL(View view) {
        showToast("click linearlayout");
    }

    public void onClickMask(View view) {
        if(maskView.isShown()){

            maskView.setClickable(false);
            maskView.setVisibility(View.GONE);
        }else{
            maskView.setVisibility(View.VISIBLE);
        }
    }
}