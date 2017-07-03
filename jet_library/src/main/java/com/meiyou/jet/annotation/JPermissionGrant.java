package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合{@link JPermission}使用，
 * 用户点击 授权权限后调用， 需要代码显示调用：
 * 
 * 先要在onRequestPermissionsResult 里面 发下通知notifyPermissionsChange
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JPermissionGrant {
    /**
     * 多个权限
     * The name of the permission that is required, if precisely one permission
     * is required. If more than one permission is required, specify either
     * {@link #all()} instead.
     * <p>
     * If specified, {@link #all()} and {@link #allOf()} must both be null.
     */
    String value() default "";

    /**
     * 多个权限
     * Specifies a list of permission names that are all required.
     * <p>
     * If specified, {@link #all()} and {@link #value()} must both be null.
     */
    String[] all() default {};
}
