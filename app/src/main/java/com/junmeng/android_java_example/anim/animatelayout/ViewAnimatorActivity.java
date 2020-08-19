package com.junmeng.android_java_example.anim.animatelayout;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

/**
 * 视图动画（又称补间动画）的一大特点就是变化后的视图点击事件实际上还是在原来的区域
 */
public class ViewAnimatorActivity extends BaseActivityDelegate {

    ImageView imageView;
    ConstraintLayout pImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView = findViewById(R.id.imageView);
        pImage = findViewById(R.id.pImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用于检测原视图对象位置是否也跟随动画变化
                showToast("click");
            }
        });

    }

    public void onClickDefaultScale(View view) {

        //默认从左上角开始缩放
        ScaleAnimation sa = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
        sa.setDuration(1000);
        sa.setFillAfter(true);//是否保留结束后的动画视图

        //重复类型，有reverse和restart两个值，reverse表示倒序回放，restart表示重新放一遍，必须与repeatCount一起使用
        sa.setRepeatMode(Animation.RESTART);
        sa.setRepeatCount(1);//1会执行两次动画，依此类推
        imageView.startAnimation(sa);
    }

    //以自身中心点进行缩放
    public void onClickCenterScale(View view) {

        //计算公式：基准点x=自身视图宽度*pivot+自身视图左上角x坐标；基准点y=自身视图高度*pivot+自身视图左上角y坐标；
        // 0.5的话计算出的坐标刚好是视图中心点的绝对坐标，因此也就按中心点缩放了
        ScaleAnimation sa = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //计算公式：基准点x=父视图宽度*pivot+自身视图左上角x坐标；基准点y=父视图高度*pivot+自身视图左上角y坐标；
        //现在按中心点缩放
        //float pivotX=imageView.getWidth()/(2.0f*pImage.getWidth());
        //float pivotY=imageView.getHeight()/(2.0f*pImage.getHeight());
        //ScaleAnimation sa=new ScaleAnimation(1.0f,2.0f,1.0f,2.0f,Animation.RELATIVE_TO_PARENT,pivotX,Animation.RELATIVE_TO_PARENT,pivotY);

        //计算公式：基准点x=pivot+自身视图左上角x坐标；基准点y=pivot+自身视图左上角y坐标；
        //现在按中心点进行缩放
        //ScaleAnimation sa=new ScaleAnimation(1.0f,2.0f,1.0f,2.0f,Animation.ABSOLUTE,imageView.getWidth()/2,Animation.ABSOLUTE,imageView.getHeight()/2);

        sa.setDuration(2000);
        sa.setFillAfter(true);
        imageView.startAnimation(sa);
    }


    public void onClickAnimSet(View view) {
        ScaleAnimation sa = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(sa);
        set.addAnimation(ta);
        set.setDuration(2000);
        set.setFillAfter(true);

        imageView.startAnimation(set);
    }
}