package com.ktnet.fta.details.item.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsItemMapper {

    public int deleteDetailsItem(Map<String, Object> map);

    public int updateForIssueItem(Map<String, Object> map);

    public int updateForBom(Map<String, Object> map);

}
