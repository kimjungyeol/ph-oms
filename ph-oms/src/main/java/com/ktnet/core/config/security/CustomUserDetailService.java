package com.ktnet.core.config.security;

import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ktnet.fta.common.service.MemberService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    
    @Resource(name = "memberService")
    private MemberService memberService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Map<String, Object> userInfo = null;
    	UserDetails user = null; 
    			
    	String user_name = "empty";
    	String user_password = "empty";
    	
    	try {
			userInfo = memberService.searchUserByEmail(username);
		
			if (userInfo != null) {
				user_name = userInfo.get("username")+"";
				user_password = userInfo.get("password")+"";
			}
			
			//.roles(userInfo.get("role")+"")
			
		} catch (NullPointerException e) {
		} catch (Exception e) {}
    	
    	user = User.builder().username(user_name).password(user_password).build();
    	
        return user;
        
    }
}