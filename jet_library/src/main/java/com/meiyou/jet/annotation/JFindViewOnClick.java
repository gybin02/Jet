package com.jet.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过FindViewById绑定View 和自动添加 OnClick功能,
 * 需要 activity implement View.OnClick;  点击才能生效
 *
 * @author zhengxiaobin
 * @since 17/5/18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JFindViewOnClick {
    int value() default 0;
}
