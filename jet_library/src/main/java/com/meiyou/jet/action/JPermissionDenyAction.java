package com.meiyou.jet.action;

import com.meiyou.jet.annotation.JPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合{@link JPermission}使用，
 * 用户点击 拒绝权限后调用
 * <p>
 * 先要在onRequestPermissionsResult 里面 发下通知notifyPermissionsChange
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JPermissionDenyAction {

}
