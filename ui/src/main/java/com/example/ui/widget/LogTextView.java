package com.example.ui.widget;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LogTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "LogTextView";
    private static final int MAX_LINES = 5000;

    public LogTextView(@NonNull Context context) {
        this(context, null, 0);
    }

    public LogTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * 自动加换行
     *
     * @param text
     */
    public void appendLine(String text) {
        post(new Runnable() {
            @Override
            public void run() {
                append(text);
                append("\n");
                if(isScrollBottom()){
                    scrollBottom();
                }


            }
        });

    }

    @Override
    public void append(CharSequence text, int start, int end) {
        super.append(text, start, end);
        int lines = getLineCount() - MAX_LINES;
        if (lines > 0) {
            int index = -1;
            String str = getText().toString();
            for (int i = 0; i < lines; i++) {
                index = str.indexOf("\n", index + 1);
            }
            setText(str.substring(index + 1));
        }
    }

    private void scrollBottom() {
        int offset = getLineCount() * getLineHeight();
        if (offset > getHeight()) {
            scrollTo(0, offset - getHeight());
        }
    }

    public void appendText(String text) {
        post(new Runnable() {
            @Override
            public void run() {
                append(text);
                if(isScrollBottom()){
                    scrollBottom();
                }
            }
        });

    }

    private void scrollTop() {
        scrollTo(0, 0);
    }

    public boolean isScrollBottom() {
        //todo
        return true;
    }
}
