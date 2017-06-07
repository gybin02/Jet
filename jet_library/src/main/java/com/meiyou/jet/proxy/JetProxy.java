package com.meiyou.jet.proxy;

import com.meiyou.jet.annotation.JImplement;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Jet 动态代理类
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/22
 */

public class JetProxy {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: 17/5/22 return null is Bad 
        return null;
    }
}
