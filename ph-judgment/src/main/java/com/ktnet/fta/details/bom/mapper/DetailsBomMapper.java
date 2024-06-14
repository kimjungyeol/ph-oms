package com.ktnet.fta.details.bom.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsBomMapper {

    public int deleteDetailsBom(Map<String, Object> map);

    public Long countForRoot(Map<String, Object> map);

    public int insertForRoot(Map<String, Object> map);

}
