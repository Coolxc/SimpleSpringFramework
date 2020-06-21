package org.simpleframework.aop.aspect;

import java.lang.reflect.Method;

public abstract class DefaultAspect {

    /**
     *
     * @param targetClass The class being proxied
     * @param method The method of class being proxied
     * @param args The method's arguments of the class being proxied
     * @throws Throwable
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable{}

    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable{
        return returnValue;
    }

    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable throwable) throws Throwable{}
}
