package com.meiyou.jet.action;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.meiyou.jet.annotation.JFindView;

import java.lang.reflect.Field;

/**
 * @author zhengxiaobin
 * @since 17/6/30
 */

public class JFindViewAction extends BaseAction {

    @Override
    public void run(Activity activity, Field field) throws Exception {
        super.run(activity, field);
        findView(activity, field, null, activity);
    }

    @Override
    public void run(Fragment fragment, Field field, View view) throws Exception {
        super.run(fragment, field, view);
        findView(null, field, view, fragment);
    }

    @Override
    public void run(Object container, Field field, View view) throws Exception {
        super.run(container, field, view);
        findView(null, field, view, container);
    }

    /**
     * 查找FindView
     *
     * @param activity
     * @param field
     * @param view
     * @param container
     * @throws Exception
     */
    private void findView(Activity activity, Field field, View view, Object container) throws Exception {
        JFindView findView = field.getAnnotation(JFindView.class);
        if (findView == null) {
            return;
        }
        int value = findView.value();
        View viewById = null;
        if (activity != null) {
            viewById = activity.findViewById(value);
        }
        if (view != null) {
            viewById = view.findViewById(value);
        }
        if (viewById != null) {
            field.setAccessible(true);
            field.set(container, viewById);
        } else {
            Log.w(TAG, String.format("@JFindView 找不到; View = %s, id=%s", field.getName(), value));
        }
    }
}
