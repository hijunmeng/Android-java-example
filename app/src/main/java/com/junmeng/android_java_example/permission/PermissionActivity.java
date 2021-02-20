package com.junmeng.android_java_example.permission;

import android.os.Bundle;
import android.view.View;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.permission.ExamplePermissionHandler;
import com.example.common.permission.IPermissionHandler;
import com.junmeng.android_java_example.R;

public class PermissionActivity extends BaseActivityDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void onClickCamera(View view) {
        new ExamplePermissionHandler(this).checkPermissions(true, new IPermissionHandler.PermissionsCallback() {
            @Override
            public void onAllGrantSuccess() {
                showToast("授予成功");
            }
        });
    }
    public void onClickAlertWindow(View view) {
        new AlertWindowPermissionHandler(this).checkPermissions(true, new IPermissionHandler.PermissionsCallback() {
            @Override
            public void onAllGrantSuccess() {
                showToast("授予成功");
            }
        });
    }

}