package com.ktnet.fta.common.web;

import java.util.HashMap;
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
import com.ktnet.fta.common.service.CommonService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommonController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "commonService")
    private CommonService commonService;
	
	@Resource
	private SessionUserDto sessionUser;
    
    /**
     * get session user info.
     * @param req
     * @param pMap
     * @return
     * @throws Exception
     */
    @PostMapping("/common/session/user")
    public ResponseEntity<ResultResponseDto> commonUserinfoSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info("commonSearch");
    	
    	Map<String, Object> rtMap = new HashMap<String, Object>();
    	
    	rtMap.put("userInfo", sessionUser.getMap());
    	
    	return new ResponseEntity<>(ResultResponse(rtMap), HttpStatus.OK);
    }
    
}