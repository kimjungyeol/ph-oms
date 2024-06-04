package com.ktnet.fta.psr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.psr.dto.PsrConditionDto;
import com.ktnet.fta.psr.dto.PsrSearchItemDto;
import com.ktnet.fta.psr.dto.PsrSearchParamsDto;
import com.ktnet.fta.psr.dto.PsrSearchResultDto;
import com.ktnet.fta.psr.dto.PsrStdItemTypeDto;
import com.ktnet.fta.psr.mapper.PsrMapper;

@Service("psrService")
public class PsrService {

    @Autowired
    private PsrMapper psrMapper;

    public PsrSearchResultDto searchPsrSearchResult(PsrSearchParamsDto params) {
        PsrSearchResultDto psrSearchResultDto = new PsrSearchResultDto();

        params.setHscodes(params.getHscodes().stream().filter(item -> StringUtils.isNotBlank(item)).distinct()
                .collect(Collectors.toList()));

        if (params.getHscodes().isEmpty()) {
            // hs 체크
            return psrSearchResultDto;
        }

        // PSR 목록 가져오기
        List<PsrSearchItemDto> psrs = psrMapper.selectPsrList(params);

        // PSR 예외조건 가져오기
        List<PsrConditionDto> conditions = psrMapper.selectPsrConditionList(params);

        for (PsrSearchItemDto psr : psrs) {
            psr.setConditions(conditions.stream().filter(item -> item.getPsrId().equals(psr.getId()))
                    .collect(Collectors.toList()));
        }

        return psrSearchResultDto;
    }

    public PsrStdItemTypeDto searchPsrStdItemType(Long classificationId, Long standardItemId) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("classificationId", classificationId);
        params.put("standardItemId", standardItemId);

        PsrStdItemTypeDto psrStdItemTypeDto = psrMapper.selectPsrStdItemType(params);

        return psrStdItemTypeDto;
    }

}
