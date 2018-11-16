package com.meiyou.jet.action;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.meiyou.jet.annotation.JOnClick;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 自动findView并设置OnCLick 方法
 *
 * @author zhengxiaobin
 * @since 17/6/30
 */
public class JOnClickAction extends BaseAction {
    @Override
    public void run(final Activity activity, final Method method) throws Exception {
        super.run(activity, method);
        JOnClick annotation = method.getAnnotation(JOnClick.class);
        if (annotation == null) return;
        int value = annotation.value();
        doClick(activity, value, method);

        int[] all = annotation.all();
        for (int i : all) {
            doClick(activity, i, method);
        }

    }

    // TODO: 17/6/30  Fragment , Container 实现类似的功能
    private void doClick(final Activity activity, int value, final Method method) {
        if (value != 0) {
            View view = activity.findViewById(value);
            if (view == null) {
                Log.d(TAG, "run: JOnClick: 找不到View");
                return;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        method.setAccessible(true);
                        Class<?>[] types = method.getParameterTypes();
                        ArrayList param = new ArrayList();
                        for (Class<?> type : types) {
                            if (type.isAssignableFrom(View.class)) {
                                param.add(v);
                            } else if (type.isPrimitive()) {
                                addValuePrimitive(param,type);
//                                param.add(type.newInstance());
                            } else {
                                //new Object();
                                param.add(null);
                            }
                        }
                        //TODO 实现可以接受不同的参数；
                        method.invoke(activity, param.toArray());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    


}
