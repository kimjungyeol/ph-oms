package com.ktnet.fta.psr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktnet.fta.psr.dto.PsrConditionDto;
import com.ktnet.fta.psr.dto.PsrSearchItemDto;
import com.ktnet.fta.psr.dto.PsrSearchParamsDto;
import com.ktnet.fta.psr.dto.PsrStdItemTypeDto;

@Mapper
public interface PsrMapper {

    List<PsrSearchItemDto> selectPsrSearchItem(PsrSearchParamsDto params);

    List<PsrConditionDto> selectPsrCondition(PsrSearchParamsDto params);

    PsrStdItemTypeDto selectPsrStdItemType(Map<String, Object> params);

}
