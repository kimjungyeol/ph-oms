package com.ktnet.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

/**
 * [공통] 페이지 관리 Toast UI Grid 응답 VO
 * 
 * @author : kjy
 * @fileName : ToastUiDto
 * @since : 2024/03/28
 */
@ToString
@Getter
public class ToastUiResponseDto {
    public boolean result;
    public Map<String, Object> data;

    public ToastUiResponseDto(boolean result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    /**
     * no paging.
     * 
     * @param resultList
     */
    public ToastUiResponseDto(List<Map<String, Object>> resultList) {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("contents", resultList);
        data.put("pagination", "");

        if (resultList == null || (resultList != null && resultList.size() == 0)) {
            this.result = false;
        } else {
            this.result = true;
        }

        this.data = data;
    }

    /**
     * use paging.
     * 
     * @param resultList
     * @param toastUiPagination
     */
    public ToastUiResponseDto(List<Map<String, Object>> resultList, ToastUiPagination toastUiPagination) {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("contents", resultList);
        data.put("pagination", toastUiPagination);

        if (resultList == null || (resultList != null && resultList.size() == 0)) {
            this.result = false;
        } else {
            this.result = true;
        }

        this.data = data;
    }
}
