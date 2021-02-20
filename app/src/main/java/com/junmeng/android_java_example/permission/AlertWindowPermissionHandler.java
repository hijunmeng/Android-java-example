package com.junmeng.android_java_example.permission;

import android.Manifest;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.common.permission.AbstractPermissionHandler;
import com.example.common.utils.SystemSettingPageUtil;

import java.util.List;

/**
 * 悬浮窗权限
 */
public class AlertWindowPermissionHandler extends AbstractPermissionHandler {

    private String[] permissions = new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};//这个权限即使被授予了，在检查时checkSelfPermission接口还是返回未授权？？？

    public AlertWindowPermissionHandler(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public String[] getRequestPermissions() {
        return permissions;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(List<String> permissions, Callback callback) {
        new AlertDialog.Builder(mActivity)
                .setMessage("向用户解释下为何要用到悬浮窗权限")
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
                .setMessage("向用户解释下为何要用到悬浮窗权限，现在需要用户手动去开启权限")
                .setNegativeButton("取消", null)
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SystemSettingPageUtil.gotoAppDetailsSettingPage(mActivity);
                        SystemSettingPageUtil.gotoAppOverlayPermissionSettingPage(mActivity);
                    }
                })
                .create()
                .show();
    }
}
