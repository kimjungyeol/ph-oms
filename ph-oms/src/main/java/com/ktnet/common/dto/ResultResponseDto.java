package com.ktnet.common.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

/**
 * [공통] 페이지 관리 응답 VO
 * 
 * @author : kjy
 * @fileName : Response
 * @since : 2024/04/03
 */
@ToString
@Getter
public class ResultResponseDto {
    public boolean result;
    public Map<String, Object> data;
    public List<Map<String, Object>> dataList;

    public ResultResponseDto(boolean result) {
        this.result = result;
    }

    public ResultResponseDto(boolean result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }
    
    public ResultResponseDto(boolean result, List<Map<String, Object>> dataList) {
    	this.result = result;
    	this.dataList = dataList;
    }
}
