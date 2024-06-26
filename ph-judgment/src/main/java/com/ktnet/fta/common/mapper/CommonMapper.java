package com.ktnet.fta.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
 
    public List<Map<String, Object>> selectCodeList(Map<String, Object> map);
    
    public Map<String, Object> selectCodeOne(Map<String, Object> map);
    
    public int insertErrorMessage(Map<String, Object> map);
}