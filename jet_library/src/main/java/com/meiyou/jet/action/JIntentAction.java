package com.jet.jet.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jet.jet.annotation.JIntent;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 自动读取Intent里面的值
 *
 * @author zhengxiaobin
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
    

    public void run(Object container, Field field, Bundle extras) throws Exception {
        JIntent annotation = field.getAnnotation(JIntent.class);
        if (annotation != null) {
            String value = annotation.value();
            Object result = getValue(field, extras, value);
            field.setAccessible(true);
            field.set(container, result);
        }
    }


    /**
     * judge Field Type
     *
     * @param field
     * @param intent
     * @param key
     * @return
     */
    public static Object getValue(Field field, Intent intent, String key) {
        Bundle extras = intent.getExtras();
        return getValue(field, extras, key);
    }


    /**
     * 从Bundle 获取数据
     *
     * @param field  属性类型
     * @param bundle 数据源
     * @param key    key
     * @return
     */
    public static Object getValue(Field field, Bundle bundle, String key) {
        Class<?> type = field.getType();

        if (type == String.class) {
            return bundle.getString(key);
        }
        if (type == Character.class || type == char.class) {
            return bundle.getChar(key, '\0');
        }
        if (type == Byte.class || type == byte.class) {
            return bundle.getByte(key, (byte) 0);
        }
        if (type == Short.class || type == short.class) {
            return bundle.getShort(key, (short) 0);
        }
        if (type == Integer.class || type == int.class) {
            return bundle.getInt(key, 0);
        }
        if (type == Long.class || type == long.class) {
            return bundle.getLong(key, 0);
        }
        if (type == Float.class || type == float.class) {
            return bundle.getFloat(key, 0);
        }
        if (type == Double.class || type == double.class) {
            return bundle.getDouble(key, 0);
        }
        if (type == Boolean.class || type == boolean.class) {
            return bundle.getBoolean(key, false);
        }
        if (type == Serializable.class) {
            return bundle.getSerializable(key);
        } else if (type == Bundle.class) {
            return bundle.getBundle(key);
        } else if (type == String[].class) {
            return bundle.getStringArray(key);
        } else {
            return bundle.getString(key);
        }
    }


}
