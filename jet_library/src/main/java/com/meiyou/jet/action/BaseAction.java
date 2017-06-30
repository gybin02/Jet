package com.meiyou.jet.action;

import android.app.Activity;
import android.support.annotation.CallSuper;

/**
 * 通用Action，注解有各自处理类
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/30
 */

public class BaseAction {

//    protected Class<? extends Activity> activityClass;
//
//    public BaseAction(Class<? extends Activity> activityClass) {
//        this.activityClass = activityClass;
//    }

    @CallSuper
    public void run(Class<? extends Activity> activityClass) {

    }
}
