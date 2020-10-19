package com.junmeng.android_java_example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.audio.AudioActivity;

/**
 * [创建通知  |  Android 开发者  |  Android Developers](https://developer.android.google.cn/training/notify-user/build-notification)
 * <p>
 * dependencies {
 * implementation "com.android.support:support-compat:28.0.0"
 * }
 * <p>
 * <uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />
 * <p>
 * 为了兼容，必须设置setPriority
 */
public class NotificationActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "10000";
    public static final int NOTIFICATION_ID = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        createNotificationChannel();
    }

    /**
     * 必须先创建通知渠道，然后才能在 Android 8.0 及更高版本上发布任何通知，因此应在应用启动时立即执行这段代码。
     * 反复调用这段代码是安全的，因为创建现有通知渠道不会执行任何操作
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "普通渠道";//在系统设置里的通知里会展示为类别
            String description = "这是一个普通渠道通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onClickSimple(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("只能显示一行内容")
                .setContentText("这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//指定通知的优先级，为了兼容，此项必须设置
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //通知id是每条通知独一无二的，如果id重复，则会覆盖旧的通知
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public void onClickLongContent(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("可展开查看更多内容")
                .setContentText("这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容，这是一段很长的通知内容"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//指定通知的优先级，为了兼容，此项必须设置
                ;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }


    public void onClickContentIntent(View view) {
        Intent intent = new Intent(this, AudioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//专用于响应通知的 Activity。用户在正常使用应用时不会无缘无故想导航到这个 Activity，因此该 Activity 会启动一个新任务，而不是添加到应用的现有任务和返回堆栈
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//用户点按通知后自动移除通知，只有设置了setContentIntent此项才生效
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public void onClickContentHightIntent(View view) {
        //需要在清单中增加<uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />
        Intent intent = new Intent(this, AudioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//专用于响应通知的 Activity。用户在正常使用应用时不会无缘无故想导航到这个 Activity，因此该 Activity 会启动一个新任务，而不是添加到应用的现有任务和返回堆栈
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)//用户点按通知后自动移除通知，只有设置了setContentIntent此项才生效
                .setFullScreenIntent(pendingIntent,true)
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public void onClickContentIntent2(View view) {
        //以下代码测试无效
//        //在清单文件中配合android:parentActivityName
        Intent resultIntent = new Intent(this, AudioActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(AudioActivity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
// Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("保留返回栈")

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);//用户点按通知后自动移除通知，只有设置了setContentIntent此项才生效

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }


}