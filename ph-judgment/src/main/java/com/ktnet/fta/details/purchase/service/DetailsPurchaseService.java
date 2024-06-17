package com.ktnet.fta.details.purchase.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.item.mapper.DetailsItemMapper;
import com.ktnet.fta.details.purchase.mapper.DetailsPurchaseMapper;

@Service("detailsPurchaseService")
public class DetailsPurchaseService {

    @Autowired
    private DetailsPurchaseMapper detailsPurchaseMapper;

    @Autowired
    private DetailsItemMapper detailsItemMapper;

    public Long insertDetailsPurchase(Map<String, Object> map) {
        // 초기화
        detailsPurchaseMapper.deleteDetailsPurchase(map);

        Long totalCount = 0L;

        /* TODO : CMPNY_DTLS_ITEM 조회 시 HS CODE 관련 컬럼 확인 필요 */
        List<Map<String, Object>> details = detailsItemMapper.selectDetailsItem(map);

        for (Map<String, Object> item : details) {
            totalCount += this.insert(item);
        }

        return totalCount;
    }

    private Long insert(Map<String, Object> item) {
        String key = "CMPNY_DTLS_PUCHAS";

        Long count = null;
        Long startId = null;
        int loopCount = 0;
        Long totalCount = 0L;

        while (loopCount < 2) {
            // 업데이트 카운트
            count = detailsPurchaseMapper.countDetailsPurchase(item);

            totalCount += count;
            loopCount++;

            // 카운트 없는 경우 종료
            if (count == 0) {
                break;
            }

            // 시퀀스 업데이트
            /* TODO : 시퀀스 확인 */
            // startId = sequenceGenerator.getSequence(key, count);

            // 구매 맵핑
            // detailsPurchaseMapper.insert(companyId, detailsId, startId);
            detailsPurchaseMapper.insertDetailsPurchase(item);
        }

        return totalCount;
    }

}
