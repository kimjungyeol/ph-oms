package com.ktnet.fta.details.material.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsMaterialMapper {

    public int deleteDetailsMaterial(Map<String, Object> map);

    public Long countDetailsMaterial(Map<String, Object> map);

    public int insertDetailsMaterial(Map<String, Object> map);

}
