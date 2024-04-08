package com.ktnet.fta.common.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.service.CommonService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommonController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "commonService")
    private CommonService commonService;
    
	/**
     * Search common code info.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
	 * <pre>
	 *      Date          Person in Charge                  Comment
	 *  ============  =======================    ===========================
	 *  2024. 03. 13.      Jung Yeol KIM                initial version
	 * </pre>
     */
    @PostMapping("/common/code/list/search")
    public @ResponseBody Map<String, Object> commonCodeListSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	
    	Map<String, Object> rtMap = new HashMap<String, Object>();
    	
    	commonService.searchCodeOne(pMap.getMap());
    	
        return rtMap;
    }
    
    /**
     * Search common code info.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
     * <pre>
     *      Date          Person in Charge                  Comment
     *  ============  =======================    ===========================
     *  2024. 03. 13.      Jung Yeol KIM                initial version
     * </pre>
     */
    @PostMapping("/common/code/one/search")
    public @ResponseBody Map<String, Object> commonCodeOneSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	
    	Map<String, Object> rtMap = new HashMap<String, Object>();
    	
    	commonService.searchCodeOne(pMap.getMap());
    	
    	return rtMap;
    }
}