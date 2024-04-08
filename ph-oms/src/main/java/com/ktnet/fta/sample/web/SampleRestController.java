package com.ktnet.fta.sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SampleRestController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("null")
    @GetMapping("/sample/rest/errtest")
    public String errtest(HttpServletRequest req, Model model) throws Exception {
    	logger.info("errtest");
    	
    	//error
		String aa = null;
		logger.debug(aa.toString());
    	
    	return "sample/sampleView";
    }
}