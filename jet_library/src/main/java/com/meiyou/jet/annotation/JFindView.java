package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过FindViewById绑定View;
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/18
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JFindView {
    int value() default 0;
}
