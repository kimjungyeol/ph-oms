package com.ktnet.fta.judgment.psr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ktnet.fta.judgment.dto.JudgmentDto;

@Component
public class ConditionPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    public boolean judgment(Long companyId, JudgmentDto judgment) {
        // TODO Auto-generated method stub
        return false;
    }

}
