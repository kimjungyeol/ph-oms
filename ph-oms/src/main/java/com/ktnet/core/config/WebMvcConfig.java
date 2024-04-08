package com.ktnet.core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktnet.core.config.xss.HTMLCharacterEscapes;
import com.ktnet.core.interceptor.HttpInterceptor;
import com.ktnet.core.resolver.MapArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private MapArgumentResolver mapArgumentResolver;
	
	@Autowired
	private HttpInterceptor httpInterceptor;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	    resolvers.add(this.mapArgumentResolver);
	} 
	  
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(httpInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**", "/images/**", "/js/**", "/error");
	}
	
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365); 
	}
	
	@Bean
	protected MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        //XSS append.
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
