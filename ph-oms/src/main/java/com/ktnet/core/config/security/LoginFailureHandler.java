package com.ktnet.core.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component(value = "loginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
    	// 실패로직 핸들링
        exception.printStackTrace();
        writePrintErrorResponse(response, exception);
    }

    private void writePrintErrorResponse(HttpServletResponse response, AuthenticationException exception) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = new HashMap<>();

            String message = getExceptionMessage(exception);
            responseMap.put("status", 401);
            responseMap.put("message", message);
            
            String rtnRst = objectMapper.writeValueAsString(responseMap);
            response.getOutputStream().println(rtnRst);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getExceptionMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "Password mismatch.";
        } else if (exception instanceof UsernameNotFoundException) {
            return "Account not foound.";
        } else if (exception instanceof AccountExpiredException) {
            return "Account expired.";
        } else if (exception instanceof CredentialsExpiredException) {
            return "Password expired.";
        } else if (exception instanceof DisabledException) {
            return "Account disabled.";
        } else if (exception instanceof LockedException) {
            return "Account locked.";
        } else {
            return "Login error occurred.";
        }
    }

}