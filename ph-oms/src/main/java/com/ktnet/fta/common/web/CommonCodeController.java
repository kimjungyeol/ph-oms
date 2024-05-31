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
import com.ktnet.common.dto.SessionUserDto;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.service.CommonCodeService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommonCodeController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "commonCodeService")
    private CommonCodeService commonCodeService;
	
	@Resource
	private SessionUserDto sessionUser;
    
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
    @PostMapping("/common/code/searchList")
    public ResponseEntity<ResultResponseDto> commonCodeSearchList(HttpServletRequest req, ParamMap pMap) throws Exception {
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
    @PostMapping("/common/code/searchOne")
    public ResponseEntity<ResultResponseDto> commonCodeSearchOne(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	
    	Map<String, Object> rtMap = commonCodeService.searchCodeOne(pMap.getMap());
    	
    	return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }
    
    /**
     * Search grid common code info.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
     * <pre>
     *      Date          Person in Charge                  Comment
     *  ============  =======================    ===========================
     *  2024. 04. 29.      Jung Yeol KIM                initial version
     * </pre>
     */
    @PostMapping("/common/code/grid/search")
    public ResponseEntity<ResultResponseDto> commonCodeGridSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
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
    	if("CM001".equals(paramMap.get("grpCd")+"")) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("text", "Yes11");
    		map.put("value", "Y");
    		rtList.add(map);
    		
    		map = new HashMap<String, Object>();
    		map.put("text", "No11");
    		map.put("value", "N");
    		rtList.add(map);
    	}
    	
    	return new ResponseEntity<>(ResultResponse(rtList), HttpStatus.OK);
    }
}