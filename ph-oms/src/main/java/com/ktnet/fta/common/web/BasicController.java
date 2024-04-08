package com.ktnet.fta.common.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.common.dto.ToastUiPagination;
import com.ktnet.common.dto.ToastUiResponseDto;

public class BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Grid search response DTO.
     * 
     * @param rtList
     * @param map
     * @return
     */
    public ToastUiResponseDto ToastUiResponse(List<Map<String, Object>> rtList, Map<String, Object> map) {
        int page = Integer.parseInt(map.get("page") + "");
        long totalCnt = Long.parseLong(rtList.get(0).get("totalCnt") + "");

        return new ToastUiResponseDto(rtList, new ToastUiPagination(page, totalCnt));
    }

    /**
     * Form search response DTO.
     * 
     * @param map
     * @return
     */
    public ResultResponseDto ResultResponse(Map<String, Object> map) {
        return new ResultResponseDto(true, map);
    }

    /**
     * From insert/update/delete response DTO.
     * 
     * @param result
     * @return
     */
    public ResultResponseDto ResultResponse(boolean result) {
        return new ResultResponseDto(result);
    }
}