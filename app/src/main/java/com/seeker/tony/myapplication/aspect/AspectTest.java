package com.seeker.tony.myapplication.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 测试具体方法执行；
 * 
 * 测试发现，任务会打断 Instant Run,需要关闭这个功能；
 */
@Aspect
public class AspectTest {

    private static final String TAG = "xuyisheng";

//    @Before("execution(* android.app.Activity.on**(..))")
//    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.d(TAG, "onActivityMethodBefore: " + key);
//    }

    @Around("execution(* com.seeker.tony.myapplication.MainActivity.testAOP())")
    public void onActivityMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodAroundFirst: " + key);
        proceedingJoinPoint.proceed();
        Log.d(TAG, "onActivityMethodAroundSecond: " + key);
    }
}