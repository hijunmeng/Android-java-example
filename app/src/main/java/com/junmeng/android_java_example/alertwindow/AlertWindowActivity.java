package com.junmeng.android_java_example.alertwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.R;

public class AlertWindowActivity extends AppCompatActivity {


    View contentView;
    TextView textView;
    int clickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_window);
        textView=findViewById(R.id.tv_text);
        textView.setText("click count:"+clickCount);
    }

    public void onClickShow(View view) {
        clickCount++;
        textView.setText("click count:"+clickCount);
        // 安卓8.0以上需要申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
// 设置宽高
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
// 设置背景透明
        layoutParams.format = PixelFormat.TRANSPARENT;
// 设置屏幕左上角为起始点
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
// FLAG_LAYOUT_IN_SCREEN：将window放置在整个屏幕之内,无视其他的装饰(比如状态栏)； FLAG_NOT_TOUCH_MODAL：不阻塞事件传递到后面的窗口
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            // 设置窗体显示类型(TYPE_TOAST:与toast一个级别)
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
// 设置布局弹出的动画
//        layoutParams.windowAnimations = R.style.anim;
// 添加视图
        contentView = LayoutInflater.from(this).inflate(R.layout.alert_window, null);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlertWindowActivity.this, "点击悬浮窗", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AlertWindowActivity.this,AlertWindowActivity.class));
            }
        });
        windowManager.addView(contentView, layoutParams);
        contentView.getRootView().setOnTouchListener(new OEdOverLayMoveListener(windowManager, contentView));
        moveTaskToBack(true);//模式为singleInstance
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClickHide(null);
    }

    public void onClickHide(View view) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(contentView);
        contentView=null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                } else {
                    onClickShow(null);

                }
            }
        }
    }
}