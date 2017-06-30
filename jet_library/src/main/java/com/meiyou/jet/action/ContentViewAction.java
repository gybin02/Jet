package com.meiyou.jet.action;

import android.app.Activity;

import com.meiyou.jet.annotation.ContentView;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/30
 */

public class ContentViewAction extends BaseAction {

//    public ContentViewAction(Class<? extends Activity> activityClass) {
//        super(activityClass);
//    }

    @Override
    public void run(Class<? extends Activity> activityClass) {
        super.run(activityClass);
        ContentView contentView = activityClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            //doSomethind
        }
    }
}
