package com.ktnet.common.response;


import lombok.Builder;

/**
 * [공통] API Response 결과의 반환 값을 관리
 */
public class ApiResponse<T> {

    // API 응답 결과 Response
    private T result;

    // API 응답 코드 Response
    private int resultCode;

    // API 응답 코드 Message
    private String resultMsg;

    public T getResult() {
		return result;
	}
	public int getResultCode() {
		return resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}

	@Builder
    public ApiResponse(final T result, final int resultCode, final String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}