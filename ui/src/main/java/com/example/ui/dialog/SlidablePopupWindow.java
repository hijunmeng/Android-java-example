package com.example.ui.dialog;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.recycler.RecyclerItemClickListener;
import com.example.common.utils.AnimUtil;
import com.example.common.utils.CalculateUtil;
import com.example.common.utils.SystemUiUtil;
import com.example.ui.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;


/**
 * 主持人移交面板
 */
public class SlidablePopupWindow extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "HostChangePanelPopupWin";

    private static final int DEFAULT_MIN_SLIDE_HEIGHT_DP = 200;
    private static final int FULL_SCREEN_FLAG =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

    public static final int CLICK_TYPE_LEAVE = 1;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CLICK_TYPE_LEAVE})
    public @interface ClickType {
    }

    public interface Callback {
        void onClick(@ClickType int type);
    }


    private View rootView;
    private View vChangePanelContainer;
    private View vChangePanelTop;
    private RecyclerView rvUsers;

    private TextView tvLeave;


    private RecyclerView.Adapter adapter;

    private Activity mContext;


    private float disY;
    private float disX;

    private int sourcePlanelHeight;

    public SlidablePopupWindow(Activity context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        loadLayout(inflater);

        initView();
        initGestureDetector();
        initRecyclerview();
    }

    public RecyclerView.Adapter getAdapter() {

        return null;
    }


    public void loadLayout(LayoutInflater inflater) {
        rootView = inflater.inflate(R.layout.junmeng_dialog_popup_simple, null);
        vChangePanelContainer = rootView.findViewById(R.id.v_change_panel_root);
        vChangePanelTop = rootView.findViewById(R.id.v_change_panel_top);
        tvLeave = rootView.findViewById(R.id.positive);

        rvUsers = rootView.findViewById(R.id.recyclerview);


        rootView.setOnClickListener(this);
        tvLeave.setOnClickListener(this);

        vChangePanelContainer.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //调用一次后需要注销这个监听，否则会阻塞ui线程
                        vChangePanelContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                        if (sourcePlanelHeight <= 0 && vChangePanelContainer.getHeight() > 0) {
                            sourcePlanelHeight = vChangePanelContainer.getHeight();
                        }

                        return true;
                    }
                });
    }

    private void initRecyclerview() {


        rvUsers.setLayoutManager(new LinearLayoutManager(mContext));
        rvUsers.setAdapter(adapter = getAdapter());
//        RecyclerViewAnimUtil.closeDefaultAnimator(rvUsers);
        rvUsers.addOnItemTouchListener(new RecyclerItemClickListener(rvUsers, new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(MotionEvent e, View view, int position) {


            }
        }));
    }


    private void initGestureDetector() {
        final GestureDetector gd = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                //向左滑动时distanceX为正数，向右滑动时为负数
                //向上滑动时distanceY为正数，向下滑动时为负数
//                if (distanceY < 0) {
//                    disY += (-distanceY);
//                    vChangePanelContainer.setTranslationY(disY);
//                }
//                LogUtil.i(TAG, "e1.getY()-e2.getY()=" + (e1.getY() - e2.getY()));
//                LogUtil.i(TAG, "e1.getRawY()-e2.getRawY()=" + (e1.getRawY() - e2.getRawY()));
//                LogUtil.i(TAG, "distanceY=" + distanceY);

                int dY = (int) (e1.getY() - e2.getY());//由于layout方法的影响，此处不能直接使用distanceY，

                ViewGroup.MarginLayoutParams dd = (ViewGroup.MarginLayoutParams) vChangePanelContainer.getLayoutParams();
//                LogUtil.i(TAG, String.format("left=%d,top=%d,right=%d,bottom=%d", vChangePanelContainer.getLeft(), vChangePanelContainer.getTop(), vChangePanelContainer.getRight(), vChangePanelContainer.getBottom()));
                int left = vChangePanelContainer.getLeft();
                int right = vChangePanelContainer.getRight();
                int bottom = vChangePanelContainer.getBottom();
                int top = vChangePanelContainer.getTop() - dY;
                if (top < dd.topMargin) {
                    top = dd.topMargin;//不能超过设置的margintop
                }
                int minHeight = CalculateUtil.dp2px(mContext, DEFAULT_MIN_SLIDE_HEIGHT_DP);
                if (top > bottom - minHeight) {
                    top = bottom - minHeight;
                }
                // vChangePanelContainer.layout(left, top, right, bottom);

                ViewGroup.LayoutParams lp = vChangePanelContainer.getLayoutParams();
                lp.height = bottom - top;
                vChangePanelContainer.setLayoutParams(lp);

                return false;
            }


            public boolean onDown(MotionEvent e) {
                disX = disY = 0;
                return true;
            }
        });

        vChangePanelTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    int height = vChangePanelContainer.getMeasuredHeight();
//                    if (disY > height / 2) {
//                        hide();
//                    } else {
//                        recoverWithAnim();
//                    }
//
//                    return true;
//                }

//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    LogUtil.i(TAG,"action_up");
//                    ViewGroup.LayoutParams lp=vChangePanelContainer.getLayoutParams();
//                    int bottom = vChangePanelContainer.getBottom();
//                    int top = vChangePanelContainer.getTop() ;
//                    lp.height=bottom-top;
//                    vChangePanelContainer.setLayoutParams(lp);
//
//                    return true;
//                }
                return gd.onTouchEvent(event);
            }
        });
    }

    private Callback mCallback;


    public void setCallback(Callback cb) {
        this.mCallback = cb;
    }


    @Deprecated
    public void show(View anchor) {
        setFocusable(false);
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
//        getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);
        setFocusable(true);
        update();

        AnimUtil.upToShow(vChangePanelContainer);
        bgAnim(true);
    }


    /**
     * @param anchor
     * @param isLand 当前屏幕是否横屏
     */
    public void show(View anchor, boolean isLand) {
        setFocusable(false);
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
        if (isLand) {
            getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);//解决横屏全屏时调用状态栏和导航栏又出现的问题
        }
        setFocusable(true);
        update();

        AnimUtil.upToShow(vChangePanelContainer);
        bgAnim(true);
    }


    private void initView() {

        setContentView(rootView);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        int height = SystemUiUtil.getScreenHeight(mContext) + SystemUiUtil.getStatusBarHeight(mContext) + SystemUiUtil.getNavigationBarHeight(mContext);//
        setHeight(height);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);
//        setAnimationStyle(R.style.meetingsdk_popupWindow_anim_style);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(this, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.v_panel_root) {
            hide();
        } else if (id == R.id.positive) {
            if (mCallback != null) {
                mCallback.onClick(CLICK_TYPE_LEAVE);
            }
        }

    }

    /**
     * 带有动画的隐藏
     */
    public void hide() {
        TranslateAnimation ta = AnimUtil.downToHide(vChangePanelContainer);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        bgAnim(false);
    }

    /**
     * 动画恢复原状，即上升到显示
     */
    private void recoverWithAnim() {
        ValueAnimator xAnimator = ValueAnimator.ofFloat(vChangePanelContainer.getTranslationY(), 0);
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                vChangePanelContainer.setTranslationY(v);

            }
        });
        xAnimator.start();
    }

    private void bgAnim(boolean isShow) {
        float from = 0;
        float to = 0;
        if (isShow) {
            from = 0.0f;
            to = 1.0f;
        } else {
            from = 1.0f;
            to = 0.0f;
        }
        ValueAnimator xAnimator = ValueAnimator.ofFloat(from, to);
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                rootView.setAlpha(v);

            }
        });
        xAnimator.start();
    }


}