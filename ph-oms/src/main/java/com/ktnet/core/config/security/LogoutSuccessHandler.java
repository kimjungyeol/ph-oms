package com.ktnet.core.config.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.ktnet.common.dto.SessionUserDto;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component(value = "logoutSuccessHandler")
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SessionUserDto sessionUser;
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
    	logger.info("==================== LogoutSuccessHandler ====================");
        
    	logger.debug("Logout User = {} ", sessionUser.getUserId());
    	
    	HttpSession session = request.getSession(false);
    	if(session != null) {
    		session.invalidate();
    	}
    	
        super.onLogoutSuccess(request, response, authentication);
    }  
}