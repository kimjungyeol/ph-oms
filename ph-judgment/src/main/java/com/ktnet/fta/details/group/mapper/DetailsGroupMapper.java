package com.ktnet.fta.details.group.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsGroupMapper {

    public Map<String, Object> selectDetailsGroup(Long id);

    public Long insertDetailsGroup(Map<String, Object> map);

}
