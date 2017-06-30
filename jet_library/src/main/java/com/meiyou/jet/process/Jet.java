package com.meiyou.jet.process;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.meiyou.jet.action.BaseAction;
import com.meiyou.jet.action.ContentViewAction;
import com.meiyou.jet.action.JFindViewAction;
import com.meiyou.jet.action.JFindViewOnClickAction;
import com.meiyou.jet.action.JIntentAction;
import com.meiyou.jet.action.JOnClickAction;
import com.meiyou.jet.action.JPermissionAction;
import com.meiyou.jet.action.JPermissionDenyAction;
import com.meiyou.jet.action.JPermissionGrantAction;
import com.meiyou.jet.wpattern.WPatternField;
import com.meiyou.jet.wpattern.exception.InjectionException;
import com.meiyou.jet.wpattern.message.ErrorMessages;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/18
 */

public class Jet {

    private static final String TAG = "Jet";
    /**
     * 所有类处理器
     */
    private static ArrayList<BaseAction> actionListType = new ArrayList<>();
    /**
     * 方法处理器
     */
    private static ArrayList<BaseAction> actionListMethod = new ArrayList<>();
    /**
     * field 处理器
     */
    private static ArrayList<BaseAction> actionListField = new ArrayList<>();


    static {
        actionListField.add(new JFindViewAction());
        actionListField.add(new JFindViewOnClickAction());
        actionListField.add(new JIntentAction());
    }


    static {
        actionListType.add(new JOnClickAction());
        actionListType.add(new JPermissionGrantAction());
        actionListType.add(new JPermissionDenyAction());
    }

    static {
        actionListMethod.add(new ContentViewAction());
        actionListMethod.add(new JPermissionAction());
    }

    /**
     * onCreate里面初始化，在setContentView之后
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        try {
            injectType(activity);
            injectField(activity);
            injectMethod(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支持  注解
     *
     * @param fragment
     * @param view
     * @JFindView
     * @JFindViewOnClick
     */
    public static void bind(Fragment fragment, View view) {
        try {

            ArrayList<BaseAction> actionList = new ArrayList<>();
            actionList.add(new JFindViewAction());
            actionList.add(new JFindViewOnClickAction());

            Class<? extends Fragment> clazz = fragment.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {

                for (BaseAction baseAction : actionList) {
                    baseAction.run(fragment,field,view);
                }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只支持 @JFindView 注解
     *
     * @param container
     * @param view
     */
    public static void bind(Object container, View view) {
        try {
            Class<? extends Object> clazz = container.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                JFindViewAction action = new JFindViewAction();
                action.run(container, field,view);
//                bindJFindView(field, object, null, view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void injectField(Object object, View view) throws Exception {
//
//    }


    private static void injectType(Activity activity) throws Exception {

        ArrayList<BaseAction> actionList = new ArrayList<>();
        actionList.add(new ContentViewAction());
        Class<? extends Activity> activityClass = activity.getClass();
        for (BaseAction baseAction : actionList) {
            baseAction.run(activityClass);
        }

    }

    private static void injectMethod(Activity activity) throws Exception {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        ArrayList<BaseAction> actionList = new ArrayList<>();
        actionList.add(new JOnClickAction());
        actionList.add(new JPermissionGrantAction());
        actionList.add(new JPermissionDenyAction());

        for (Method declaredMethod : declaredMethods) {
            for (BaseAction baseAction : actionList) {
                baseAction.run(activity, declaredMethod);
            }
//            JLoggable annotation = declaredMethod.getAnnotation(JLoggable.class);
//            if (annotation != null) {
//                declaredMethod.
            //doSomethind
//            }
        }

    }

//    private static void injectIntent(Activity activity) {
//        Intent intent = activity.getIntent();
//        intent.getBooleanExtra();

//    }

//    private static void injectOnClick(Activity activity) {
//        Class<? extends Activity> aClass = activity.getClass();
//        Method[] methods = aClass.getDeclaredMethods();
//        for (Method method : methods) {
//            JFindViewOnClick annotation = method.getAnnotation(JFindViewOnClick.class);
//            if (annotation != null) {
//                int value = annotation.value();
//                View viewById = activity.findViewById(value);
////                    viewById.setOnClickListener(method);
//
////                    //通过InvocationHandler设置代理  
//                DynamicHandler handler = new DynamicHandler(activity);
//                handler.addMethod("onClick", method);
//                Class<View.OnClickListener> listenerType = View.OnClickListener.class;
//                Object listener = Proxy.newProxyInstance(
//                        listenerType.getClassLoader(),
//                        new Class<?>[]{listenerType}, handler);
//                viewById.setOnClickListener((View.OnClickListener) listener);
////                }
//            }
//
//        }
//    }

    private static void injectField(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        ArrayList<BaseAction> actionList = new ArrayList<>();
        Class<? extends Activity> activityClass = activity.getClass();
        actionList.add(new JFindViewAction());
        actionList.add(new JFindViewOnClickAction());
        actionList.add(new JIntentAction());

        for (Field field : declaredFields) {
            try {
                for (BaseAction baseAction : actionList) {
                    baseAction.run(activity, field);
                }

//                bindJFindView(field, activity, activity, null);
//
//                bindJFindViewOnClick(field, activity, activity, null);
//
//                handleIntent(activity, field);
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
//
//    public static void handleIntent(Activity activity, Field field) throws Exception {
//      
//    }

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

//
//    private static void injectField(Fragment fragment, View view) throws Exception {
//   
//    }


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
