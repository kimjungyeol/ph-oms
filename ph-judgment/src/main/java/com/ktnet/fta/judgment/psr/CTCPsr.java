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
        // TODO Auto-generated method stub
        return false;
    }
}
