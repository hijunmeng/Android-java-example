package com.junmeng.android_java_example.frags;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.common.base.BaseActivityDelegate;
import com.junmeng.android_java_example.R;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * 本例演示了
 * 如何解决Activity被系统重新创建时导致的fragment被实例化多次的问题，原因是Activity在恢复过程中会恢复之前的fragment，
 * 而在oncreate中如果没有加以判断，则又会新建一个实例
 *
 * add方法可以理解为多个fragment画面叠加，因此如果fragment背景是透明的话可以看到前个fragment的视图
 * replace方法可以理解为先清除之前的fragment的画面，因此之前的fragment画面不会影响本fragment，即使本fragment背景是透明的
 * addToBackStack被执行多少次，就需要回退多少次(不管是add,replace,show)
 *
 * 只要有addToBackStack，fragment就不会执行到onDestroy和onDetach
 *
 * commit不会立即执行，如果想立即执行可以使用commitNow或executePendingTransactions
 * popBackStack不会立即执行，如果想立即执行可以使用popBackStackImmediate或executePendingTransactions
 *
 *
 * popBackStack的flag参数为0表示将自身以上出栈，但保留自身在栈中，为POP_BACK_STACK_INCLUSIVE表示将自身及以上都出栈
 * addToBackStack时可以指定名字，这个名字可以用来popBackStack，如果栈中有同名的，则popBackStack会以最接近栈底的那个开始出栈
 *
 *
 *
 */
public class FragContainerActivity extends BaseActivityDelegate {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_container);
        mFragmentManager = getSupportFragmentManager();


        testWhenSavedInstanceStateNotNull(savedInstanceState);
//        testReplaceSameMore();
//        testAddMore();
//        testShowHide();
//        testReplaceMore();
//        test();
    }


    public void testWhenSavedInstanceStateNotNull(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            //savedInstanceState不为空表明是系统恢复创建（例如屏幕旋转的情况），则要对原先的fragment进行判断，如果已经存在则不要重新创建，否则会导致有多个fragment实例对象
            Fragment fragment = mFragmentManager.findFragmentByTag(AFragment.class.getSimpleName());
            if (fragment != null) {
                mFragmentManager.beginTransaction()
                        .show(fragment)//这里直接show即可，如果用add则会抛异常，因为之前已经add过了,同一个实例不能add两次
//                        .replace(R.id.frag_container,fragment,AFragment.class.getSimpleName())//这里也可以用replace
                        .commit();
            }

            mFragmentManager.executePendingTransactions();//使得事务立即执行，之后再调用其他api才可能获得正确数据，如getBackStackEntryCount

        } else {
            mFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, AFragment.newInstance("", ""), AFragment.class.getSimpleName())
//                    .addToBackStack(AFragment.class.getSimpleName())//addToBackStack则在回退时会执行
                    .commit();//不会立即执行，因此可以有多个commit；要立即执行可以用commitNow
            //      .commitNow();//会立即执行，因此不能有多个commitNow
        }
    }


    /**
     * 测试replace同一个实例3次
     *
     * 结果：不会报错；多次replace同一实例不会导致生命周期重新执行；第一二次物理返回键不会销毁fragment，第三次物理返回键才会销毁fragment
     */
    public void testReplaceSameMore(){
        Fragment fragment= AFragment.newInstance("", "");
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();


    }

    /**
     * 由于背景透明，画面会叠加在一起
     * 按物理返回键依次销毁C,B,A
     */
    public void testAddMore(){
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, AFragment.newInstance("", ""), AFragment.class.getSimpleName())
                .addToBackStack(AFragment.class.getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, BFragment.newInstance("", ""), BFragment.class.getSimpleName())
                .addToBackStack(BFragment.class.getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, CFragment.newInstance("", ""), CFragment.class.getSimpleName())
                .addToBackStack( CFragment.class.getSimpleName())
                .commit();


    }

    /**
     * 如果上个fragment没有addToBackStack，则replace会让上个fragment销毁（即detach），
     * 如果有addToBackStack，则replace会让上个fragment执行到onDestroyView但不会接着onDestroy
     */
    public void testReplaceMore(){
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, AFragment.newInstance("", ""), AFragment.class.getSimpleName())
                .addToBackStack(AFragment.class.getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, BFragment.newInstance("", ""), BFragment.class.getSimpleName())
                .addToBackStack(BFragment.class.getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .replace(R.id.frag_container, CFragment.newInstance("", ""), CFragment.class.getSimpleName())
                .addToBackStack( CFragment.class.getSimpleName())
                .commit();


    }

    /**
     * show和hide并不会改变fragment在栈中的顺序
     *
     */
    public void testShowHide(){
        Fragment a= AFragment.newInstance("", "");
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, a, a.getClass().getSimpleName())
                .addToBackStack(a.getClass().getSimpleName())
                .commit();

        Fragment b= BFragment.newInstance("", "");
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container,b, BFragment.class.getSimpleName())
                .addToBackStack(BFragment.class.getSimpleName())
                .commit();

        //第一次物理返回键会销毁b,此时界面还是a,第二次物理返回键会销毁a,此时a界面消失
        mFragmentManager.beginTransaction()
                .show(a)
                .hide(b)
                .commit();

        //第一次物理返回键会销毁b,此时b界面消失,但a界面还是隐藏的，第二次物理返回键会销毁a
//        mFragmentManager.beginTransaction()
//                .show(b)
//                .hide(a)
//                .commit();
    }

    public void test(){
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, AFragment.newInstance("", ""), AFragment.class.getSimpleName())
                .addToBackStack(AFragment.class.getSimpleName())
                .commit();
        sleep(200);

        Fragment b=BFragment.newInstance("", "");
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, b, b.getClass().getSimpleName())
                .addToBackStack(b.getClass().getSimpleName())
                .commit();
        sleep(200);
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, CFragment.newInstance("", ""), CFragment.class.getSimpleName())
                .addToBackStack( CFragment.class.getSimpleName())
                .commit();

        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, DFragment.newInstance("", ""), DFragment.class.getSimpleName())
                .addToBackStack( DFragment.class.getSimpleName())
                .commit();

        mFragmentManager.executePendingTransactions();//让commit的事务立即执行
        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());//4,4


//        mFragmentManager.popBackStack(b.getClass().getSimpleName(),0);//界面显示到fragment b
//        mFragmentManager.executePendingTransactions();
//        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());//2,2


        //POP_BACK_STACK_INCLUSIVE设置此标记，那么自身也会被出栈，为0则保留自身在栈中
//        mFragmentManager.popBackStack(b.getClass().getSimpleName(),POP_BACK_STACK_INCLUSIVE);//界面显示到fragment a
//        mFragmentManager.executePendingTransactions();
//        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());//1,1

//        mFragmentManager.popBackStackImmediate(b.getClass().getSimpleName(),0);//界面显示到fragment b
//        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());//2,2

        mFragmentManager.popBackStackImmediate(b.getClass().getSimpleName(),POP_BACK_STACK_INCLUSIVE);//界面显示到fragment a
        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());//1,1


        //验证下出栈后再入栈会不会有null存在栈中
        mFragmentManager.beginTransaction()
                .add(R.id.frag_container, CFragment.newInstance("", ""), CFragment.class.getSimpleName())
                .addToBackStack( CFragment.class.getSimpleName())
                .commit();
        mFragmentManager.executePendingTransactions();
        Log.i(TAG, "getBackStackEntryCount(): "+mFragmentManager.getBackStackEntryCount()+",getFragments().size():"+mFragmentManager.getFragments().size());

    }



}