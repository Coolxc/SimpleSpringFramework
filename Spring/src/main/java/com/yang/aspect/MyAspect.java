package com.yang.aspect;

import lombok.extern.log4j.Log4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.aspect.DefaultAspect;

import java.lang.reflect.Method;

@Log4j
@Aspect(pointcut = "within(com.yang.controller..*)") //Only controller
public class MyAspect extends DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("Before advice~");
        super.before(targetClass, method, args);
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        log.info("After returning advice~");
        return super.afterReturning(targetClass, method, args, returnValue);
    }
}
