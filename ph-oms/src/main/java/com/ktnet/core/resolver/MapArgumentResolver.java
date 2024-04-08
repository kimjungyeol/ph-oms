package com.ktnet.core.resolver;

import java.util.Enumeration;
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

    @Autowired
    private FileUtil fileUtil;

    @Override
    public Object resolveArgument(MethodParameter prameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        ParamMap collector = new ParamMap();

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        logger.debug("==================== MapArgumentResolver ====================");

        setParameter(request, collector);

        if ("post".equalsIgnoreCase(request.getMethod().toLowerCase())) {
            setParameterPostMethod(request, collector);
        }

        logger.debug("collector.getMap() \t:  " + collector.getMap().toString());

        return collector;
    }

    private void setParameter(HttpServletRequest request, ParamMap collector) {
        Enumeration<?> enumeration = request.getParameterNames();
        String key = null;
        String[] values = null;
        while (enumeration.hasMoreElements()) {
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            if (values != null) {
                collector.put(key, (values.length > 1) ? values : values[0]);
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

        // MULTIPART UPLOAD CONFIG.
        fileUtil.uploadFileConfig(request, collector);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ParamMap.class.isAssignableFrom(parameter.getParameterType());
    }

}