package com.meiyou.jet.action;

import android.app.Activity;

import com.meiyou.jet.annotation.JPermission;
import com.meiyou.jet.annotation.JPermissionDeny;
import com.meiyou.jet.annotation.JPermissionGrant;
import com.meiyou.jet.grant.PermissionsManager;
import com.meiyou.jet.grant.PermissionsResultAction;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 申请权限
 *
 * @author zhengxiaobin@xiaoyouzi.com
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
        // TODO: 17/6/30 数据新增可能有异常 
        List<String> list = Arrays.asList(all);
        list.add(value);
        String[] result = {};
        list.toArray(result);

//        Arrays(all,value);
        final Method finalGrantMethod = grantMethod;
        final Method finalDenyMethod = denyMethod;
        PermissionsManager.getInstance()
                          .requestPermissionsIfNecessaryForResult(activity, result, new PermissionsResultAction() {
                              @Override
                              public void onGranted() {
                                  try {
                                      if (finalGrantMethod != null) {
                                          finalGrantMethod.setAccessible(true);
                                          finalGrantMethod.invoke(activity);
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
