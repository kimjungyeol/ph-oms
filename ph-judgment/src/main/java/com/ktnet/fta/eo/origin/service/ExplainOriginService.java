package com.ktnet.fta.eo.origin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.eo.origin.mapper.ExplainOriginMapper;

@Service("explainOriginService")
public class ExplainOriginService {

    @Autowired
    private ExplainOriginMapper explainOriginMapper;

    public Long insertExplainOriginMaterial(Map<String, Object> map) {
        // 초기화
        explainOriginMapper.deleteExplainOriginMaterial(map);

        /*** 자재 명세서 ***/
        // 업데이트 카운트
        Long count = explainOriginMapper.countExplainOriginMaterial(map);

        // 카운트 없는 경우 종료
        if (count == 0) {
            return count;
        }

        // 시퀀스 업데이트
        /* TODO : 시퀀스 생성 */
        // String key = "FTA_EO_MTRIL";
        // Long startId = sequenceGenerator.getSequence(key, count);

        // 데이터 처리
        explainOriginMapper.insertExplainOriginMaterial(map);

        /*** 수취 증명 명세서 ***/
        // 업데이트 카운트
        count = explainOriginMapper.countExplainOriginReceiptByCo(map);

        if (count > 0) {
            // 카운트 있는 경우 처리
            // 시퀀스 업데이트
            /* TODO : 시퀀스 생성 */
            // key = "FTA_EO_RECEPT_DO";
            // startId = sequenceGenerator.getSequence(key, count);

            // 데이터 처리
            // explainOriginReceiptMapper.insertByCo(companyId, params, startId);
            explainOriginMapper.insertExplainOriginReceiptByCo(map);
        }

        return count;

        /*** 수취 확인 명세서 ***/
        // 업데이트 카운트
        // count = explainOriginMapper.countExplainOriginReceipt(map);
    }

}
