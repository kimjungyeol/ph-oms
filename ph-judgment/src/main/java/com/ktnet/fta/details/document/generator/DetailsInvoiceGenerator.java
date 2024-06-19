package com.ktnet.fta.details.document.generator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ktnet.fta.details.bom.service.DetailsBomService;
import com.ktnet.fta.details.document.service.DetailsInvoiceService;
import com.ktnet.fta.details.group.service.DetailsGroupService;
import com.ktnet.fta.details.material.service.DetailsMaterialService;
import com.ktnet.fta.details.purchase.service.DetailsPurchaseService;
import com.ktnet.fta.eo.origin.service.ExplainOriginService;
import com.ktnet.fta.judgment.constant.CertificateOriginStatus;
import com.ktnet.fta.judgment.service.JudgmentService;

import jakarta.annotation.Resource;

@Component
public class DetailsInvoiceGenerator {

    @Resource(name = "detailsInvoiceService")
    private DetailsInvoiceService detailsInvoiceService;

    @Resource(name = "detailsGroupService")
    private DetailsGroupService detailsGroupService;

    @Resource(name = "detailsBomService")
    private DetailsBomService detailsBomService;

    @Resource(name = "detailsMaterialService")
    private DetailsMaterialService detailsMaterialService;

    @Resource(name = "detailsPurchaseService")
    private DetailsPurchaseService detailsPurchaseService;

    @Resource(name = "explainOriginService")
    private ExplainOriginService explainOriginService;

    @Resource(name = "judgmentService")
    private JudgmentService judgmentService;

    public void generate(Map<String, Object> map) {
        // 문서 기존 group id 가져오기
        Long beforeGroupId = detailsInvoiceService.searchGroupId(map);

        // 파라미터 생성
        map.put("beforeGroupId", beforeGroupId);

        // group id check 및 생성
        Long groupId = detailsGroupService.create(map);
        map.put("groupId", groupId);

        // 문서에 group id 반영
        detailsInvoiceService.updateGroupId(map);

        // invoice id 가져오기 (detailsInvoiceService.updateStatus 실행 시 키값)
        Long inveId = detailsInvoiceService.searchInvoiceId(map);
        map.put("inveId", inveId);

        try {
            // 명세 정보 생성
            this.generateDetails(map);
            // 상태 수정
            map.put("sttus", CertificateOriginStatus.JUDGMENT);
            detailsInvoiceService.updateStatus(map);
        } catch (Exception e) {
            // 상태 수정
            map.put("sttus", CertificateOriginStatus.ERROR);
            e.printStackTrace();
        }
    }

    private void generateDetails(Map<String, Object> map) {
        // 품목 명세 생성
        detailsInvoiceService.insertDetailsItem(map);

        // BOM 명세 생성
        detailsBomService.insertDetailsBom(map);

        // 자재명세 생성
        detailsMaterialService.insertDetailsMaterial(map);

        // 구매명세 생성
        detailsPurchaseService.insertDetailsPurchase(map);

        // 소명서 생성
        this.generateEo(map);
    }

    private void generateEo(Map<String, Object> map) {
        // 소명서 생성
        detailsInvoiceService.insertExplainOrigin(map);

        // 소명서 자재 내역 생성
        explainOriginService.insertExplainOriginMaterial(map);

        // 원산지 판정
        judgmentService.judgmentExecute(map);

        // 판정 결과 반영
        explainOriginService.updateExplainOriginByJudgment(map);

    }
}
