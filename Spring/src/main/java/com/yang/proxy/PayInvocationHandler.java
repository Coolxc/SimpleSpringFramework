package com.yang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PayInvocationHandler implements InvocationHandler {

    private Object targetObject;
    public PayInvocationHandler(Object targetObject){
        this.targetObject = targetObject;
    }

    void before(){
        System.out.println("Withdraw money from AnYang bank");
    }

    void after(){
        System.out.println("pay for Yang Youxiu");
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before();
        Object result = method.invoke(targetObject, objects);
        //Do something extra if the method name is dueDate
        if (method.getName() == "dueDate"){
            System.out.println("It's time to repayment");
            String date = (String) method.invoke(targetObject, objects);
            System.out.println(date);
        }
        after();
        return null;
    }
}
