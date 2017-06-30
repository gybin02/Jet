package com.meiyou.jet.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动findView并设置OnCLick 方法
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JOnClickAction {

    int value() default 0;

    /**
     * 全部按钮点击
     *
     * @return
     */
    int[] all() default {};
}
