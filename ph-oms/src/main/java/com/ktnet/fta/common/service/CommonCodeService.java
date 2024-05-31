package com.ktnet.fta.common.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.fta.common.mapper.CommonCodeMapper;

@Service("commonCodeService")
public class CommonCodeService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private CommonCodeMapper commonCodeMapper;
    
    public List<Map<String, Object>> searchCodeList(Map<String, Object> map) throws Exception {
        return commonCodeMapper.selectCodeList(map);
    }
    
    public Map<String, Object> searchCodeOne(Map<String, Object> map) throws Exception {
    	return commonCodeMapper.selectCodeOne(map);
    }
    
}