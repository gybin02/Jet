package com.meiyou.jet.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy 方法代理类
 * @author zhengxiaobin
 * @since 17/5/22
 */

public class ProxyMethodHandler implements InvocationHandler {
    private static final String TAG = "JetProxy";

    private final String targetClazzName;

    public ProxyMethodHandler(String targetClazzName) {
        this.targetClazzName = targetClazzName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //异常类，异常方法，出错等会有Log， Throw 会被不捕获住
        Class clazz = Class.forName(targetClazzName);
        Object newInstance = clazz.newInstance();
        String methodName = method.getName();
        Method realMethod = clazz.getMethod(methodName, method.getParameterTypes());
//        if (realMethod == null) {
//            Log.e(TAG, String.format("can't find Method:%s  in  class : %s", methodName, targetClazzName));
//            return null;
//        }

        realMethod.setAccessible(true);
        return realMethod.invoke(newInstance, args);
    }


}
