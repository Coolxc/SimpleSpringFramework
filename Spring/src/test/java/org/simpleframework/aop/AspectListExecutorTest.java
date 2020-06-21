package org.simpleframework.aop;

import org.junit.Test;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.aop.mock.Mock;
import org.simpleframework.aop.mock.Mock1;
import org.simpleframework.aop.mock.Mock2;
import org.simpleframework.aop.mock.Mock3;

import java.util.ArrayList;
import java.util.List;

public class AspectListExecutorTest {

    @Test
    public void sortTest(){
        Mock mock1 = new Mock1();
        Mock mock2 = new Mock2();
        Mock mock3 = new Mock3();

        List<AspectInfo> aspectInfoList = new ArrayList<>();
        aspectInfoList.add(new AspectInfo(getOrder(mock1), (DefaultAspect) mock1, null));
        aspectInfoList.add(new AspectInfo(getOrder(mock2), (DefaultAspect) mock2, null));
        aspectInfoList.add(new AspectInfo(getOrder(mock3), (DefaultAspect) mock3, null));

        AspectListExecutor executor = new AspectListExecutor(String.class, aspectInfoList);
        for (AspectInfo aspectInfo : executor.getSortedAspectInfoList()){
            System.out.println(aspectInfo.getOrderIndex() + " : " + aspectInfo.getAspectObject());
        }
    }

    //Get order index for the annotation
    private int getOrder(Mock mock) {
        Class<?> clazz = mock.getClass();
        Order order= clazz.getDeclaredAnnotation(Order.class);
        return order.value();
    }
}
