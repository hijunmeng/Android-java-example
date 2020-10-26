package com.junmeng.android_java_example.mediaprojection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.junmeng.android_java_example.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MediaProjectionService extends Service {
    public static final String CHANNEL_ID = "20000";
    private static final String TAG = "MediaProjectionService";
    private MediaProjectionManager mMediaProjectionManager;
    private VirtualDisplay mVirtualDisplay;
    private int mResultCode;
    private Intent mResultData;


    private HandlerThread mHandlerThread;
    private Handler mThreadHandler;
    private Handler mMainHandler;

    private ImageReader mImageReader;

    private MediaProjectionBinder mMediaProjectionBinder;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    public MediaProjectionService() {
        initHandler();
    }

    private void initHandler() {
        mHandlerThread = new HandlerThread("media projection thread");
        mHandlerThread.start();
        mThreadHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
        mMainHandler = new Handler();
    }


    @Override
    public IBinder onBind(Intent intent) {
        startForegroundService();
        initMediaProjection(intent);
        return mMediaProjectionBinder = new MediaProjectionBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForegroundService();

        initMediaProjection(intent);


        return super.onStartCommand(intent, flags, startId);

    }

    private void initMediaProjection(Intent intent) {
        mResultCode = intent.getIntExtra("code", -1);
        mResultData = intent.getParcelableExtra("data");
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mResultData);
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;

        //目前测试只能输出PixelFormat.RGBA_8888
        mImageReader = ImageReader.newInstance(mScreenWidth, mScreenHeight, PixelFormat.RGBA_8888, 2);
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Log.i(TAG, "onImageAvailable: reader==null? " + (reader == null));
                //获取最新的一帧的Image
                Image image = reader.acquireLatestImage();
                if (image == null) {

                    return;
                }
                if (mMediaProjectionBinder != null && mMediaProjectionBinder.getDataCallback() != null) {
                    mMediaProjectionBinder.getDataCallback().onData(image);
                }

                image.close();
//因为是ImageFormat.JPEG格式，所以 image.getPlanes()返回的数组只有一个，也就是第0个。
//                ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
//                byte[] bytes = new byte[byteBuffer.remaining()];
//                byteBuffer.get(bytes);
//                Log.i(TAG, "onImageAvailable: len="+bytes.length);

//                String fileName = null;
//                try {
//                    Date currentDate = new Date();
//                    SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
//                    File dir = getExternalFilesDir(null);
//                    fileName = dir.getAbsolutePath() + "/" + date.format(currentDate) + ".png";
//                    FileOutputStream fos = new FileOutputStream(fileName);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }
        }, mThreadHandler);
        mVirtualDisplay = mediaProjection.createVirtualDisplay("media projection", mImageReader.getWidth(), mImageReader.getHeight(), outMetrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mImageReader.getSurface(), new VirtualDisplay.Callback() {
                    @Override
                    public void onPaused() {
                        Log.i(TAG, "onPaused: ");
                    }

                    @Override
                    public void onResumed() {
                        Log.i(TAG, "onResumed: ");
                    }

                    @Override
                    public void onStopped() {
                        Log.i(TAG, "onStopped: ");
                        if (mImageReader != null) {
                            mImageReader.setOnImageAvailableListener(null, null);
                            mImageReader.close();
                        }
                    }
                }, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "普通渠道";//在系统设置里的通知里会展示为类别
            String description = "屏幕共享服务通知渠道";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startForegroundService() {

        createNotificationChannel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create notification channel
//            NotificationManager notificationManager=new NotificationManager(this,null);
            // Create notification builder.
            Intent notificationIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getService(this, 1212, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setColor(ContextCompat.getColor(this, android.R.color.white));
            builder.setContentTitle("这是一个前台服务");
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            startForeground(100000, notification);
            Log.d(TAG, "Start foreground service");
        } else {
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
