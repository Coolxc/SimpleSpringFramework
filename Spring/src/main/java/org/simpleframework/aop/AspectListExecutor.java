package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.until.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AspectListExecutor implements MethodInterceptor {
    //The class proxied
    private Class<?> targetClass;
    @Getter
    List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList){
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
        this.targetClass = targetClass;
    }

    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo aspectInfo, AspectInfo t1) {
                return aspectInfo.getOrderIndex() - t1.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = null;
        if (ValidationUtil.isEmpty(sortedAspectInfoList)){return returnValue;}

        //1.Execute all Aspect's before methods in ascending order
        invokeBeforeAdvices(method, args);
        try {
            //2.Execute the proxy method
            returnValue = proxy.invokeSuper(obj, args);
            //3.If the delegated method returns normally, execute all
            //  Aspect's afterReturning methods in descending order
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        }catch (Exception e){
            //4.If the delegated method throws exception, execute all
            //  Aspect's afterThrowing methods in descending order.
            invokeAfterThrowingAdvices(method, args, e);
        }
        return returnValue;
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--){
            sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, e);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable{
        Object result = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--){
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable{
        for (AspectInfo aspectInfo : sortedAspectInfoList){
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }
}
