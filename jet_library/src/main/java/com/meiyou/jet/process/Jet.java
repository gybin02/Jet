package com.meiyou.jet.process;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.meiyou.jet.annotation.ContentView;
import com.meiyou.jet.annotation.JFindView;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.annotation.JIntent;
import com.meiyou.jet.annotation.JLoggable;
import com.meiyou.jet.wpattern.WPatternField;
import com.meiyou.jet.wpattern.exception.InjectionException;
import com.meiyou.jet.wpattern.message.ErrorMessages;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/18
 */

public class Jet {

    private static final String TAG = "Jet";

    /**
     * onCreate里面初始化，在setContentView之后
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        injectType(activity);
        injectField(activity);
//        injectOnClick(activity);
//        injectIntent(activity);
        injectMethod(activity);
    }

    /**
     * 支持  注解
     * @JFindView
     * @JFindViewOnClick
     * @param fragment
     * @param view
     */
    public static void bind(Fragment fragment, View view) {
        try {
            injectField(fragment, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只支持 @JFindView 注解
     *
     * @param object
     * @param view
     */
    public static void bind(Object object, View view) {
        try {
            injectField(object, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectField(Object object, View view) throws Exception {
        Class<? extends Object> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            bindJFindView(field, object, null, view);
        }
    }


    private static void injectType(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        ContentView contentView = activityClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            //doSomethind
        }
    }

    private static void injectMethod(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            JLoggable annotation = declaredMethod.getAnnotation(JLoggable.class);
            if (annotation != null) {
//                declaredMethod.
                //doSomethind
            }
        }
    }

    private static void injectIntent(Activity activity) {
//        Intent intent = activity.getIntent();
//        intent.getBooleanExtra();

    }

    private static void injectOnClick(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            JFindViewOnClick annotation = method.getAnnotation(JFindViewOnClick.class);
            if (annotation != null) {
                int value = annotation.value();
                View viewById = activity.findViewById(value);
//                    viewById.setOnClickListener(method);

//                    //通过InvocationHandler设置代理  
                DynamicHandler handler = new DynamicHandler(activity);
                handler.addMethod("onClick", method);
                Class<View.OnClickListener> listenerType = View.OnClickListener.class;
                Object listener = Proxy.newProxyInstance(
                        listenerType.getClassLoader(),
                        new Class<?>[]{listenerType}, handler);
                viewById.setOnClickListener((View.OnClickListener) listener);
//                }
            }

        }
    }

    private static void injectField(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                bindJFindView(field, activity, activity, null);

                bindJFindViewOnClick(field, activity, activity, null);

                handleIntent(activity, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //=====================================================================================
    // PUBLIC METHODS
    //=====================================================================================

    public static void validateFields(Object objectInstance) throws InjectionException {
        Class<?> objectClass = objectInstance.getClass();

        Field[] fields = objectClass.getDeclaredFields();

        for (Field field : fields) {
            WPatternField annotationField = field.getAnnotation(WPatternField.class);

            if (annotationField != null) {
                if (annotationField.required()) {
                    if (!isValidRequeridField(field)) {
                        throw new InjectionException(String.format(ErrorMessages.FIELD_WITH_INVALID_TYPE,
                                annotationField.name(), field.getType()));
                    }
                } else if (!isValidNotRequeridField(field)) {
                    throw new InjectionException(String.format(ErrorMessages.FIELD_WITH_INVALID_TYPE,
                            annotationField.name(), field.getType()));
                }
            }
        }
    }

    //=====================================================================================
    // PRIVATE METHODS
    //=====================================================================================

    private static boolean isValidRequeridField(Field field) {
        return !((field.getType() != int.class) && (field.getType() != long.class) && (field.getType() != boolean.class) &&
                (field.getType() != char.class) && (field.getType() != double.class) && (field.getType() != float.class) &&
                (field.getType() != Date.class));
    }

    private static boolean isValidNotRequeridField(Field field) {
        return !((field.getType() != int.class) && (field.getType() != Integer.class) &&
                (field.getType() != long.class) && (field.getType() != Long.class) &&
                (field.getType() != boolean.class) && (field.getType() != Boolean.class) &&
                (field.getType() != char.class) && (field.getType() != Character.class) &&
                (field.getType() != double.class) && (field.getType() != Double.class) &&
                (field.getType() != float.class) && (field.getType() != Float.class) &&
                (field.getType() != Date.class));
    }

//    public static void handleFindView(Activity activity, Field field) throws Exception {
//        JFindView annotation = field.getAnnotation(JFindView.class);
//        if (annotation != null) {
//            int value = annotation.value();
//            View viewById = activity.findViewById(value);
//            field.setAccessible(true);
//            field.set(activity, viewById);
////                Method findViewById = aClass.getMethod("findViewById", int.class);
////                Object invoke = findViewById.invoke(activity, value);
////                field.setAccessible(true);
////                field.set(activity, invoke);
//        }
//    }

//    public static void handleFindViewOnClick(Activity activity, Field field) throws Exception {
//        if (!(activity instanceof View.OnClickListener)) {
//            Log.e(TAG, "Activity Should implement View.OnClickLisentener");
//            return;
//        }
//        JFindViewOnClick findViewOnClick = field.getAnnotation(JFindViewOnClick.class);
//        if (findViewOnClick != null) {
//            int value = findViewOnClick.value();
//            View viewById = activity.findViewById(value);
//            if (viewById == null) {
//                Log.e(TAG, "JFindViewOnClick can not find View，id is :" + value);
//            }
//            field.setAccessible(true);
//            field.set(activity, viewById);
//            viewById.setOnClickListener((View.OnClickListener) activity);
//        }
//    }

    public static void handleIntent(Activity activity, Field field) throws Exception {
        JIntent annoIntent = field.getAnnotation(JIntent.class);
        if (annoIntent != null) {
            String value = annoIntent.value();
            Intent intent = activity.getIntent();

            Object result = getValue(field, intent, value);
            field.setAccessible(true);
            field.set(activity, result);

        }
    }

//    private static void injectContentView(Activity activity) {
//        Class<? extends Activity> aClass = activity.getClass();
//        ContentView annotation = aClass.getAnnotation(ContentView.class);
//        if (annotation != null) {
//            int value = annotation.value();
//            try {
//                activity.setContentView(value);
////                Method method = aClass.getMethod(METHOD_SET_CONTENTVIEW, int.class);
////                method.setAccessible(true);
////                method.invoke(activity, value);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

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


    private static void injectField(Fragment fragment, View view) throws Exception {
        Class<? extends Fragment> clazz = fragment.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            bindJFindView(field, fragment, null, view);

            bindJFindViewOnClick(field, fragment, null, view);
        }
    }

    private static void bindJFindView(Field field, Object container, Activity activity, View view) throws Exception {
        JFindView findView = field.getAnnotation(JFindView.class);
        if (findView != null) {
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

    private static void bindJFindViewOnClick(Field field, Object container, Activity activity, View view) throws Exception {
        JFindViewOnClick findViewOnClick = field.getAnnotation(JFindViewOnClick.class);
        if (findViewOnClick != null) {
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

    /**
     * 注入所有的事件
     *
     * @param activity
     */
//    private static void injectEvents(Activity activity) {
//
//        Class<? extends Activity> clazz = activity.getClass();
//        Method[] methods = clazz.getMethods();
//        //遍历所有的方法  
//        for (Method method : methods) {
//            Annotation[] annotations = method.getAnnotations();
//            //拿到方法上的所有的注解  
//            for (Annotation annotation : annotations) {
//                Class<? extends Annotation> annotationType = annotation
//                        .annotationType();
//                //拿到注解上的注解  
//                EventBase eventBaseAnnotation = annotationType
//                        .getAnnotation(EventBase.class);
//                //如果设置为EventBase  
//                if (eventBaseAnnotation != null) {
//                    //取出设置监听器的名称，监听器的类型，调用的方法名  
//                    String listenerSetter = eventBaseAnnotation
//                            .listenerSetter();
//                    Class<?> listenerType = eventBaseAnnotation.listenerType();
//                    String methodName = eventBaseAnnotation.methodName();
//
//                    try {
//                        //拿到Onclick注解中的value方法  
//                        Method aMethod = annotationType
//                                .getDeclaredMethod("value");
//                        //取出所有的viewId  
//                        int[] viewIds = (int[]) aMethod
//                                .invoke(annotation, null);
//                        //通过InvocationHandler设置代理  
//                        DynamicHandler handler = new DynamicHandler(activity);
//                        handler.addMethod(methodName, method);
//                        Object listener = Proxy.newProxyInstance(
//                                listenerType.getClassLoader(),
//                                new Class<?>[]{listenerType}, handler);
//                        //遍历所有的View，设置事件  
//                        for (int viewId : viewIds) {
//                            View view = activity.findViewById(viewId);
//                            Method setEventListenerMethod = view.getClass()
//                                                                .getMethod(listenerSetter, listenerType);
//                            setEventListenerMethod.invoke(view, listener);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//
//    }

}
