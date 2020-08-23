package com.junmeng.android_java_example.anim.animatelayout;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.anim.MyTypeEvaluator;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

/**
 * 属性动画演示
 */
public class PropertyAnimatorActivity extends BaseActivityDelegate {
    private static final String TAG = "PropertyActivity";
    ImageView imageView;
    TextView numberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        imageView = findViewById(R.id.image);
        numberView = findViewById(R.id.number);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("click");
            }
        });
    }

    public void onClickValue(View view) {

        ValueAnimator va = ValueAnimator.ofInt(0, 800, 50, 500);
        va.setDuration(3000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: value=" + value);
                imageView.setTranslationX(value);
            }
        });
        va.start();
    }

    public void onClickCustomTypeEvaluator(View view) {
        ValueAnimator va = ValueAnimator.ofObject(new MyTypeEvaluator(), new Point(0, 0), new Point(300, 400), new Point(0, 0));
        va.setDuration(3000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point p = (Point) animation.getAnimatedValue();
                imageView.setTranslationX(p.x);
                imageView.setTranslationY(p.y);
            }
        });
        va.start();
    }

    public void onClickObject(View view) {


        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return;

        }
        ObjectAnimator va = ObjectAnimator.ofArgb(imageView, "backgroundColor", Color.RED, Color.GREEN, Color.BLUE);
        //可以使用AnimatorListenerAdapter实现要监听指定的回调
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        va.setDuration(3000);
        va.start();
    }

    public void onClickAnimSet(View view) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator va1 = ObjectAnimator.ofFloat(imageView, "translationX", 0, 800, 50, 500);
        ObjectAnimator va2 = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 0.5f, 1.0f, 2.0f);
        ObjectAnimator va3 = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1, 0.5f, 1);
        set.play(va1).before(va2); //在va2之前播放va1
        set.play(va1).with(va3);//va1和va3一起播放
        set.setDuration(3000);
        set.start();
    }


    public void onClickStateList(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //代码设置StateListAnimator或者在xml中设置android:stateListAnimator="@xml/animate_scale"
        imageView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.xml.animate_scale));
    }

    public void onClickKeyFrame(View v){

        //fraction的取值为0.0~1.0，在此期间可以定义一些关键帧，至于关键帧中间的内容系统会自动计算
        //以下分别在fraction为0.0 0.5 1.0定义了三个关键帧
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhRotation);
        rotationAnim.setDuration(5000);
        rotationAnim.start();

    }
    public void onClickPropertyValuesHolder(View v){
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 100f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 50);
        ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY).start();
    }
    public void onClickViewPropertyAnimator(View v) {
        //推荐此种方式，高效又简洁
        imageView.animate().x(50f).y(100f);

    }

    public void onClickXmlAnimator(View v) {
        Animator a=AnimatorInflater.loadAnimator(this,R.animator.raise_number);
        a.setTarget(numberView);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                numberView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                numberView.setTextColor(Color.GRAY);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        a.start();

    }


}