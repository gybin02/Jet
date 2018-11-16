package com.jet.jet.action;

import android.app.Activity;

import com.jet.jet.annotation.JPermission;
import com.jet.jet.annotation.JPermissionDeny;
import com.jet.jet.annotation.JPermissionGrant;
import com.jet.jet.grant.PermissionsManager;
import com.jet.jet.grant.PermissionsResultAction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 申请权限
 *
 * @author zhengxiaobin
 * @since 17/6/30
 */
public class JPermissionAction extends BaseAction {
    @Override
    public void run(final Activity activity) throws Exception {
        super.run(activity);
        Class<? extends Activity> clazz = activity.getClass();
        JPermission annotation = clazz.getAnnotation(JPermission.class);
        if (annotation == null) return;
        Method grantMethod = null;
        Method denyMethod = null;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            JPermissionGrant grant = method.getAnnotation(JPermissionGrant.class);
            if (grant != null) {
                grantMethod = method;
            }
            JPermissionDeny deny = method.getAnnotation(JPermissionDeny.class);
            if (deny != null) {
                denyMethod = method;
            }
        }
        String value = annotation.value();
        String[] all = annotation.all();
        // 从数组中创建列表
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(all));
        arrayList.add(value);
        //列表转换成一个数组
        String[] result = new String[arrayList.size()];
        arrayList.toArray(result);

        final Method finalGrantMethod = grantMethod;
        final Method finalDenyMethod = denyMethod;
        PermissionsManager.getInstance()
                          .requestPermissionsIfNecessaryForResult(activity, result, new PermissionsResultAction() {
                              @Override
                              public void onGranted() {
                                  try {
                                      if (finalGrantMethod != null) {
                                          finalGrantMethod.setAccessible(true);
                                          ArrayList paramList = getMethodDefault(finalGrantMethod);
                                          finalGrantMethod.invoke(activity, paramList.toArray());
                                      }
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                              }

                              @Override
                              public void onDenied(String permission) {
                                  try {
                                      if (finalDenyMethod != null) {
                                          finalDenyMethod.setAccessible(true);
                                          finalDenyMethod.invoke(activity, permission);
                                      }
                                  } catch (Exception e) {
                                      e.printStackTrace();
                                  }
                              }
                          });

    }


}
