package com.seeker.tony.myapplication.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

/**
 * 测试@JLoggable注解执行,
 * 用于追踪某个方法花费的时间,可以用于性能调优的评判	
 */
@Aspect
public class Hugo {
    //带有DebugLog注解的所有类
    @Pointcut("within(@com.meiyou.jet.annotation.JLoggable *)")
    public void withinAnnotatedClass() {
    }

    //在带有DebugLog注解的所有类，除去synthetic修饰的方法
    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    //在带有DebugLog注解的所有类，除去synthetic修饰的构造方法
    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    //在带有DebugLog注解的方法
    @Pointcut("execution(@com.meiyou.jet.annotation.JLoggable * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    //在带有DebugLog注解的构造方法
    @Pointcut("execution(@com.meiyou.jet.annotation.JLoggable *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {
    }

    @Around("method() || constructor()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行方法前，做些什么
//        enterMethod(joinPoint);
        Log.e("Hugo", "执行方法前，做些什么 ");
        long startNanos = System.nanoTime();
        Thread.sleep(1000);
        //执行原方法
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);
        //执行方法后，做些什么
//        exitMethod(joinPoint, result, lengthMillis);
        Log.e("Hugo", "执行方法后，做些什么 ");
        Log.e("Hugo", "方法总执行时间：" + lengthMillis);
        return result;
    }
}