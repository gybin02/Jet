package com.seeker.tony.myapplication.proxy;

import android.util.Log;

import com.meiyou.jet.annotation.JProvider;

/**
 * @author zhengxiaobin
 * @since 17/5/22
 */
@JProvider
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
