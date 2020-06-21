package org.simpleframework.core;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.graalvm.compiler.nodes.calc.ObjectEqualsNode;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.until.ClassUtil;
import org.simpleframework.until.ValidationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE) //define the private constructor
public class BeanContainer {

    //A map to save classes objects and their instance
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    //Save all custom annotations, Non-Annotation are not allowed.
    //Aspect class are also beans that need to be managed
    private static List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class,
            Repository.class, Service.class, Aspect.class);

    private boolean loaded = false; //Determine whether the beanMap has been loaded

    public boolean getLoaded(){
        return loaded;
    }

    /**
     * Load the bean and put it into the concurrentMap
     * Need a synchronized keyword to preventing concurrent problems
     */
    public synchronized void loadBeans(String packageName){
        if (loaded == true){
            log.warn("The beanMap has been loaded");
            return;
        }

        //Extract all the classes and put them in the class set
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)){
            log.warn("extract noting from specified package");
        }

        //if a class has an specified annotation, place the class itself as a key
        // and the instance of the class as a value in the ConcurrentMap
        for (Class<?> clazz : classSet){
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION){
                if (clazz.isAnnotationPresent(annotation)){
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        loaded = true; //loaded success
    }

    //Users can add elements to beanMap
    public Object addBean(Class<?> clazz, Object object){
        return beanMap.put(clazz, object);
    }

    //Users can remove elements from beanMap, return the class's instance (value)
    public Object removeBean(Class<?> clazz){
        return beanMap.remove(clazz);
    }

    //Get the specified bean's instance
    public Object getBean(Class<?> clazz){
        return beanMap.get(clazz);
    }

    //Get all the classes from beanMap (key)
    public Set<Class<?>> getClasses(){
        return beanMap.keySet();
    }

    //Get all the instance of class
    public HashSet<Object> getBeans(){
        return new HashSet<Object>(beanMap.values());
    }

    //Get all the classes we want according to the specified annotation
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();

        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)){
            log.warn("Noting in beanMap");
            return null;
        }

        for (Class<?> clazz : keySet){
            if (clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }

        return classSet.size() > 0 ? classSet : null;
    }

    //Get all subclasses of the specified super class or interface
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrSuperClass){
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)){
            log.warn("Noting in beanMap");
            return null;
        }

        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet){
            if (interfaceOrSuperClass.isAssignableFrom(clazz)){
                classSet.add(clazz);
            }
        }

        return classSet.size() > 0 ? classSet : null;
    }

    public int getSize(){
        return beanMap.size();
    }

    //Use enum to implements singleton pattern
    private enum ContainerHolder{
        HOLDER;

        BeanContainer instance;

        ContainerHolder(){
            instance = new BeanContainer();
        }
    }

    public static BeanContainer getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
}
