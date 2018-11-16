package com.jet.jet.proxy;

import com.jet.jet.annotation.JImplement;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Jet 动态代理类
 *
 * @author zhengxiaobin
 * @since 17/5/22
 */

public class JetProxy {
    private static final String TAG = "JetProxy";
    private static JetProxy instance;
    private Map<Class<?>, Object> mShadowBeanMap = new HashMap<>();

    public static JetProxy getInstance() {
        if (instance == null) {
            instance = new JetProxy();
        }
        return instance;
    }

    public <T> T create(Class<T> stub) {
        try {
            if (mShadowBeanMap.get(stub) != null) {
                return (T) mShadowBeanMap.get(stub);
            }

            JImplement annotation = stub.getAnnotation(JImplement.class);
            if (annotation != null) {
                String className = annotation.value();
                ProxyMethodHandler handler = new ProxyMethodHandler(className);
                T result = (T) Proxy.newProxyInstance(stub.getClassLoader(), new Class[]{stub}, handler);
                mShadowBeanMap.put(stub, result);
                return result;
            }else{
                android.util.Log.d(TAG, "annotation ==null ,是不是忘了加@JImplement? ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: 17/5/22 return null is Bad 
        return null;
    }
}
