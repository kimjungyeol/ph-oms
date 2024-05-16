package com.ktnet.fta.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface I18nMapper {

	public Map<String, Object> selectWord(@Param("code") String code, @Param("lang") String lang);
    public Map<String, Object> selectMessage(@Param("code") String code, @Param("lang") String lang);
    public List<Map<String, Object>> selectDefaultWordList(@Param("lang") String lang);
    public List<Map<String, Object>> selectDefaultMessageList(@Param("lang") String lang);
}