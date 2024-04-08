package com.ktnet.fta.common.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
 
    public int insertLoginInfo(Map<String, Object> map);
    
    public Map<String, Object> selectUserByEmail(String email);
}