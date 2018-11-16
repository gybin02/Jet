package com.meiyou.jet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 申请权限, Activity需要 <br/>
 * 实现：先要在onRequestPermissionsResult 里面 发下通知notifyPermissionsChange
 * 才能收到授权通知
 * {@link com.meiyou.jet.grant.PermissionsManager}
 *
 * @author zhengxiaobin
 * @JPermission(Manifest.permission.CAMERA) public class MainActivity extends AppCompatActivity
 * <p>
 * <p>
 * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
 * }
 * @JPermissionGrant private void onGrand() {
 * Toast.makeText(MainActivity.this, "onGrant Success", Toast.LENGTH_SHORT)
 * .show();
 * }
 * @JPermissionDeny private void onDeny(String permisson) {
 * Toast.makeText(MainActivity.this, "onDenied Success: "+permisson, Toast.LENGTH_SHORT)
 * .show();
 * }
 * @since 17/6/30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JPermission {
    /**
     * 多个权限
     * The name of the permission that is required, if precisely one permission
     * is required. If more than one permission is required, specify either
     * {@link #all()} instead.
     * <p>
     * If specified, {@link #all()} and {@link #all()} must both be null.
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
