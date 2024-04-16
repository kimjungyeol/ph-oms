package com.ktnet.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import groovy.transform.ToString;

/**
 * [공통] Upload File 관리 VO
 * 
 * @author : kjy
 * @fileName : FileUploadDto
 * @since : 2024/04/11
 */
@ToString
public class FileUploadDto {
	Logger logger = LoggerFactory.getLogger(getClass());
	
    private MultipartFile fileObj;
    private String fileId;
    private int fileSn;
    private String fileNm;
    private long fileSize;
    private String fileOrignNm;
    private String fileExtension;
    private String fileUploadPath;
    private String fileClsfCd;
    
    public FileUploadDto() {
    }
    
	public MultipartFile getFileObj() {
		return fileObj;
	}
	public void setFileObj(MultipartFile fileObj) {
		this.fileObj = fileObj;
	}
	public String getFileOrignNm() {
		return fileOrignNm;
	}
	public void setFileOrignNm(String fileOrignNm) {
		this.fileOrignNm = fileOrignNm;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getFileSn() {
		return fileSn;
	}

	public void setFileSn(int fileSn) {
		this.fileSn = fileSn;
	}
	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUploadPath() {
		return fileUploadPath;
	}
	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}
	public String getFileClsfCd() {
		return fileClsfCd;
	}
	public void setFileClsfCd(String fileClsfCd) {
		this.fileClsfCd = fileClsfCd;
	}
	
	public Map<String, Object> getDataMap() {
		Map<String, Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("fileId", this.fileId);
    	paramMap.put("fileSn", this.fileSn);
    	paramMap.put("fileNm", this.fileNm);
    	paramMap.put("fileOrignNm", this.fileOrignNm);
    	paramMap.put("fileExtension", this.fileExtension);
    	paramMap.put("fileSize", this.fileSize);
    	paramMap.put("fileUploadPath", this.fileUploadPath);
    	paramMap.put("fileClsfCd", this.fileClsfCd);
    	
    	return paramMap;
	}
	
	public void printData() {
		logger.debug("fileExtension: {}", this.fileExtension);
		logger.debug("fileId: {}", this.fileId);
		logger.debug("fileSn: {}", this.fileSn);
		logger.debug("fileNm: {}", this.fileNm);
		logger.debug("fileOrignNm: {}", this.fileOrignNm);
		logger.debug("fileExtension: {}", this.fileExtension);
		logger.debug("fileSize: {}", this.fileSize);
		logger.debug("fileUploadPath: {}", this.fileUploadPath);
		logger.debug("fileClsfCd: {}", this.fileClsfCd);
	}
}
