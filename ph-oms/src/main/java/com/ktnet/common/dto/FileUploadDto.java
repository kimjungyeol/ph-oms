package com.ktnet.common.dto;

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
    private String fileOrgnNm;
    private String fileExtension;
    private String fileId;
    private long fileSize;
    private String fileUploadPath;
    private String fileClsfCd;
    private String fileCstdyPath;
    
    public FileUploadDto() {
    }
    
	public MultipartFile getFileObj() {
		return fileObj;
	}
	public void setFileObj(MultipartFile fileObj) {
		this.fileObj = fileObj;
	}
	public String getFileOrgnNm() {
		return fileOrgnNm;
	}
	public void setFileOrgnNm(String fileOrgnNm) {
		this.fileOrgnNm = fileOrgnNm;
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
	public String getFileCstdyPath() {
		return fileCstdyPath;
	}
	public void setFileCstdyPath(String fileCstdyPath) {
		this.fileCstdyPath = fileCstdyPath;
	}
	
	public void printData() {
		logger.debug("fileOrgnNm: {}", this.fileOrgnNm);
		logger.debug("fileExtension: {}", this.fileExtension);
		logger.debug("fileId: {}", this.fileId);
		logger.debug("fileSize: {}", this.fileSize);
		logger.debug("fileUploadPath: {}", this.fileUploadPath);
		logger.debug("fileClsfCd: {}", this.fileClsfCd);
		logger.debug("fileCstdyPath: {}", this.fileCstdyPath);
	}
}
