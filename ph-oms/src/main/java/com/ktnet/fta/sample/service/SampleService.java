package com.ktnet.fta.sample.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.fta.sample.mapper.SampleMapper;

@Service("sampleService")
public class SampleService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SampleMapper sampleMapper;
    
    public List<Map<String, Object>> searchSampleList(Map<String, Object> map) {
        List<Map<String, Object>> list = sampleMapper.selectSampleList(map);
        return list; 
    }
    
    public Map<String, Object> searchSample(Map<String, Object> map){
    	Map<String, Object> rtMap = sampleMapper.selectSample(map);
    	return rtMap;
    }
    
    
    @Transactional
    public int saveSample(Map<String, Object> map) throws Exception {
    	int rtnCnt = 0;
    	if ("I".equals(map.get("tran")+"")) {
    		rtnCnt = sampleMapper.insertSample( map );    		
    	} else if ("U".equals(map.get("tran")+"")) {
    		rtnCnt = sampleMapper.updateSample( map );
    	}
    	
    	return rtnCnt;
    }
    
    @SuppressWarnings("null")
	@Transactional
    public int transTest(Map<String, Object> map) throws Exception {
    	int rtnCnt = 0;
    	
    	rtnCnt = sampleMapper.updateSample(map);
    	
    	//error!!
    	String aa = null;
    	logger.debug(aa.toString());
    	
    	return rtnCnt;
    }
}