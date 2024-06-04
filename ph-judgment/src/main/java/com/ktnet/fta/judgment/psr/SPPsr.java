package com.ktnet.fta.judgment.psr;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;
import com.ktnet.fta.judgment.mapper.JudgmentMapper;

@Component
public class SPPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JudgmentMapper judgmentMapper;

    public boolean judgment(Long companyId, JudgmentDto judgment) {
        logger.debug(">>>> SPPsr : " + judgment);

        // 품목분류 누락의 경우
        if (judgment.getClassificationId() == null) {
            judgment.addError("judgment.sp.check", "[[ 품목분류 누락 ]]" + "<br>품목분류 등록 후, 선택기준을 설정해야 합니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 관리정보 > 품목분류");
            return false;
        }
        // 품목분류 > 선택기준을 설정 필요
//        ClassificationPsr userChoisePsr = classificationPsrRepository.findByPsrId(companyId,
//                judgment.getClassificationId(), judgment.getPsrId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("companyId", companyId);
        params.put("classificationId", judgment.getClassificationId());
        params.put("psrId", judgment.getPsrId());

        Map<String, Object> userChoisePsrMap = judgmentMapper.selectUserChoisePsr(params);

        if (userChoisePsrMap == null) {
            judgment.addError("judgment.sp.check", "[[ 품목분류 선택기준 미설정 ]]" + "<br>품목분류 > 선택기준을 설정해야 합니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 관리정보 > 품목분류 > 선택기준");
            return false;
        }
        // 품목분류 > 선택기준 불충족 설정
        if (userChoisePsrMap.get("originAt") == null) {
            judgment.addError("judgment.sp.check", "[[ 품목분류 선택기준 미설정 ]]" + "<br>품목분류 선택기준 미설정으로 불충족 처리되었습니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 관리정보 > 품목분류 > 선택기준");
            return false;
        }
        if (Boolean.valueOf(userChoisePsrMap.get("originAt").toString()) != true) {
            judgment.addError("judgment.sp.check", "[[ 품목분류 선택기준 불충족 ]]" + "<br>품목분류 선택기준 미설정으로 불충족 처리되었습니다." + "<br>"
                    + "<br> ** 관련 메뉴 " + "<br>- 관리정보 > 품목분류 > 선택기준");
            return false;
        }

        judgment.setSpSufficient(true);

        return judgment.getSpSufficient();
    }

}
