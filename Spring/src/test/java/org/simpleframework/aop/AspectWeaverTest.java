package org.simpleframework.aop;

import com.yang.controller.frontend.MainPageController;
import com.yang.service.combine.HeadLineShopCategoryCombineService;
import com.yang.service.combine.HeadLineShopCategoryCombineServiceImpl;
import org.junit.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

public class AspectWeaverTest {
    BeanContainer beanContainer;
    @Test
    public void aspectWeaveTest(){
        //Load beans
        beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.yang");

        //Execute all aspect weave
        AspectWeaver aspectWeaver = new AspectWeaver();
        aspectWeaver.doAop();

        //Inject all the field marked the Autowired annotation
        DependencyInjector injector = new DependencyInjector();
        injector.doIOC();

        System.out.println("Have aspect:");
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        controller.sayHi();
        System.out.println("=====================\n");
        controller.sayNo();
        System.out.println("=====================\n");

        System.out.println("No aspect:");
        HeadLineShopCategoryCombineService service = (HeadLineShopCategoryCombineServiceImpl) beanContainer.getBean(HeadLineShopCategoryCombineServiceImpl.class);
        service.sayNo();
        System.out.println("=====================\n");
    }
}
