package com.ktnet.fta.details.purchase.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailsPurchaseMapper {

    public int deleteDetailsPurchase(Map<String, Object> map);

    public Long countDetailsPurchase(Map<String, Object> item);

    public int insertDetailsPurchase(Map<String, Object> item);

}
