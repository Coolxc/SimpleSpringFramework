package org.simpleframework.core;

import com.yang.service.solo.HeadLineService;
import com.yang.service.solo.impl.HeadLIneServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.annotation.Service;

import java.util.Set;

public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @Before
    public void init(){beanContainer = BeanContainer.getInstance();}

    @Test
    public void loadBeanTest(){
        beanContainer.loadBeans("com.yang");
    }

    @Test
    public void getClassesBySuperTest(){
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(HeadLineService.class);
        Assert.assertNotEquals(classSet, 0);
    }

    @Test
    public void getBeanTest(){
        HeadLIneServiceImpl headLIneService = (HeadLIneServiceImpl) beanContainer.getBean(HeadLIneServiceImpl.class);
        Assert.assertEquals(true, headLIneService instanceof HeadLIneServiceImpl);
    }

    @Test
    public void getClassesByAnnotationTest(){
        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(Service.class);
        Assert.assertEquals(true, classSet.size() != 0);
    }
}
