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
    
    private final String DEFAULT_LANG = "EN";
    
    private String defaultLang(String lang) {
    	if (StringUtil.isEmpty(lang)) {
    		lang = DEFAULT_LANG;
    	}
    	return lang;
    }

    /**
     * word inquiry ( single )
     */
    @GetMapping("/word")
    public ResponseEntity<ResultResponseDto> getWord(HttpServletRequest req, ParamMap pMap) throws Exception {
        logger.info( ">> getWord <<" );
        Map<String, Object> map = pMap.getMap();
        String code = map.get("code")+"";
        String lang = map.get("lang")+"";
        
        Map<String, Object> resultMap = i18nService.searchWord(code, defaultLang(lang));
        
        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    /**
     * word inquiry ( multiple )
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/word/list")
    public ResponseEntity<ResultResponseDto> getWordList(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info(">> getWordList <<");
    	Map<String, Object> map = pMap.getMap();
    	Map<String, Object> i18n = (Map<String, Object>)map.get("i18n");
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	String lang = (String)map.get("lang");
    	for (String key : i18n.keySet()) {
    		Map<String, Object> term = i18nService.searchWord(key, defaultLang(lang));
    		
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
        
        Map<String, Object> resultMap = i18nService.searchMessage(code, defaultLang(lang));
        
        return new ResponseEntity<>(ResultResponse(resultMap), HttpStatus.OK);
    }

    /**
     * Default Message List inquiry
     */
    @PostMapping("/default/word/list")
    public ResponseEntity<ResultResponseDto> getDefaultWordList(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info( ">> getMessageList <<" );
    	Map<String, Object> map = pMap.getMap();
        String bascMsgFlag = map.get("bascMsgFlag")+"";
        String lang = map.get("lang")+"";

        List<Map<String, Object>> rtnList = i18nService.searchDefaultWordList(bascMsgFlag, defaultLang(lang));
        
        return new ResponseEntity<>(ResultResponse(rtnList), HttpStatus.OK);
    }
    
    /**
     * Default Message List inquiry
     */
    @PostMapping("/default/message/list")
    public ResponseEntity<ResultResponseDto> getDefaultMessageList(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info( ">> getMessageList <<" );
    	Map<String, Object> map = pMap.getMap();
    	String lang = map.get("lang")+"";
    	
    	List<Map<String, Object>> rtnList = i18nService.searchDefaultMessageList(defaultLang(lang));
    	
    	return new ResponseEntity<>(ResultResponse(rtnList), HttpStatus.OK);
    }

}