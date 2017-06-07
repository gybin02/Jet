package com.seeker.tony.myapplication.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * 将方法的入参和出参都打印出来,可以用于调试	 
 */
@Aspect
public class LogMethodAspect {
    private static final String TAG = "LogMethodAspect";

    @Around("execution(!synthetic * *(..)) && onLogMethod()")
    public Object doLogMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethod(joinPoint);
    }

    @Pointcut("@within(com.seeker.tony.myapplication.aspect.annotation.LogMethod)||@annotation(com.seeker.tony.myapplication.aspect.annotation.LogMethod)")
    public void onLogMethod() {
    }

    private Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String string = Arrays.deepToString(args);

        String method = joinPoint.getSignature()
                            .toShortString();
        Log.w(TAG, method + " Args : " + (args != null ? string : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        Log.w(TAG, method + " Result : " + ("void".equalsIgnoreCase(type) ? "void" : result));
        return result;
    }
}