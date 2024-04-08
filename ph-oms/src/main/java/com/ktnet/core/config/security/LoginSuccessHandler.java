package com.ktnet.core.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component(value = "loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	logger.info("==================== onAuthenticationSuccess ====================");
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
    	
//    	response.sendRedirect("/sample/search");
    	
    	try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("status", 200);
            responseMap.put("message", "Success");
            
            String rtnRst = objectMapper.writeValueAsString(responseMap);
            response.getOutputStream().println(rtnRst);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}