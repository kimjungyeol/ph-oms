package com.ktnet.common.dto;

/**
 * [공통] 페이지 관리 Toast UI Grid 응답 VO
 * 
 * @author : kjy
 * @fileName : ToastUi Pagination
 * @since : 2024/03/28
 */
public class ToastUiPagination {
    private int page;
    private long totalCount;

    ToastUiPagination() {
    }

    public ToastUiPagination(int page, long totalCount) {
        this.page = page;
        this.totalCount = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
