package com.junmeng.android_java_example.mediaprojection;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

/**
 * MediaProjectionManager需要api21及以上
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MediaProjectionActivity extends BaseActivityDelegate {
    private MediaProjectionManager mpm;
    private static final String TAG = "MediaProjectionActivity";

    private SurfaceView surfaceView;
    private VirtualDisplay virtualDisplay;

    private MediaProjectionBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_projection);
        surfaceView = findViewById(R.id.surfaceView);
        mpm = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

    }

    public void onClickStart(View view) {
        Intent intent = mpm.createScreenCaptureIntent();
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode != 123) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode != RESULT_OK) {
            showToast("用户拒绝录屏权限");
            return;
        }

        Intent service = new Intent(this, MediaProjectionService.class);
        service.putExtra("resultCode", resultCode);
        service.putExtra("data", data);

        bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder= (MediaProjectionBinder) service;
                binder.start(mpm,resultCode,data,surfaceView.getHolder().getSurface());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder=null;

            }
        }, BIND_AUTO_CREATE);

    }

    public void onClickStop(View view) {
        if (binder != null) {
            binder.stop();
        } else {
            showToast("binder is null");
        }
    }
}