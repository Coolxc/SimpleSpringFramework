package org.simpleframework.inject;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.core.BeanContainer;

public class DependencyInjectorTest {

    private static BeanContainer beanContainer;

    @Before
    public void init(){
        beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.yang");
    }

    @Test
    public void autowiredTest(){
        DependencyInjector ioc = new DependencyInjector();
        ioc.doIOC();
    }
}
