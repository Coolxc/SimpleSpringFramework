package com.yang.service.combine;

import com.yang.dto.MainPageInfoDTO;
import com.yang.dto.Result;

public interface HeadLineShopCategoryCombineService {

    Result<MainPageInfoDTO> getMainPageInfo();

    void sayNo();
}
