package org.simpleframework.aop;

import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.until.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Woven into the aspect
 */
public class AspectWeaver {
    private BeanContainer beanContainer; //Aggregate BeanContainer

    public AspectWeaver(){
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        //1.Get all the aspect classes according to the AspectAnnotation
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) return;

        //2.Populate the aspectList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);

        //3.Traversing the objects in beanContainer and filter eligible aspect
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class targetClass : classSet){
            //Exclude the aspect class, prevent the proxy class agent itself
            if (targetClass.isAnnotationPresent(Aspect.class)){
                continue;
            }
            //4.Rough match: Find all aspect classes related to the target class
            List<AspectInfo> roughMatchedAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
            //5.Aspect weave: Execute all the corresponding aspect classes on the target class
            if (!ValidationUtil.isEmpty(roughMatchedAspectList)){
                wrapIfNecessary(roughMatchedAspectList, targetClass);
            }
        }
    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class targetClass) {
        AspectListExecutor executor = new AspectListExecutor(targetClass, roughMatchedAspectList);
        Object proxyObject = ProxyCreator.createProxy(targetClass, executor);
        //Use proxy object to overwrite the old object
        beanContainer.addBean(targetClass, proxyObject);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class targetClass) {
        List<AspectInfo> roughAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList){
            if (aspectInfo.getPointcutLocator().roughMatches(targetClass)){
                roughAspectList.add(aspectInfo);
            }
        }
        return roughAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        int orderIndex = 0;
        DefaultAspect aspectObject;
        PointcutLocator pointcutLocator;
        for (Class clazz : aspectSet){
            //1.Get the order index
            if (clazz.isAnnotationPresent(Order.class)){
                Order order = (Order) clazz.getDeclaredAnnotation(Order.class);
                orderIndex = order.value();
            }
            //2.Get the aspect object from bean container
            aspectObject = (DefaultAspect) beanContainer.getBean(clazz);
            //3.Create the pointcut locator
            Aspect aspect = (Aspect) clazz.getDeclaredAnnotation(Aspect.class);
            String pointcut = aspect.pointcut();
            pointcutLocator = new PointcutLocator(pointcut);
            //3.Assemble the aspectInfo object
            AspectInfo aspectInfo = new AspectInfo(orderIndex, aspectObject, pointcutLocator);

            aspectInfoList.add(aspectInfo);
        }

        return aspectInfoList;
    }

}
