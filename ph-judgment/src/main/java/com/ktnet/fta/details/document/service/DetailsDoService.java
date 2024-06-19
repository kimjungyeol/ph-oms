package com.ktnet.fta.details.document.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.document.mapper.DetailsDoMapper;
import com.ktnet.fta.details.item.mapper.DetailsItemMapper;
import com.ktnet.fta.eo.origin.mapper.ExplainOriginMapper;

@Service("detailsDoService")
public class DetailsDoService {

    @Autowired
    private DetailsDoMapper detailsDoMapper;

    @Autowired
    private DetailsItemMapper detailsItemMapper;

    @Autowired
    private ExplainOriginMapper explainOriginMapper;

    public Long searchGroupId(Map<String, Object> map) {
        Long groupId = detailsDoMapper.selectGroupId(map);
        return groupId;
    }

    public int updateGroupId(Map<String, Object> map) {
        return detailsDoMapper.updateGroupId(map);
    }

    public int updateStatus(Map<String, Object> map) {
        return detailsDoMapper.updateStatus(map);
    }

    public Long insertDetailsItem(Map<String, Object> map) {
        // 기 데이터 삭제
        detailsItemMapper.deleteDetailsItem(map);

        // 매출 정보 연결
        detailsDoMapper.updateFromSales(map);

        // 업데이트 카운트
        Long count = detailsDoMapper.countDetailsItem(map);

        if (count == 0) {
            return count;
        }

        // 시퀀스 업데이트
        /* TODO : 1. 시퀀스 Generator 생성해야함 */
        // String key = "CMPNY_DTLS_ITEM";
        // Long startId = sequenceGenerator.getSequence(key, count);

        // 데이터 처리
        detailsDoMapper.insertDetailsItem(map);

        // 발급품명 반영
        detailsItemMapper.updateForIssueItem(map);

        // BOM 정보 반영
        detailsItemMapper.updateForBom(map);

        return count;
    }

    public void insertExplainOrigin(Map<String, Object> map) {
        // 기 데이터 삭제
        explainOriginMapper.deleteExplainOrigin(map);

        // 업데이트 카운트
        Long count = detailsDoMapper.countExplainOrigin(map);

        // 시퀀스 업데이트
        /* TODO : 시퀀스 생성 */
        // String key = "FTA_EO";
        // Long startId = sequenceGenerator.getSequence(key, count);

        // 데이터 처리
        detailsDoMapper.insertExplainOrigin(map);

    }

}
