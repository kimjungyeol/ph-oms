package com.ktnet.fta.sample.simualtion.psr;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RVCPsr {
    Logger logger = LoggerFactory.getLogger(getClass());

    public boolean judgment(Map<String, Object> judgmentData) {
        boolean result = false;
        logger.debug("RVC PSR Judgment");

        return result;
    }
}
