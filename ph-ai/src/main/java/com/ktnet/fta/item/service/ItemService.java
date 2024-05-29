package com.ktnet.fta.item.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.fta.item.mapper.ItemMapper;

@Service("itemService")
public class ItemService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ItemMapper itemMapper;
    
    public List<Map<String, Object>> searchHsCodeList(Map<String, Object> map) {
        return itemMapper.selectHsCodeList(map);
    }
    
}