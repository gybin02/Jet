package com.seeker.tony.myapplication.proxy;

import android.util.Log;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/22
 */

public class TestImpl {

    private static final String TAG = "TestImpl";

    public void test() {
        Log.d(TAG, "test Method  invoke");
    }

    public String getValue() {
        String str = "HelloWorld";
        Log.d(TAG, "getValue Method  invoke： " + str);
        return str;
    }
}
