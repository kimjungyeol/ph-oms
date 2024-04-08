package com.ktnet.fta.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
     * move login page.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
	 * <pre>
	 *      Date          Person in Charge                  Comment
	 *  ============  =======================    ===========================
	 *  2024. 03. 18.      Jung Yeol KIM                initial version
	 * </pre>
     */
    @GetMapping("/login")
    public String login(HttpServletRequest req) throws Exception {
    	logger.info("login page move.");
    	
        return "member/login";
    }
    
    @GetMapping("/join")
    public String join(HttpServletRequest req, Model model) throws Exception {
    	logger.info(">>> jion<<<");
    	return "member/join";
    }
}