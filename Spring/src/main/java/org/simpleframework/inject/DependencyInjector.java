package org.simpleframework.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.until.ClassUtil;
import org.simpleframework.until.ValidationUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Dependency inject
 */
@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;

    public DependencyInjector(){
        beanContainer = BeanContainer.getInstance();
    }

    public void doIOC(){

        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)){
            log.warn("Nothing in the bean container");
            return;
        }

        for (Class<?> clazz : classSet){
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)){
                continue; //Break out of the current cycle
            }

            for (Field field : fields){
                if (field.isAnnotationPresent(Autowired.class)){
                    //get autowired annotation and it's value attribute
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();

                    //Get the field type and it's value
                    Class<?> fieldClass = field.getType();
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);

                    if (fieldValue == null){
                        throw new RuntimeException("Unable to inject the relevant type, the target type is: " + fieldClass.getName());
                    }else {
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, fieldValue, targetBean, true);
                    }
                }
            }
        }
    }

    //Get the instance or implementation class in the bean container based on class object
    //Use autowired's value to find specify class when the bean container has multiple implements of field type
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null){
           return fieldValue;
        }else { //It is a interface if fieldValue == null
            Class<?> implementClass = getImplementClass(fieldClass, autowiredValue);
            if (implementClass != null){
                return beanContainer.getBean(implementClass);
            }
        }
        return null;
    }

    private Class<?> getImplementClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if (!ValidationUtil.isEmpty(classSet)){ //return null directly if the class set is empty
            if (ValidationUtil.isEmpty(autowiredValue)){ //validate the set size if the autowired value of Autowired is null
                if (classSet.size() == 1){
                    log.info("==== Autowired SUCCESS =====" + classSet.iterator().next().getName());
                    return classSet.iterator().next();
                }else {
                    log.warn("multiple implements classes for " + fieldClass.getName() + ". please set autowired value");
                    throw new RuntimeException();
                }
            }else { //Otherwise find the specified class based on the autowired's value
                for (Class<?> clazz : classSet){
                    if (clazz.getSimpleName().equals(autowiredValue)){
                        log.info("find the specified class " + clazz.getSimpleName() + " from " + classSet.stream().map((s)->s.getSimpleName()).collect(toList()));
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
