package com.jet.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合{@link JPermission}使用，
 * 用户点击 拒绝权限后调用
 * <p>
 *1.  先要在onRequestPermissionsResult 里面 发下通知notifyPermissionsChange
 *2.  返回值可以使用 这个方法获得；private void onDeny(String permission)
 * @author zhengxiaobin
 * @since 17/6/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JPermissionDeny {

}
