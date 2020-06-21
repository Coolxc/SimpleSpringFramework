package com.yang.controller.frontend;

import com.yang.dto.Result;
import com.yang.service.combine.HeadLineShopCategoryCombineService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

@Controller
public class MainPageController {
    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public void sayHi(){
        System.out.println("It's controller");
        System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiiii");
    }

    public void sayNo(){
        System.out.println("It's controller");
        headLineShopCategoryCombineService.sayNo();
    }
}
