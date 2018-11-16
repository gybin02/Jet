package com.jet.jet.action;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.jet.jet.annotation.JFindViewOnClick;

import java.lang.reflect.Field;

/**
 * 通过FindViewById绑定View 和自动添加 OnClick功能
 *
 * @author zhengxiaobin
 * @since 17/5/18
 */
public class JFindViewOnClickAction extends BaseAction {
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

//    @Override
//    public void run(Object container, Field field, View view) throws Exception {
//        super.run(container, field, view);
//        findView(null,field,view,container);
//    }

    private void findView(Activity activity, Field field, View view, Object container) throws IllegalAccessException {
        JFindViewOnClick findViewOnClick = field.getAnnotation(JFindViewOnClick.class);
        if (findViewOnClick == null) {
            return;
        }
        int value = findViewOnClick.value();
        View viewById = null;
        if (activity != null) {
            viewById = activity.findViewById(value);
        }
        if (view != null) {
            viewById = view.findViewById(value);
        }
        if (viewById != null) {
            if (container instanceof View.OnClickListener) {
                viewById.setOnClickListener((View.OnClickListener) container);
            } else {
                Log.w(TAG, "@JFindViewOnClick 需要 Actiity/Fragment 实现 implement View.OnClickListener ");
            }
            field.setAccessible(true);
            field.set(container, viewById);
        } else {
            Log.w(TAG, String.format("@JFindViewOnClick 找不到View, View= %s  Id=%s ", field.getName(), value));
        }
    }
}
