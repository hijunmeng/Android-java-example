package com.junmeng.android_java_example.mediaprojection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.common.base.BaseActivityDelegate;
import com.junmeng.android_java_example.R;
import com.example.common.utils.NotificationUtil;

import java.nio.ByteBuffer;

/**
 * MediaProjectionManager需要api21及以上
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MediaProjectionActivity extends BaseActivityDelegate {
    private MediaProjectionManager mMediaProjectionManager;
    private static final String TAG = "MediaProjectionActivity";

    private SurfaceView surfaceView;
    private ImageView captureView;

    private MediaProjectionBinder binder;

    private ServiceConnection mServiceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_projection);
        surfaceView = findViewById(R.id.surfaceView);
        captureView = findViewById(R.id.iv_capture);
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

    }

    public void onClickStart(View view) {
        Intent intent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(intent, 123);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        try {
            WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            mWindowManager.getDefaultDisplay().getMetrics(metrics);
        } catch (Exception e) {
            Log.e(TAG, "MediaProjection error");
        }
        Intent service = new Intent(this, MediaProjectionService.class);
        service.putExtra("resultCode", resultCode);
        service.putExtra("data", data);

//        startService(service);
        bindService(service, mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (MediaProjectionBinder) service;
                binder.setDataCallback(new MediaProjectionBinder.DataCallback() {
                    @Override
                    public void onData(Image image) {
                        try{
                            Image.Plane[] planes = image.getPlanes();
                            ByteBuffer buffer = planes[0].getBuffer();
                            int pixelStride = planes[0].getPixelStride();
                            int rowStride = planes[0].getRowStride();
                            int rowPadding = rowStride - pixelStride * image.getWidth();
                            Bitmap bitmap = Bitmap.createBitmap(image.getWidth() + rowPadding / pixelStride,
                                    image.getHeight(), Bitmap.Config.ARGB_8888);
                            bitmap.copyPixelsFromBuffer(buffer);
                            updateCaptureView(bitmap);
                        }catch(Exception e){
                           e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder = null;

            }
        }, BIND_AUTO_CREATE);


    }

    private void updateCaptureView(Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                captureView.setImageBitmap(bitmap);
            }
        });

    }

    public void onClickStop(View view) {
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
            mServiceConnection = null;

        }

    }

    public void onClickCheckNotification(View view) {
        if (!NotificationUtil.isNotificationEnabled(this)) {
            NotificationUtil.gotoNotificationSettingPage(this);
        }
    }
}