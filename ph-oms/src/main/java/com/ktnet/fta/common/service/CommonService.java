package com.ktnet.fta.common.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.fta.common.mapper.CommonMapper;

@Service("commonService")
public class CommonService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private CommonMapper commonMapper;
    
    @Transactional
    public int saveErrorMessage(Map<String, Object> map) throws Exception {
    	
    	//TODO : table 생성 및 데이터 저장.
    	return 1;
    	//return commonMapper.insertErrorMessage( map ); 
    }
    
    
}