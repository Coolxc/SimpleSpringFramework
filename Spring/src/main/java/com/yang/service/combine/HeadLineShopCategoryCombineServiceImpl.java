package com.yang.service.combine;

import com.yang.dto.MainPageInfoDTO;
import com.yang.dto.Result;
import com.yang.entity.HeadLine;
import com.yang.entity.ShopCategory;
import com.yang.service.solo.HeadLineService;
import com.yang.service.solo.ShopCategoryService;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.inject.annotation.Autowired;

import java.util.List;

@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    @Autowired("HeadLIneServiceImpl")
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        //1.extract headLine list
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineResult = headLineService.queryHeadLine(headLineCondition, 1, 4);
        //2.extract shop category list
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);
        //3.merge the two and return
        Result<MainPageInfoDTO> result = MergeMainPageInfoResult(headLineResult, shopCategoryResult);

        return result;
    }

    @Override
    public void sayNo() {
        System.out.println("Noooooooooooooooooooooooooooooo");
    }

    private Result<MainPageInfoDTO> MergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
