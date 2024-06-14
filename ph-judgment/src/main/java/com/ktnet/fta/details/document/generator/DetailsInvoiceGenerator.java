package com.ktnet.fta.details.document.generator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ktnet.fta.details.bom.service.DetailsBomService;
import com.ktnet.fta.details.document.service.DetailsInvoiceService;
import com.ktnet.fta.details.group.service.DetailsGroupService;
import com.ktnet.fta.judgment.constant.CertificateOriginStatus;

import jakarta.annotation.Resource;

@Component
public class DetailsInvoiceGenerator {

    @Resource(name = "detailsInvoiceService")
    private DetailsInvoiceService detailsInvoiceService;

    @Resource(name = "detailsGroupService")
    private DetailsGroupService detailsGroupService;

    @Resource(name = "detailsBomService")
    private DetailsBomService detailsBomService;

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

        // invoice id 가져오기
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
    }
}
