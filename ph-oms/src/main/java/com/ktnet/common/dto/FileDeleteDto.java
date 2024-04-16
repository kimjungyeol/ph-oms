package com.ktnet.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.transform.ToString;

/**
 * [공통] Upload File 관리 VO
 * 
 * @author : kjy
 * @fileName : FileDeleteDto
 * @since : 2024/04/11
 */
@ToString
public class FileDeleteDto {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private String fileId;
	private String fileSn;
    private String fileUploadPath;
    
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileSn() {
		return fileSn;
	}
	public void setFileSn(String fileSn) {
		this.fileSn = fileSn;
	}
	public String getFileUploadPath() {
		return fileUploadPath;
	}
	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	
	public void printData() {
		logger.debug("fileId: {}", this.fileId);
		logger.debug("fileSn: {}", this.fileSn);
		logger.debug("fileUploadPath: {}", this.fileUploadPath);
	}
}
