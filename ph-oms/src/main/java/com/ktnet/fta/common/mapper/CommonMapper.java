package com.ktnet.fta.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
 
    public int insertErrorMessage(Map<String, Object> map);
}