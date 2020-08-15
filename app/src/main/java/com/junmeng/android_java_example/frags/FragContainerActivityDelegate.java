package com.junmeng.android_java_example.frags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

/**
 * 本例演示了
 * 如何解决Activity被系统重新创建时导致的fragment被实例化多次的问题，原因是Activity在恢复过程中会恢复之前的fragment，
 * 而在oncreate中如果没有加以判断，则又会新建一个实例
 *
 * add方法可以理解为多个fragment画面叠加，因此如果fragment背景是透明的话可以看到前个fragment的视图
 * replace方法可以理解为先清除之前的fragment的画面，因此之前的fragment画面不会影响本fragment，即使本fragment背景是透明的
 */
public class FragContainerActivityDelegate extends BaseActivityDelegate {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_container);
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            //savedInstanceState不为空表明是系统恢复创建（例如屏幕旋转的情况），则要对原先的fragment进行判断，如果已经存在则不要重新创建，否则会导致有多个fragment实例对象
            Fragment fragment = mFragmentManager.findFragmentByTag(AFragmentSimple.class.getSimpleName());
            if (fragment != null) {
                mFragmentManager.beginTransaction()
                        .show(fragment)//这里直接show即可，如果用add则会抛异常，因为之前已经add过了,同一个实例不能add两次
//                        .replace(R.id.frag_container,fragment,AFragment.class.getSimpleName())//这里也可以用replace
                        .commit();
            }

            mFragmentManager.executePendingTransactions();//使得事务立即执行，之后再调用其他api才可能获得正确数据，如getBackStackEntryCount

        } else {
            mFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, AFragmentSimple.newInstance("", ""), AFragmentSimple.class.getSimpleName())
                    .addToBackStack("")//addToBackStack则在回退时会执行
                    .commit();//不会立即执行，因此可以有多个commit；要立即执行可以用commitNow
            //      .commitNow();//会立即执行，因此不能有多个commitNow
        }


    }




    public void test(){
        Fragment fragment= AFragmentSimple.newInstance("", "");
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName())
                .commit();

        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName())
                .commit();

    }


}