package com.ktnet.fta.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.service.CommonService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommonController extends BasicController {

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
    public ResponseEntity<ResultResponseDto> commonCodeListSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	Map<String, Object> paramMap = pMap.getMap();
    	
    	List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
    	
    	//commonService.searchCodeOne(pMap.getMap());
    	
    	//test용
    	if("CM000".equals(paramMap.get("grpCd")+"")) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("text", "Yes");
    		map.put("value", "Y");
    		rtList.add(map);
    		
    		map = new HashMap<String, Object>();
    		map.put("text", "No");
    		map.put("value", "N");
    		rtList.add(map);
    	}
    	
    	return new ResponseEntity<>(ResultResponse(rtList), HttpStatus.OK);
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
    public ResponseEntity<ResultResponseDto> commonCodeOneSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	
    	Map<String, Object> paramMap = pMap.getMap();
    	Map<String, Object> rtMap = new HashMap<String, Object>();
    	
    	//commonService.searchCodeOne(pMap.getMap());
    	
    	//test용
    	if("CM000".equals(paramMap.get("grpCd")+"")) {
    		rtMap.put("Y", "Yes");
    		rtMap.put("N", "No");
    	}
    	
    	return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }
    
}