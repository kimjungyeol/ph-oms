package com.ktnet.fta.details.document.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.document.mapper.DetailsInvoiceMapper;

@Service("detailsInvoiceService")
public class DetailsInvoiceService {

    @Autowired
    private DetailsInvoiceMapper detailsInvoiceMapper;

    public Long searchGroupId(Map<String, Object> map) {
        Long groupId = detailsInvoiceMapper.selectGroupId(map);
        return groupId;
    }

    public int updateGroupId(Map<String, Object> map) {
        return detailsInvoiceMapper.updateGroupId(map);

    }

    public Long searchInvoiceId(Map<String, Object> map) {
        return detailsInvoiceMapper.selectInvoiceId(map);
    }

    // DetailsInvoiceService.insertDetailsItem - 품목 명세 생성
    // DetailsBomGenerator.insert - BOM 명세 생성 (공통)
    // DetailsMaterialGenerator.insert - 자재명세 생성 (공통)
    // DetailsPurchaseGenerator.insert - 구매명세 생성 (공통)
    // DetailsInvoiceService.insertExplainOrigin - 소명서 생성
    // ExplainOriginMaterialGenerator.insert - 소명서 자재 내역 생성 (공통)
    // JudgmentExecutor.execute - 원산지 판정
    // ExplainOriginGenerator.update - 판정결과 반영 (공통)

    // DetailsDoService.insertDetailsItem
    //
}
