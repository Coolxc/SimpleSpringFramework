package com.yang.proxy.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyUtil {
    public static <T> T newInstance(T targetObject, InvocationHandler h){
        Class clazz = targetObject.getClass();
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), h);
    }
}
