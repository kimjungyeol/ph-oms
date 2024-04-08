package com.ktnet.fta.common.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.fta.common.mapper.MemberMapper;

@Service("memberService")
public class MemberService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private MemberMapper memberMapper;
    
    public Map<String, Object> searchUserByEmail(String email) throws Exception {
    	return memberMapper.selectUserByEmail(email);
    }
    
    @Transactional
    public int saveLoginInfo(Map<String, Object> map) throws Exception {
    	
    	//TODO : table 생성 및 데이터 저장.
    	return 1;
    	//return memberMapper.insertLoginInfo( map ); 
    }
    
    
}