package com.ktnet.fta.sample.service;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.common.util.ServiceUtil;

@Service("sampleBoardService")
public class SampleBoardService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * grid 저장.
     * 
     * map
     * 	  data
     * 		createdRows
     * 		updatedRows
     * 		deletedRows
     * 
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional
    public int saveBoardSample(Map<String, Object> map) throws Exception {
        int rtnCnt = 0;

        logger.debug(map.toString());
        
        ArrayList<Map<String, Object>> insertList = ServiceUtil.getGridInsertData(map);
        ArrayList<Map<String, Object>> updateList = ServiceUtil.getGridUpdateData(map);
        ArrayList<Map<String, Object>> deleteList = ServiceUtil.getGridDeleteData(map);
        
        logger.debug("insertList count, {}", insertList.size());
        logger.debug("updateList count, {}", updateList.size());
        logger.debug("deleteList count, {}", deleteList.size());
        
        //insert
        //update
        //delete

        return rtnCnt;
    }
}