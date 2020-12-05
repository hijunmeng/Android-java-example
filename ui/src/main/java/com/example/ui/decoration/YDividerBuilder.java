package com.example.ui.decoration;
import androidx.annotation.ColorInt;

public class YDividerBuilder {

    private YSideLine leftSideLine;
    private YSideLine topSideLine;
    private YSideLine rightSideLine;
    private YSideLine bottomSideLine;


    public YDividerBuilder setLeftSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.leftSideLine = new YSideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public YDividerBuilder setTopSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.topSideLine = new YSideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public YDividerBuilder setRightSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.rightSideLine = new YSideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public YDividerBuilder setBottomSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.bottomSideLine = new YSideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public YDivider create() {
        //提供一个默认不显示的sideline，防止空指针
        YSideLine defaultSideLine = new YSideLine(false, 0xff666666, 0, 0, 0);

        leftSideLine = (leftSideLine != null ? leftSideLine : defaultSideLine);
        topSideLine = (topSideLine != null ? topSideLine : defaultSideLine);
        rightSideLine = (rightSideLine != null ? rightSideLine : defaultSideLine);
        bottomSideLine = (bottomSideLine != null ? bottomSideLine : defaultSideLine);

        return new YDivider(leftSideLine, topSideLine, rightSideLine, bottomSideLine);
    }


}