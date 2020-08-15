package com.junmeng.android_java_example.frags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivity;

public class FragContainerActivity extends BaseActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_container);
        mFragmentManager = getSupportFragmentManager();

        if(savedInstanceState!=null){
            //savedInstanceState不为空表明是系统恢复创建（例如屏幕旋转的情况），则要对原先的fragment进行判断，如果已经存在则不要重新创建，否则会导致有多个fragment实例对象
           Fragment fragment= mFragmentManager.findFragmentByTag(AFragment.class.getSimpleName());
            if(fragment!=null){
                mFragmentManager.beginTransaction()
                        .show(fragment)//这里直接show即可，如果用add则会抛异常，因为之前已经add过了,同一个实例不能add两次
//                        .replace(R.id.frag_container,fragment,AFragment.class.getSimpleName())//这里也可以用replace
                        .commitAllowingStateLoss();
            }

        } else{
            mFragmentManager.beginTransaction()
                    .add(R.id.frag_container, AFragment.newInstance("", ""), AFragment.class.getSimpleName())
                    .addToBackStack("")
                    .commitAllowingStateLoss();
        }


    }
    
}