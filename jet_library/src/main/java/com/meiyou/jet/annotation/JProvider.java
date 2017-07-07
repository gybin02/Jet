package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识类为JImplement实现类，提示其他人不要删除该类
 * 用于混淆配置
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/23
 */
//RetentionPolicy.Source；只会存在Java文件中；
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JProvider {
    String value() default "";
}


