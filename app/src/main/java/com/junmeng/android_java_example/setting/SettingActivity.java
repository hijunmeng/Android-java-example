package com.junmeng.android_java_example.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.R;
import com.example.common.utils.NotificationUtil;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    /**
     * 跳到投屏页
     *
     * @param view
     */
    public void onClickCast(View view) {
//        Settings.Global.putInt(getContentResolver(),Settings.Global., flag);
        Intent intent = new Intent(Settings.ACTION_CAST_SETTINGS);
        startActivity(intent);
    }


    /**
     * 跳到系统设置页
     *
     * @param view
     */
    public void onClickSetting(View view) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    /**
     * 跳到wlan页
     *
     * @param view
     */
    public void onClickWifi(View view) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    /**
     * 跳到应用通知设置页
     *
     * @param view
     */
    public void onClickNotification(View view) {
        NotificationUtil.gotoNotificationSettingPage(this);
    }

    /**
     * 跳到应用悬浮窗权限设置页
     *
     * @param view
     */
    public void onClickOverlay(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
            Toast.makeText(this, "api 小于26", Toast.LENGTH_SHORT).show();
        }
    }
}