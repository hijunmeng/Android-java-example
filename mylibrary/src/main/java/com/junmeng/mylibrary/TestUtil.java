package com.junmeng.mylibrary;

import androidx.annotation.Keep;

@Keep
public class TestUtil {

    public static String get(){
        return new TestWithoutKeep("123",18).toString();
    }

}
