package com.ktnet.fta.judgment.psr;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;

@Component
public class CTCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    public boolean judgment(Map<String, Object> judgmentData) {
        boolean result = false;
        int hsMatchCount = 0;

        logger.debug("CTC PSR Judgment");

        if ("CC".equals(judgmentData.get("psrStandard"))) {
            hsMatchCount = (int) judgmentData.get("ccMatchCount");
        }

        if ("CTH".equals(judgmentData.get("psrStandard"))) {
            hsMatchCount = (int) judgmentData.get("cthMatchCount");
        }

        if ("CTSH".equals(judgmentData.get("psrStandard"))) {
            hsMatchCount = (int) judgmentData.get("ctshMatchCount");
        }

        if (hsMatchCount == 0) {
            result = true;
        }

        return result;
    }

    public boolean judgment(Long companyId, JudgmentDto judgment) {
        // log.debug("세번변경 유형 : " + judgment.getCtcStandard());
        if (judgmentCTC(judgment)) {
            return judgment.getCtcSufficient();
        }

        return judgmentDeminimis(judgment);
    }

    private boolean judgmentCTC(JudgmentDto judgment) {
        int hsMatchCount = 0;

        if ("CC".equals(judgment.getCtcStandard())) {
            // 2자리 변경기준
            hsMatchCount = judgment.getCcMatchCount();

        } else if ("CTH".equals(judgment.getCtcStandard())) {
            // 4자리 변경기준
            hsMatchCount = judgment.getCthMatchCount();

        } else if ("CTSH".equals(judgment.getCtcStandard())) {
            // 6자리 변경기준
            hsMatchCount = judgment.getCtshMatchCount();
        }

        if (hsMatchCount == 0) {
            judgment.setCtcSufficient(Boolean.TRUE);
            return judgment.getCtcSufficient();
        }

        // log.debug("세번변경 불충족 : " + hsMatchCount + " 건의 자재 HS 일치");
        return judgment.getCtcSufficient();
    }

    private boolean judgmentDeminimis(JudgmentDto judgment) {
        // log.debug("미소기준 유형 : " + judgment.getDeminimisStandard());

        if (judgment.getHscodeErrorCount() > 0) {
            judgment.addError("judgment.deminimis.amount",
                    "[[ 미소기준 미충족 ]]" + "<br>미소기준 판정을 위해서는 모든 자재가 품목분류 되어야 합니다." + "<br>" + "<br> ** 아래 사항을 확인해 주세요."
                            + "<br>- 투입자재중 HS코드가 등록되지 않은 자재의 HS코드를 등록해 주세요." + "<br>" + "<br> ** 관련 메뉴 "
                            + "<br>- 원산지분석 > 분류대상 분석");
            return false;
        }

        if ("C".equals(judgment.getCtcStandard())) {
            // 금액 또는 중량 기준
            return judgmentDeminimisOfTypeC(judgment);

        }
        if ("B".equals(judgment.getCtcStandard())) {
            // 금액 및 CTSH 기준
            return judgmentDeminimisOfTypeB(judgment);
        }
        if ("W".equals(judgment.getCtcStandard())) {
            // 중량 기준
            return judgmentDeminimisOfTypeW(judgment);
        }

        // 금액 기준
        return judgmentDeminimisOfTypeA(judgment);
    }


    

    

    private boolean judgmentDeminimisOfTypeC(JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private boolean judgmentDeminimisOfTypeB(JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private boolean judgmentDeminimisOfTypeW(JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private boolean judgmentDeminimisOfTypeA(JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }
}
