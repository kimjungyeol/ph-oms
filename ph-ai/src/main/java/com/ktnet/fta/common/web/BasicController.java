package com.ktnet.fta.common.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.dto.ResultResponseDto;

public class BasicController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Form search Map response DTO.
     * 
     * @param map
     * @return
     */
    public ResultResponseDto ResultResponse(Map<String, Object> map) {
        return new ResultResponseDto(true, map);
    }
    
    /**
     * Form search List response DTO.
     * 
     * @param map
     * @return
     */
    public ResultResponseDto ResultResponse(List<Map<String, Object>> list) {
    	return new ResultResponseDto(true, list);
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