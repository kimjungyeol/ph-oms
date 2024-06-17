package com.ktnet.fta.details.material.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.details.material.mapper.DetailsMaterialMapper;

@Service("detailsMaterialService")
public class DetailsMaterialService {

    @Autowired
    private DetailsMaterialMapper detailsMaterialMapper;

    public Long insertDetailsMaterial(Map<String, Object> map) {
        // 초기화
        detailsMaterialMapper.deleteDetailsMaterial(map);

        // 업데이트 카운트
        Long count = detailsMaterialMapper.countDetailsMaterial(map);
        // 카운트 없는 경우 종료
        if (count == 0) {
            return count;
        }

        // 시퀀스 업데이트
        /* TODO : 시퀀스 생성해야함 */
        // String key = "CMPNY_DTLS_MTRIL";
        // Long startId = sequenceGenerator.getSequence(key, count);

        // 데이터 처리
        // detailsMaterialMapper.insert(companyId, params, startId);
        detailsMaterialMapper.insertDetailsMaterial(map);

        return count;
    }
}
