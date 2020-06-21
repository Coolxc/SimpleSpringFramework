package org.simpleframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Create the cglib proxy object
 */
public class ProxyCreator {

    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor){
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
