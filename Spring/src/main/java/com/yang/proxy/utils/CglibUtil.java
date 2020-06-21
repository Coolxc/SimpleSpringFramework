package com.yang.proxy.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibUtil {
    public static <T> T creatProxy(T targetObject, MethodInterceptor m) {
        return (T) Enhancer.create(targetObject.getClass(), m);
    }
}
