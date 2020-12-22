package com.example.ui.dialog;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ui.R;

/**
 * 通用对话框（必含内容和一个按钮）
 * 支持自定义布局，保持id一致即可
 * 支持三种类型按钮
 * 支持标题
 * 支持关闭按钮
 */
public class CommonDialogFragment extends DialogFragment implements View.OnClickListener, DialogInterface {
    private static final String TAG = "CommonDialogFragment";
    private Builder mBuilder;

    //contentView和positiveView不能为空
    @Nullable
    private TextView titleView;
    private TextView contentView;
    @Nullable
    private TextView negativeView;
    @Nullable
    private TextView neutralView;
    private TextView positiveView;
    @Nullable
    private ImageView closeView;
    @Nullable
    private View splite1View;
    @Nullable
    private View splite2View;

    public CommonDialogFragment(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(mBuilder.layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleView = view.findViewById(R.id.title);
        closeView = view.findViewById(R.id.close);
        contentView = view.findViewById(R.id.content);
        negativeView = view.findViewById(R.id.negative);
        positiveView = view.findViewById(R.id.positive);
        neutralView = view.findViewById(R.id.neutral);
        splite1View = view.findViewById(R.id.splite1);
        splite2View = view.findViewById(R.id.splite2);

        handleCloseButton();

        handleTitleView();

        handleContentView();

        handlePositiveButton();
        handleNegativeButton();
        handleNeutralButton();

        handleSpliteView();
    }

    private void handleContentView() {
        if (mBuilder.isContentCenter) {
            contentView.setGravity(Gravity.CENTER);
        }
        if (mBuilder.contentSizeSP != -1) {
            contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBuilder.contentSizeSP);
        }
        if (mBuilder.contentColor != -1) {
            contentView.setTextColor(mBuilder.contentColor);
        }
        contentView.setText(mBuilder.content);
    }

    private void handleCloseButton() {
        if (closeView == null) {
            return;
        }
        if (mBuilder.isShowCloseButton) {
            closeView.setVisibility(View.VISIBLE);
            closeView.setOnClickListener(this);
            if (mBuilder.closeIcon != -1) {
                closeView.setImageResource(mBuilder.closeIcon);
            }
        } else {
            closeView.setVisibility(View.GONE);
        }

    }

    private void handleSpliteView() {
        int flag = (mBuilder.isShowPositive ? 1 : 0)
                + (mBuilder.isShowNegative ? 1 : 0)
                + (mBuilder.isShowNeutral ? 1 : 0);
        if (flag == 3) {
            if (splite1View != null) {
                splite1View.setVisibility(View.VISIBLE);
            }
            if (splite2View != null) {
                splite2View.setVisibility(View.VISIBLE);
            }
        } else if (flag == 2) {
            if (splite1View != null) {
                splite1View.setVisibility(View.VISIBLE);
            }
            if (splite2View != null) {
                splite2View.setVisibility(View.GONE);
            }

        } else {
            if (splite1View != null) {
                splite1View.setVisibility(View.GONE);
            }
            if (splite2View != null) {
                splite2View.setVisibility(View.GONE);
            }
        }
    }

    private void handleNeutralButton() {
        if (neutralView == null) {
            return;
        }

        if (mBuilder.isShowNeutral) {
            neutralView.setOnClickListener(this);
            neutralView.setVisibility(View.VISIBLE);
            if (mBuilder.positiveButtonText != null) {
                neutralView.setText(mBuilder.neutralButtonText);
            }
            if (mBuilder.positiveButtonColor != -1) {
                neutralView.setTextColor(mBuilder.neutralButtonColor);
            }
            if (mBuilder.buttonSizeSP != -1) {
                neutralView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBuilder.buttonSizeSP);
            }

        } else {
            neutralView.setVisibility(View.GONE);
        }
    }

    private void handleNegativeButton() {
        if (negativeView == null) {
            return;
        }
        if (mBuilder.isShowNegative) {
            negativeView.setOnClickListener(this);
            negativeView.setVisibility(View.VISIBLE);
            if (mBuilder.positiveButtonText != null) {
                negativeView.setText(mBuilder.negativeButtonText);
            }
            if (mBuilder.positiveButtonColor != -1) {
                negativeView.setTextColor(mBuilder.negativeButtonColor);
            }
            if (mBuilder.buttonSizeSP != -1) {
                negativeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBuilder.buttonSizeSP);
            }

        } else {
            negativeView.setVisibility(View.GONE);
        }
    }

    private void handlePositiveButton() {
        if (mBuilder.isShowPositive) {
            positiveView.setOnClickListener(this);
            positiveView.setVisibility(View.VISIBLE);
            if (mBuilder.positiveButtonText != null) {
                positiveView.setText(mBuilder.positiveButtonText);
            }
            if (mBuilder.positiveButtonColor != -1) {
                positiveView.setTextColor(mBuilder.positiveButtonColor);
            }
            if (mBuilder.buttonSizeSP != -1) {
                positiveView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBuilder.buttonSizeSP);
            }

        } else {
            positiveView.setVisibility(View.GONE);
        }
    }

    private void handleTitleView() {
        if (titleView == null) {
            return;
        }
        if (mBuilder.isShowTitle) {
            titleView.setVisibility(View.VISIBLE);
            if (mBuilder.titleSizeSP != -1) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBuilder.titleSizeSP);
            }
            if (mBuilder.titleBold) {
                titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            if (mBuilder.titleColor != -1) {
                titleView.setTextColor(mBuilder.titleColor);
            }
            titleView.setText(mBuilder.title);
        } else {
            titleView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mBuilder.isAutoDismiss) {
            dismiss();
        }
        int id = v.getId();
        if (id == R.id.close) {
            if (isVisible()) {
                dismiss();
            }
        } else if (id == R.id.negative) {
            if (mBuilder.negativeClickListener != null) {
                mBuilder.negativeClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
            } else if (mBuilder.onClickListener != null) {
                mBuilder.onClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
            }
        } else if (id == R.id.positive) {
            if (mBuilder.positiveClickListener != null) {
                mBuilder.positiveClickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
            } else if (mBuilder.onClickListener != null) {
                mBuilder.onClickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
            }
        } else if (id == R.id.neutral) {
            if (mBuilder.neutralClickListener != null) {
                mBuilder.neutralClickListener.onClick(this, DialogInterface.BUTTON_NEUTRAL);
            } else if (mBuilder.onClickListener != null) {
                mBuilder.onClickListener.onClick(this, DialogInterface.BUTTON_NEUTRAL);
            }
        }
    }

    @Override
    public void cancel() {
        dismiss();
    }

    public static class Builder implements IDialog {
        private String title = null;
        private int titleColor = -1;
        private int titleSizeSP = -1;
        private boolean titleBold = false;
        private boolean isShowTitle = false;

        private boolean isContentCenter = false;
        private String content = null;
        private int contentColor = -1;
        private int contentSizeSP = -1;


        private int buttonSizeSP = -1;//-1表示默认
        private boolean isAutoDismiss = true;
        private String negativeButtonText = null;
        private int negativeButtonColor = -1;
        private DialogInterface.OnClickListener negativeClickListener;
        private boolean isShowNegative;

        private String neutralButtonText = null;
        private int neutralButtonColor = -1;
        private DialogInterface.OnClickListener neutralClickListener;
        private boolean isShowNeutral;

        private String positiveButtonText = null;
        private int positiveButtonColor = -1;
        private DialogInterface.OnClickListener positiveClickListener;
        private boolean isShowPositive = true;

        private boolean isCancelable = true;


        private int layoutResId = R.layout.junmeng_dialog_simple;
        private boolean isShowCloseButton = false;
        private int closeIcon = -1;

        private DialogInterface.OnClickListener onClickListener;


        @Override
        public Builder setTitle(String title) {
            isShowTitle = true;
            this.title = title;
            return this;
        }

        @Override
        public Builder setTitleColor(@ColorInt int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        @Override
        public Builder setTitleSize(int titleSizeSP) {
            this.titleSizeSP = titleSizeSP;
            return this;
        }

        @Override
        public Builder setTitleBold(boolean titleBold) {
            this.titleBold = titleBold;
            return this;
        }

        @Override
        public Builder isContentCenter(boolean isContentCenter) {
            this.isContentCenter = isContentCenter;
            return this;
        }

        @Override
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public Builder setContentColor(@ColorInt int contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        @Override
        public Builder setContentSize(int contentSizeSP) {
            this.contentSizeSP = contentSizeSP;
            return this;
        }

        @Override
        public Builder setButtonSize(int buttonSizeSP) {
            this.buttonSizeSP = buttonSizeSP;
            return this;
        }

        @Override
        public Builder setPositiveButton(String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener) {
            isShowPositive = true;
            this.positiveClickListener = onClickListener;
            this.positiveButtonColor = color;
            this.positiveButtonText = text;
            return this;
        }

        @Override
        public Builder setNeutralButton(String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener) {
            isShowNeutral = true;
            this.neutralClickListener = onClickListener;
            this.neutralButtonColor = color;
            this.neutralButtonText = text;
            return this;
        }

        @Override
        public Builder setNegativeButton(String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener) {
            isShowNegative = true;
            this.negativeClickListener = onClickListener;
            this.negativeButtonColor = color;
            this.negativeButtonText = text;
            return this;
        }

        @Override
        public Builder setCloseIcon(@DrawableRes int iconResId) {
            this.closeIcon = iconResId;
            return this;
        }

        @Override
        public Builder isShowCloseButton(boolean isShowCloseButton) {
            this.isShowCloseButton = isShowCloseButton;
            return this;
        }

        @Override
        public Builder isAutoDismiss(boolean isAutoDismiss) {
            this.isAutoDismiss = isAutoDismiss;
            return this;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        @Override
        public Builder setLayout(int layoutResId) {
            this.layoutResId = layoutResId;
            return this;
        }

        @Override
        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public CommonDialogFragment create() {
            if (TextUtils.isEmpty(content)) {
                throw new RuntimeException("必须调用setContent");
            }
            CommonDialogFragment dialog = new CommonDialogFragment(this);
            dialog.setCancelable(isCancelable);
            return dialog;
        }

    }

    public interface IDialog {
        /**
         * 标题文本
         *
         * @return
         */
        Builder setTitle(@NonNull String title);

        /**
         * 标题颜色
         *
         * @param titleColor
         * @return
         */
        Builder setTitleColor(@ColorInt int titleColor);

        /**
         * 标题大小
         *
         * @param titleSizeSP
         * @return
         */
        Builder setTitleSize(int titleSizeSP);

        /**
         * 标题是否加粗
         *
         * @param titleBold
         * @return
         */
        Builder setTitleBold(boolean titleBold);

        /**
         * 内容是否居中，默认left
         *
         * @param isContentCenter
         * @return
         */
        Builder isContentCenter(boolean isContentCenter);

        Builder setContent(@NonNull String content);

        Builder setContentColor(@ColorInt int contentColor);

        Builder setContentSize(int contentSizeSP);

        /**
         * 设置按钮文字大小
         *
         * @param buttonSizeSP
         * @return
         */
        Builder setButtonSize(int buttonSizeSP);

        /**
         * @param text            为null表示使用默认
         * @param color           -1表示使用默认
         * @param onClickListener
         * @return
         */
        Builder setPositiveButton(@Nullable String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener);

        Builder setNeutralButton(@Nullable String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener);

        Builder setNegativeButton(@Nullable String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener);

        /**
         * 设置关闭图标
         *
         * @param iconResId
         * @return
         */
        Builder setCloseIcon(@DrawableRes int iconResId);

        /**
         * 是否展示关闭按钮
         *
         * @param isShowCloseButton
         * @return
         */
        Builder isShowCloseButton(boolean isShowCloseButton);

        /**
         * 在点击时对话框是否自动消失，默认true
         *
         * @param isAutoDismiss
         * @return
         */
        Builder isAutoDismiss(boolean isAutoDismiss);

        /**
         * 点击外部是否会关闭对话框
         *
         * @param cancelable
         * @return
         */
        Builder setCancelable(boolean cancelable);

        /**
         * 自定义布局
         *
         * @param layoutResId
         * @return
         */
        Builder setLayout(@LayoutRes int layoutResId);

        /**
         * 设置监听器，优先级低于setPositiveButton，setNeutralButton，setNegativeButton
         *
         * @param onClickListener
         * @return
         */
        Builder setOnClickListener(DialogInterface.OnClickListener onClickListener);
    }
}