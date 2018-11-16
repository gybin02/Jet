package com.jet.jet.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {


    public Object targetObj;

    public ProxyHandler(Object targetObj) {
        this.targetObj = targetObj;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

//        System.out.println("before the function \"" + method.getName() + "\"");
        Object ret = method.invoke(targetObj, args);
//        System.out.println("after the function \"" + method.getName() + "\"");
        return ret;
    }

} 