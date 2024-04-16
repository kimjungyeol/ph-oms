package com.ktnet.fta.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
 
	public Map<String, Object> selectFileInfo(Map<String, Object> paramMap) throws Exception;
	public List<Map<String, Object>> selectFileInfoList(Map<String, Object> paramMap) throws Exception;
	public int selectAttachFileSn(String paramMap);
	
    public void insertUploadFileInfo(Map<String, Object> paramMap) throws Exception;
    public void deleteUploadFileInfo(Map<String, Object> paramMap) throws Exception;
    public void deleteAllFileInfo(Map<String, Object> paramMap) throws Exception;
}