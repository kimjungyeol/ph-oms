package com.ktnet.fta.eo.origin.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExplainOriginMapper {

    public int deleteExplainOrigin(Map<String, Object> map);

    public int deleteExplainOriginMaterial(Map<String, Object> map);

    public Long countExplainOriginMaterial(Map<String, Object> map);

    public int insertExplainOriginMaterial(Map<String, Object> map);

    public Long countExplainOriginReceiptByCo(Map<String, Object> map);

    public int insertExplainOriginReceiptByCo(Map<String, Object> map);

    public Long countExplainOriginReceipt(Map<String, Object> map);

}
