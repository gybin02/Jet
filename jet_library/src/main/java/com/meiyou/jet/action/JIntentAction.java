package com.meiyou.jet.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.meiyou.jet.annotation.JIntent;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 自动读取Intent里面的值
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/18
 */
public class JIntentAction extends BaseAction {

    @Override
    public void run(Activity activity, Field field) throws Exception {
        super.run(activity, field);
        JIntent annoIntent = field.getAnnotation(JIntent.class);
        if (annoIntent != null) {
            String value = annoIntent.value();
            Intent intent = activity.getIntent();

            Object result = getValue(field, intent, value);
            field.setAccessible(true);
            field.set(activity, result);

        }
    }


    /**
     * judge Field Type
     *
     * @param field
     * @param intent
     * @param value
     * @return
     */
    public static Object getValue(Field field, Intent intent, String value) {
        Class<?> type = field.getType();

        if (type == String.class) {
            return intent.getStringExtra(value);
        }
        if (type == Character.class || type == char.class) {
            return intent.getCharExtra(value, '\0');
        }
        if (type == Byte.class || type == byte.class) {
            return intent.getByteExtra(value, (byte) 0);
        }
        if (type == Short.class || type == short.class) {
            return intent.getShortExtra(value, (short) 0);
        }
        if (type == Integer.class || type == int.class) {
            return intent.getIntExtra(value, 0);
        }
        if (type == Long.class || type == long.class) {
            return intent.getLongExtra(value, 0);
        }
        if (type == Float.class || type == float.class) {
            return intent.getFloatExtra(value, 0);
        }
        if (type == Double.class || type == double.class) {
            return intent.getDoubleExtra(value, 0);
        }
        if (type == Boolean.class || type == boolean.class) {
            return intent.getBooleanExtra(value, false);
        }
        if (type == Serializable.class) {
            return intent.getSerializableExtra(value);
        } else if (type == Bundle.class) {
            return intent.getBundleExtra(value);
        } else if (type == String[].class) {
            return intent.getStringArrayExtra(value);
        } else {
            return intent.getStringExtra(value);
        }
    }
}
