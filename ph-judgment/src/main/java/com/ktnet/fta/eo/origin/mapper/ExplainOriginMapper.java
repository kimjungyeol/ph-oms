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

    public int insertExplainOriginReceipt(Map<String, Object> map);

    public int updateExplainOriginMaterialForMainPurchase(Map<String, Object> map);

    public int updateExplainOriginMaterialByPurchase(Map<String, Object> map);

    public int updateExplainOriginMaterialByDoReceipt(Map<String, Object> map);

    public int updateExplainOriginByPurchase(Map<String, Object> map);

    public int updateExplainOriginByMaterial(Map<String, Object> map);

    public int updateExplainOriginByJudgment(Map<String, Object> map);

}
