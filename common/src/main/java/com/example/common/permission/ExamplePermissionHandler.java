package com.example.common.permission;

import android.Manifest;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.common.utils.SystemSettingPageUtil;

import java.util.List;

/**
 * AbstractPermissionHandler的一个示例
 */
public class ExamplePermissionHandler extends AbstractPermissionHandler {

    private String[] permissions = new String[]{Manifest.permission.CAMERA};

    public ExamplePermissionHandler(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public String[] getRequestPermissions() {
        return permissions;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(List<String> permissions, Callback callback) {
        new AlertDialog.Builder(mActivity)
                .setMessage("向用户解释下为何要用到相机权限")
                .setNegativeButton("取消", null)
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onAgree();
                        }
                    }
                })
                .create()
                .show();
        return true;
    }

    @Override
    public void onAlwaysRejectGrant() {
        new AlertDialog.Builder(mActivity)
                .setMessage("向用户解释下为何要用到相机权限，现在需要用户手动去开启权限")
                .setNegativeButton("取消", null)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SystemSettingPageUtil.gotoAppDetailsSettingPage(mActivity);
                    }
                })
                .create()
                .show();
    }
}
