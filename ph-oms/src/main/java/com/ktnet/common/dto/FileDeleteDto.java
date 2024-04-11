package com.ktnet.common.dto;

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
	private String fileId;
    private String filePath;
    
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
