package com.ktnet.core.config.security;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ktnet.common.dto.SessionUserDto;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Resource
	private SessionUserDto sessionUser;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	logger.debug("2.CustomAuthenticationProvider");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // AuthenticationFilter에서 생성된 토큰으로부터 ID, PW를 조회
        String loginId = token.getName();
        String userPassword = (String) token.getCredentials();
        
        UserDetails securityUserDetails = null;
        
        try {
        	
        	// Spring security - UserDetailsService를 통해 DB에서 username으로 사용자 조회
        	securityUserDetails = (UserDetails) userDetailsService.loadUserByUsername(loginId);
        	
        } catch(Exception e) {
        }
        
        // TODO : Login 처리시 수정.
        //if (securityUserDetails.getUsername().equals("empty")) {
    	//	throw new UsernameNotFoundException("User name Not found!!@!");
    	//}
        
        // 대소문자를 구분하는 matches() 메서드로 db와 사용자가 제출한 비밀번호를 비교
        //if (!bCryptPasswordEncoder().matches(userPassword, securityUserDetails.getPassword())) {
        //	throw new BadCredentialsException(securityUserDetails.getUsername() + "Invalid password");
        //}
        
        sessionUser.setUserId("999999");
        sessionUser.setUserNm("testLoginUser");
        
        // 인증이 성공하면 인증된 사용자의 정보와 권한을 담은 새로운 UsernamePasswordAuthenticationToken을 반환한다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityUserDetails, userPassword, securityUserDetails.getAuthorities());
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}