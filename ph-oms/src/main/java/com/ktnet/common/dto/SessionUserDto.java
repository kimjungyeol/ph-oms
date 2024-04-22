package com.ktnet.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import groovy.transform.ToString;

/**
 * [공통] Upload File 관리 VO
 * 
 * @author : kjy
 * @fileName : FileDeleteDto
 * @since : 2024/04/11
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class SessionUserDto implements Serializable {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userNm;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public Map<String, Object> getMap() {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", this.userId);
		rtMap.put("userNm", this.userNm);
		
		return rtMap;
	}
}
