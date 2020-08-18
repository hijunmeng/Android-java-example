package com.junmeng.android_java_example.anim.animatelayout;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.R;

import java.util.ArrayList;
import java.util.List;

import static android.animation.LayoutTransition.CHANGE_APPEARING;

/**
 * LayoutTransition可作用子view下的子view，因此直接在xml的根view设置android:animateLayoutChanges="true"即可
 * [布局动画--LayoutTransition - 简书](https://www.jianshu.com/p/60d632fe8f71)
 */
public class AnimateLayoutActivity extends AppCompatActivity {

    ViewGroup rootView;
    ImageView imageView;
    ViewGroup container;

    List<View> views = new ArrayList<>();
    List<View> view2s = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_layout);
        rootView = findViewById(R.id.root);
        imageView = findViewById(R.id.image);
        container = findViewById(R.id.container);


    }

    public LayoutTransition getCustomLayoutTransition() {
        LayoutTransition lt = new LayoutTransition();
        //动画时间
        lt.setDuration(2000);

        //动画延时
        lt.setStartDelay(CHANGE_APPEARING, 1000);

        //添加View时过渡动画效果
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "rotationY", 0, 90, 0).
                setDuration(lt.getDuration(LayoutTransition.APPEARING));
        lt.setAnimator(LayoutTransition.APPEARING, addAnimator);

        //移除View时过渡动画效果
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0, -90, 0).
                setDuration(lt.getDuration(LayoutTransition.DISAPPEARING));
        lt.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);

        /**
         *LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING的过渡动画效果
         * 必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！使用ObjectAnimator是行不通的,
         * 发现这点时真特么恶心,但没想到更恶心的在后面,在测试效果时发现在构造动画时，”left”、”top”、”bottom”、”right”属性的
         * 变动是必须设置的,至少设置两个,不然动画无效,问题是我们即使这些属性不想变动!!!也得设置!!!
         * 我就问您恶不恶心!,因为这里不想变动,所以设置为(0,0)
         *
         */
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 0);


        /**
         * view被添加时,其他子View的过渡动画效果
         */
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft,  pvhBottom, animator).
                setDuration(lt.getDuration(LayoutTransition.CHANGE_APPEARING));

        lt.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);


        /**
         * view移除时，其他子View的过渡动画
         */
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhBottom, pvhRotation).
                setDuration(lt.getDuration(LayoutTransition.CHANGE_DISAPPEARING));

        lt.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);



       //view 动画改变时，布局中的每个子view动画的时间间隔
        lt.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        lt.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        return lt;
    }

    public void onClickAdd(View view) {
        TextView tv2 = new TextView(this);
        tv2.setText(Math.random() + "");
        container.addView(tv2);
        view2s.add(tv2);
        TextView tv = new TextView(this);
        tv.setText(Math.random() + "");
        rootView.addView(tv);
        views.add(tv);



    }

    public void onClickRemove(View view) {
        if (views.size() <= 0) {
            return;
        }
        View v = views.get(0);
        rootView.removeView(v);
        views.remove(v);

        View v2 = view2s.get(0);
        container.removeView(v2);
        view2s.remove(v2);
    }

    public void onClickToggleImageVisibility(View view) {
        if (imageView.isShown()) {
            imageView.setVisibility(View.GONE);

        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void onClickCustom(View view) {
        rootView.setLayoutTransition(getCustomLayoutTransition());
    }

    public void onClickMoveImage(View view) {
        imageView.setTranslationX(100);
    }
}