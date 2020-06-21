package org.simpleframework.util;

import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.until.ClassUtil;

import java.util.Set;

public class ClassUtilTest {

    @Test
    public void extractPackageClassTest(){
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.yang.service");
        Assert.assertNotNull(classSet);
        System.out.println(classSet);
    }
}
