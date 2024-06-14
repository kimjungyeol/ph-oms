package com.ktnet.fta.details.document.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.document.mapper.DetailsInvoiceMapper;
import com.ktnet.fta.details.item.mapper.DetailsItemMapper;

@Service("detailsInvoiceService")
public class DetailsInvoiceService {

    @Autowired
    private DetailsInvoiceMapper detailsInvoiceMapper;

    @Autowired
    private DetailsItemMapper detailsItemMapper;

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

    public int updateStatus(Map<String, Object> map) {
        return detailsInvoiceMapper.updateStatus(map);
    }

    public Long insertDetailsItem(Map<String, Object> map) {
        // 기 데이터 삭제
        detailsItemMapper.deleteDetailsItem(map);

        // 업데이트 카운트
        Long count = detailsInvoiceMapper.countDetailsItem(map);
        // 판정가능한 품목 없는 경우 오류 처리
        if (count == 0) {
            return count;
        }

        // 시퀀스 업데이트
        /* TODO : 1. 시퀀스 Generator 생성해야함 */

        // 데이터 처리
        detailsInvoiceMapper.insertDetailsItem(map);

        // 발급품명 반영
        detailsItemMapper.updateForIssueItem(map);

        // BOM 정보 반영
        detailsItemMapper.updateForBom(map);

        return count;
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
