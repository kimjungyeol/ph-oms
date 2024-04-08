package com.ktnet.core.config.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component(value = "logoutSuccessHandler")
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
    	logger.info("==================== LogoutSuccessHandler ====================");
        
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        logger.debug("Logout User = {} ", username);
         
        // TODO : process logics with customer
         
        super.onLogoutSuccess(request, response, authentication);
    }  
}