package com.yang.service.solo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yang.dto.Result;
import com.yang.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    Result<Boolean> addHeadLine(HeadLine headLine);

    Result<Boolean> removeHeadLine(int headLineId);

    Result<Boolean> modifyHeadLine(HeadLine headLine);

    Result<HeadLine> queryHeadLineById(int headLineId);

    Result<List<HeadLine>> queryHeadLine(HeadLine headLine, int pageIndex, int pageSize);
}
