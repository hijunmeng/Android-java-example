package com.example.common.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限处理器的抽象类，用户可继承此类实现自己需要的权限，可参考{@link ExamplePermissionHandler}
 */
public abstract class AbstractPermissionHandler implements IPermissionHandler {

    public static final int REQUEST_CODE_PERMISSION = 10088;

    private HolderFragment mHolderFragment;
    public FragmentActivity mActivity;
    private PermissionsCallback mPermissionsCallback;

    public AbstractPermissionHandler(@NonNull FragmentActivity activity) {
        mActivity = activity;
    }

    private HolderFragment.OnResultListener mOnResultListener = new HolderFragment.SimpleOnResultListener() {
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            detachHolderFragment();
            if (permissions == null || permissions.length == 0) {
                return;
            }
            if (requestCode == REQUEST_CODE_PERMISSION) {
                List<String> shouldShowRequestPermissions = new ArrayList<String>();
                List<String> unGrantedPermissions = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        unGrantedPermissions.add(permissions[i]);
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[i])) {
                        shouldShowRequestPermissions.add(permissions[i]);
                    }
                }
                if (!unGrantedPermissions.isEmpty()) {//说明未全部授予
                    if (!shouldShowRequestPermissions.isEmpty()) {
                        //可以在此处展示权限解释对话框
                        shouldShowRequestPermissionRationale(shouldShowRequestPermissions, new Callback() {
                            @Override
                            public void onAgree() {
                                requestPermission(unGrantedPermissions);
                            }
                        });
                    } else {//到了这里可以认为用户勾选了拒绝不再提示
                        onAlwaysRejectGrant();//可以在此处实现跳转到应用权限设置页让用户手动设置
                    }
                    return;
                }
                onAllGrantSuccess();
            }
        }
    };

    @Override
    public boolean checkPermissions(boolean isAutoRequestPermissionWhenUngranted, @Nullable PermissionsCallback callback) {
        mPermissionsCallback = callback;
        String[] permissions = getRequestPermissions();
        if (permissions == null || permissions.length == 0) {
            onAllGrantSuccess();
            return true;
        }

        boolean isAllGranted = true;

        List<String> unGrantedPermissions = new ArrayList<>();
        for (String it : permissions) {
            if (!isPermissionGranted(mActivity, it)) {
                unGrantedPermissions.add(it);
                isAllGranted = false;
            }
        }
        if (isAllGranted) {
            onAllGrantSuccess();
            return true;
        }

        if (isAutoRequestPermissionWhenUngranted) {
            requestPermission(unGrantedPermissions);
        }
        return false;
    }

    private void attachHolderFragment() {
        if (mActivity == null) {
            return;
        }
        if (mHolderFragment == null) {
            mHolderFragment = HolderFragment.newInstance(mOnResultListener);
        }
        mHolderFragment.attachToActivity(mActivity);
    }

    private void detachHolderFragment() {
        if (mHolderFragment != null && mActivity != null) {
            mHolderFragment.detachToActivity(mActivity);
        }
    }

    private boolean isPermissionGranted(Context context, String permission) {
        int result = ActivityCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission(List<String> unGrantedPermissions) {
        attachHolderFragment();
        String[] array = new String[unGrantedPermissions.size()];
        unGrantedPermissions.toArray(array);
        mHolderFragment.requestPermissions(array, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onAllGrantSuccess() {
        if (mPermissionsCallback != null) {
            mPermissionsCallback.onAllGrantSuccess();
        }
    }
}
