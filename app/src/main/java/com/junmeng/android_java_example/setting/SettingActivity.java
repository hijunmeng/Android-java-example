package com.junmeng.android_java_example.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void onClickCast(View view) {
//        Settings.Global.putInt(getContentResolver(),Settings.Global.di, flag);
        Intent intent = new Intent(Settings.ACTION_CAST_SETTINGS);
        startActivity(intent);
    }

    public void onClickDisplay(View view) {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        startActivity(intent);
    }

    public void onClickWireless(View view) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    public void onClickWifi(View view) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }
    public void onClickWifiIP(View view) {
        Intent intent = new Intent(Settings.ACTION_WIFI_IP_SETTINGS);
        startActivity(intent);
    }
}