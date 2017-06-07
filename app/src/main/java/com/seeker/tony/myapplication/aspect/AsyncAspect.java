package com.seeker.tony.myapplication.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 异步线程执行，切面,借助rxjava,异步执行app中的方法	
 */
@Aspect
public class AsyncAspect {

    @Around("execution(!synthetic * *(..)) && onAsyncMethod()")
    public void doAsyncMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        asyncMethod(joinPoint);
    }

    @Pointcut("@within(com.seeker.tony.myapplication.aspect.annotation.Async)||@annotation(com.seeker.tony.myapplication.aspect.annotation.Async)")
    public void onAsyncMethod() {
    }

    private void asyncMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
//
//        Observable.create(new Observable.OnSubscribe<Object>() {
//
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//                Looper.prepare();
//                try {
//                    joinPoint.proceed();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//                Looper.loop();
//            }
//        })
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe();
    }
}