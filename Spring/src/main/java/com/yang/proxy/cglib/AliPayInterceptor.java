package com.yang.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AliPayInterceptor implements MethodInterceptor {

    void before(){
        System.out.println("Withdraw money from AnYang bank");
    }

    void after(){
        System.out.println("pay for Yang Youxiu");
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object result = proxy.invokeSuper(obj, args);
        after();
        return result;
    }
}
