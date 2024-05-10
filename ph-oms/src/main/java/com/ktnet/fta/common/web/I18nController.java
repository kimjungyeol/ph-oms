package com.ktnet.fta.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.common.util.StringUtil;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.service.I18nService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/i18n")
public class I18nController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
    @Resource(name = "i18nService")
    private I18nService i18nService;

    /**
     * word inquiry ( single )
     */
    @GetMapping("/word")
    public ResponseEntity<ResultResponseDto> getWord(HttpServletRequest req, ParamMap pMap) throws Exception {
        logger.info( ">> getWord <<" );
        Map<String, Object> map = pMap.getMap();
        String code = map.get("code")+"";
        String lang = map.get("lang")+"";
        
        Map<String, Object> resultMap = i18nService.searchWord(code, lang);
        
        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    /**
     * word inquiry ( multiple )
     */
    @PostMapping("/word/list")
    public ResponseEntity<ResultResponseDto> getWordList(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info(">> getWordList <<");
    	Map<String, Object> map = pMap.getMap();
    	
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String lang = (String)map.get( "lang" );
    
    	@SuppressWarnings("unchecked")
    	Map<String, Object> i18n = (Map<String, Object>)map.get("i18n");
    	
    	for (String key : i18n.keySet()) {
    		Map<String, Object> term = i18nService.searchWord(key, lang);
    		
    		String name = term.get("name")+"";
    		if (StringUtil.isEmpty(name)) {
    			name = i18n.get(key)+"";
    		}
    		
    		resultMap.put(key, name);
    	}

        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    /**
     * Message inquiry ( single )
     */
    @GetMapping("/message")
    public ResponseEntity<ResultResponseDto> getMessage(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info( ">> getMessage <<" );
    	Map<String, Object> map = pMap.getMap();
        String code = map.get("code")+"";
        String lang = map.get("lang")+"";
        
        Map<String, Object> resultMap = i18nService.searchMessage(code, lang);
        
        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    /**
     * Message List inquiry
     */
    @PostMapping("/message/list")
    public ResponseEntity<ResultResponseDto> getMessageList(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info( ">> getMessageList <<" );
    	Map<String, Object> map = pMap.getMap();
        String bascMsgFlag = map.get("bascMsgFlag")+"";
        String lang = map.get("lang")+"";

        List<Map<String, Object>> rtnList = i18nService.searchMessageList(bascMsgFlag, lang);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("msgList", rtnList);
        
        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

}