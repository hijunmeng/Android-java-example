package com.example.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;

public class SystemSettingPageUtil {
    /**
     * 跳转到应用悬浮窗权限设置页
     *
     * @param context
     */
    public static void gotoAppOverlayPermissionSettingPage(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 跳转到系统投屏设置页
     *
     * @param context
     */
    public static void gotoCastSettingPage(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_CAST_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转到应用权限、通知等设置页
     *
     * @param context
     */
    public static void gotoAppDetailsSettingPage(@NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
