package com.ktnet.fta.details.group.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.fta.details.group.mapper.DetailsGroupMapper;

@Service("detailsGroupService")
@Transactional
public class DetailsGroupService {

    @Autowired
    private DetailsGroupMapper detailsGroupMapper;

    public Long create(Map<String, Object> map) {
        Map<String, Object> groupMap = null;
        Long groupId = null;

        if (map.get("beforeGroupId") != null) {
            // 이미 그룹 ID 가 있는 경우, 해당 객체 확인
            groupMap = this.searchDetailsGroup((Long) map.get("beforeGroupId"));
            groupId = (Long) map.get("beforeGroupId");
            Boolean locked = groupMap.get("lockAt") != null && (Integer) groupMap.get("lockAt") == 1 ? true : false;

            if (locked) {
                // 발급으로 인해 잠금 처리 된 경우, 신규 그룹 정보 생성
                groupId = this.saveDetailsGroup(map);
            }
        } else {
            groupId = this.saveDetailsGroup(map);
        }

        return groupId;
    }

    public Map<String, Object> searchDetailsGroup(Long id) {
        return detailsGroupMapper.selectDetailsGroup(id);
    }

    public Long saveDetailsGroup(Map<String, Object> map) {
        return detailsGroupMapper.insertDetailsGroup(map);
    }

}
