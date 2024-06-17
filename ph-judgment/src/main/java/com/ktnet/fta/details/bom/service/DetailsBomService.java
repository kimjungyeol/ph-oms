package com.ktnet.fta.details.bom.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.bom.mapper.DetailsBomMapper;

@Service("detailsBomService")
public class DetailsBomService {

    @Autowired
    private DetailsBomMapper detailsBomMapper;

    public Long insertDetailsBom(Map<String, Object> map) {
        // 초기화
        this.delete(map);

        Long totalCount = 0L;

        // 업데이트 카운트
        Long count = detailsBomMapper.countForRoot(map);
        totalCount += count;

        // 시퀀스 업데이트
        /* TODO : 시퀀스 생성해야함 */
        // String key = "CMPNY_DTLS_BOM";
        // Long startId = sequenceGenerator.getSequence(key, count);

        // ROOT LEVEL BOM 생성
        detailsBomMapper.insertForRoot(map);
        Long bomLevel = 1L;
        while (bomLevel < 20) {
            // 업데이트 카운트
            count = detailsBomMapper.countForChild(map);
            totalCount += count;

            if (count == 0) {
                // 하위 BOM 없으면 종료
                break;
            }
            /* TODO : 시퀀스 생성해야함 */
            // startId = sequenceGenerator.getSequence(key, count);
            // 최대 20 레벨
            // detailsBomMapper.insertForChild(companyId, params, bomLevel++, startId);
            detailsBomMapper.insertForChild(map);
        }

        // 자재 여부 설정
        detailsBomMapper.updateForMaterialAt(map);

        return totalCount;
    }

    private int delete(Map<String, Object> map) {
        return detailsBomMapper.deleteDetailsBom(map);
    }

}
