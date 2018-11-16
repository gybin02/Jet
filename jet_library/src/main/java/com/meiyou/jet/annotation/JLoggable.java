package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加注释功能，
 *不实用，使用Jet_aop替换
 * @author zhengxiaobin
 * @since 17/5/18
 */
@Deprecated 
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JLoggable {
}
