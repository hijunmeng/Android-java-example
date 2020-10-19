package com.junmeng.android_java_example.mediaprojection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.junmeng.android_java_example.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MediaProjectionService extends Service {
    private static final String TAG = "MediaProjectionService";
    private MediaProjectionManager mpm;
    private VirtualDisplay virtualDisplay;
    private int mResultCode;
    private Intent mResultData;

    private IBinder binder;

    public MediaProjectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MediaProjectionBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mResultCode = intent.getIntExtra("code", -1);
//        mResultData = intent.getParcelableExtra("data");
//        mpm = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
//        MediaProjection mediaProjection = mpm.getMediaProjection(mResultCode, mResultData);

        startForegroundService();
        return super.onStartCommand(intent, flags, startId);

    }

    private void startForegroundService() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create notification channel
//            NotificationManager notificationManager=new NotificationManager(this,null);
            // Create notification builder.
            Intent notificationIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getService(this, 1212, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setColor(ContextCompat.getColor(this, android.R.color.white));
            builder.setContentTitle("这是一个前台服务");
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            startForeground(100000, notification);
            Log.d(TAG,"Start foreground service");
        }else{
            // 在API16之后，可以使用build()来进行Notification的构建 Notification
            Notification notification = new Notification.Builder(this.getApplicationContext())
                    .setContentText("这是一个前台服务")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .build();
            startForeground(100000, notification);
        }

    }
}
