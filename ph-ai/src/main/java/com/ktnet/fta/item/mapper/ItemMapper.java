package com.ktnet.fta.item.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
 
    public List<Map<String, Object>> selectHsCodeList( Map<String, Object> map );
}