package com.ktnet.core.config;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktnet.core.config.security.LoginFailureHandler;
import com.ktnet.core.config.security.LoginSuccessHandler;
import com.ktnet.core.config.security.LogoutSuccessHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
	
	@Autowired
	@Qualifier("loginSuccessHandler")
	LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	@Qualifier("loginFailureHandler")
	LoginFailureHandler loginFailureHandler;
	
	@Autowired
	@Qualifier("logoutSuccessHandler")
	LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	http.cors(corsConfigurer -> corsConfigurer
    			.configurationSource(corsConfigurationSource()))
    		.csrf((csrfConfig) -> csrfConfig
				.disable())
        	.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new MvcRequestMatcher(introspector, "/**")).permitAll()
        		.anyRequest()
                .authenticated())
	        .formLogin(login -> login
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login-process").permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password"))
	        .logout((logout) -> logout
        		.logoutSuccessHandler(logoutSuccessHandler))
			.exceptionHandling((exceptionConfig) -> exceptionConfig
				.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)); // 401 403 관련 예외처리
        
        return http.build();
    }
    
    @Bean
    protected WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) -> {
            web.ignoring()
               .requestMatchers(
                  PathRequest.toStaticResources().atCommonLocations()
               );
        };
    }
    
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

  	@Getter
    public class ErrorResponse {
		HttpStatus status;
        String message;
        public ErrorResponse(HttpStatus status, String message) {
        	this.status = status;
        	this.message = message;
        }
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}