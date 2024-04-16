package com.ktnet.core.resolver;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktnet.common.util.FileUtil;
import com.ktnet.core.map.ParamMap;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class MapArgumentResolver implements HandlerMethodArgumentResolver {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    String chkContentType = "application/x-www-form-urlencoded";
    String chkMultipartType = "multipart/form-data";

    @Autowired
    private FileUtil fileUtil;

    @Override
    public Object resolveArgument(MethodParameter prameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        ParamMap collector = new ParamMap();

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        logger.debug("==================== MapArgumentResolver ====================");

        setParameter(request, collector);
        
        String contentType = (String) request.getContentType();
        
        //CALL POST REQUEST.
        if ("post".equalsIgnoreCase(request.getMethod().toLowerCase())) {
        	if (contentType.indexOf(chkContentType) > -1) {
        		// AJAX  REQUEST DATA.
        		setParameterPostMethod(request, collector);
        	} else if (contentType.indexOf(chkMultipartType) > -1) {
        		// MULTIPART UPLOAD FILE REQUEST DATA.
        		fileUtil.uploadFileConfig(request, collector);
        	}
        }
        
        //set session info.
        setSessionInfo(request, collector);

        logger.debug("contentType \t: {}", contentType);
        logger.debug("collector.getMap() \t: {}", collector.getMap().toString());

        return collector;
    }

    private void setParameter(HttpServletRequest request, ParamMap collector) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
        Enumeration<?> enumeration = request.getParameterNames();
        
        String key = null;
        String[] values = null;
        while (enumeration.hasMoreElements()) {
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            
            if (values != null) {
                if (values.length > 1) {
                	collector.put(key, values[0]);
                } else {
                	
                	String value = values[0]+"";
                	if (value.indexOf("[{") > -1) {
                		@SuppressWarnings("rawtypes")
						//Map[] mapData = mapper.readValue(values[0], Map[].class);
                		List mapData = mapper.readValue(values[0], List.class);
                		collector.put(key, mapData);
                		
                	} else if (value.indexOf("{") > -1) {
            			@SuppressWarnings("unchecked")
            			Map<String, Object> mapData = mapper.readValue(value, Map.class);  //use saveForm - multipart/form-data
            			collector.put(key, mapData);
                	} else {
                		collector.put(key, value);
                	}
                }
            }
        }
    }

    private void setParameterPostMethod(HttpServletRequest request, ParamMap collector) throws Exception {
        Set<String> set = collector.keySet();
        if (set.size() > 0) {
            String postParams = collector.keySet().iterator().next();
            ObjectMapper mapper = new ObjectMapper();

            @SuppressWarnings("unchecked")
            Map<String, Object> mapData = mapper.readValue(postParams, Map.class);
            collector.clear();
            collector.putAll(mapData);
        }
    }
    
    private void setSessionInfo(HttpServletRequest request, ParamMap collector) throws Exception {
    	collector.put("loginUserId", "testUser");
    	
    	//TODO: set session login user object.
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ParamMap.class.isAssignableFrom(parameter.getParameterType());
    }

}