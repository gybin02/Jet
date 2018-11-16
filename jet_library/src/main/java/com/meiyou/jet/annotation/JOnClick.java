package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动findView并设置OnCLick 方法
 * 方法参数 带有View，会返回点击的View如；
 * 
 *  test(View view);
 *
 * @author zhengxiaobin
 * @since 17/6/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JOnClick {

    int value() default 0;

    /**
     * 全部按钮点击
     *
     * @return
     */
    int[] all() default {};
}
