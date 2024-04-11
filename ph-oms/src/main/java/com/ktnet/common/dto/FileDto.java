package com.ktnet.common.dto;

import java.util.List;

/**
 * [공통] File 관리 VO
 * 
 * @author : kjy
 * @fileName : FileDto
 * @since : 2024/04/11
 */
public class FileDto {
    private List<FileUploadDto> uploadList;
    private List<FileDeleteDto> deleteList;

    public FileDto(List<FileUploadDto> uploadList) {
        this.setUploadList(uploadList);
    }

    public FileDto(List<FileUploadDto> uploadList, List<FileDeleteDto> deleteList) {
        this.setUploadList(uploadList);
        this.setDeleteList(deleteList);
    }

	public List<FileUploadDto> getUploadList() {
		return uploadList;
	}

	public void setUploadList(List<FileUploadDto> uploadList) {
		this.uploadList = uploadList;
	}

	public List<FileDeleteDto> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<FileDeleteDto> deleteList) {
		this.deleteList = deleteList;
	}
}
