package com.seeker.tony.myapplication.proxy;

import android.util.Log;

import com.jet.jet.annotation.JImplement;

/**
 * @author zhengxiaobin
 * @since 17/5/22
 */
@JImplement
public class DumpImpl {

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
