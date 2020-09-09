package com.junmeng.android_java_example.recycler.decoration;

public class YDivider {

    public YSideLine leftSideLine;
    public YSideLine topSideLine;
    public YSideLine rightSideLine;
    public YSideLine bottomSideLine;


    public YDivider(YSideLine leftSideLine, YSideLine topSideLine, YSideLine rightSideLine, YSideLine bottomSideLine) {
        this.leftSideLine = leftSideLine;
        this.topSideLine = topSideLine;
        this.rightSideLine = rightSideLine;
        this.bottomSideLine = bottomSideLine;
    }

    public YSideLine getLeftSideLine() {
        return leftSideLine;
    }

    public void setLeftSideLine(YSideLine leftSideLine) {
        this.leftSideLine = leftSideLine;
    }

    public YSideLine getTopSideLine() {
        return topSideLine;
    }

    public void setTopSideLine(YSideLine topSideLine) {
        this.topSideLine = topSideLine;
    }

    public YSideLine getRightSideLine() {
        return rightSideLine;
    }

    public void setRightSideLine(YSideLine rightSideLine) {
        this.rightSideLine = rightSideLine;
    }

    public YSideLine getBottomSideLine() {
        return bottomSideLine;
    }

    public void setBottomSideLine(YSideLine bottomSideLine) {
        this.bottomSideLine = bottomSideLine;
    }
}
