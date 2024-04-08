package com.ktnet.fta.sample.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sampleBoardService")
public class SampleBoardService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public int saveBoardSample(Map<String, Object> map) throws Exception {
        int rtnCnt = 0;

        logger.debug(map.toString());

        return rtnCnt;
    }
}