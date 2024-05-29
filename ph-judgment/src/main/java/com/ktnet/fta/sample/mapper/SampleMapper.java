package com.ktnet.fta.sample.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {
 
    public List<Map<String, Object>> selectSampleList( Map<String, Object> map );
    public Map<String, Object> selectSample( Map<String, Object> map );
}