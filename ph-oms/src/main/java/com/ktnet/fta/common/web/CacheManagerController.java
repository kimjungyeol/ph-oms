package com.ktnet.fta.common.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktnet.common.dto.ResultResponseDto;

/***********************************************************************
 * Program ID           : CacheController
 * Program name         : cache management
 * Creation date        : 2024.05.17
 * Created by           : JungYeol Kim
 * Description          : cache management
 * Modification Description
 ***********************************************************************/

@Controller
@RequestMapping("/cache")
public class CacheManagerController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * i18n word Cache Clear
     * @return void
     * @throws Exception
     */
    @GetMapping("/clear/i18n/word")
    @CacheEvict( value="i18nWord", allEntries=true )
    public ResponseEntity<ResultResponseDto> i18nWordCacheClear() throws Exception {
        logger.info( " >> i18nWordCacheClear << " );
        
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("message", "i18nWord Cache Clear");
        
        return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }
    
    /**
     * i18n message Cache Clear
     * @return void
     * @throws Exception
     */
    @GetMapping("/clear/i18n/message")
    @CacheEvict( value="i18nMessage", allEntries=true )
    public ResponseEntity<ResultResponseDto> i18nMessageCacheClear() throws Exception {
    	logger.info( " >> i18nMessageCacheClear << " );
    	
    	Map<String, Object> rtMap = new HashMap<String, Object>();
    	rtMap.put("message", "i18nMessage Cache Clear");
    	
    	return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }

}