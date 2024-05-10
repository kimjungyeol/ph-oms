package com.ktnet.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

	static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * Map으로 requestParam 세팅.
     * @param map
     * @param req
     */
    public static HashMap<String, Object> getRequestParam( HttpServletRequest req ) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Enumeration<?> params = req.getParameterNames();
        while ( params.hasMoreElements() ) {
            String name = (String)params.nextElement();
            String value = req.getParameter( name ) == null ? "" : req.getParameter( name )+"";
            map.put( name, value );
        }

        return map;
    }

    /**
     * Map으로 request Map info 세팅.
     * @param map
     * @param req
     */
    public static void getRequestMapParam( Map<String, Object> reqMap, Map<String, Object> map ) {
        Set<String> set = reqMap.keySet();
        Iterator<String> keys = set.iterator();

        while( keys.hasNext() ) {
            String key = keys.next();
            map.put( key, reqMap.get( key ) );
        }
    }

    /**
     * Save access network information.
     */
    public static void setNetworkInfo(HttpServletRequest req, Map<String, Object> info) throws Exception {
        info.put( "remoteHost", "" + req.getRemoteHost() ); // Access Pc

        String realIp = req.getHeader("x-real-ip") == null ? "" : req.getHeader("x-real-ip")+"";

        if( StringUtil.isNotEmpty(realIp) ) {
            info.put("ipAddr", realIp);
        } else {

            String ipAddr = req.getLocalAddr();
            if( "0:0:0:0:0:0:0:1".equals( ipAddr )) {
                ipAddr = "127.0.0.1";
            }

            info.put("ipAddr", ipAddr);
        }
    }

    /**
     * Get ip for different environments.
     *
     * @param request
     * @return
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static int getRandomNumber(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max-min)+1)+min;

        return randomNum;
    }

    public static List<Map<String, Object>> cloneListMap(List<Map<String, Object>> list) {
        List<Map<String, Object>> listClone = new ArrayList<>();
        Map<String, Object> mapClone = new HashMap<String, Object>();

        for (Map<String, Object> f : list) {
            mapClone = new HashMap<String, Object>();
            mapClone.putAll(f);

            listClone.add(mapClone);
        }

        return listClone;
    }
    
    /**
     * common.properties
     * 
     * @param propKey
     * @return
     */
    public static String getCommonProperty(String propKey) {
        Properties prop = new Properties();
        InputStream is = null;
        try {
    		is = CommonUtil.class.getResourceAsStream("/environment.properties");
			prop.load(is);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if( is != null) {
				try {
					if (is != null) is.close();
				} catch (IOException e1) {
					logger.error("", e1);
				}
			}
		}

        return prop.getProperty(propKey);

    }
    
    
    /**
     * IS_CACHEABLE
     * 
     * @param resource
     * @return boolean
     */
    public static boolean isCacheable(Map<String, Object> param, String cacheAlias) {
    	if(StringUtil.isEmpty(cacheAlias)) { return false; }
    	
    	String cacheableStatus = param.get("cacheableStatus")+"";
    	if(StringUtil.isEmpty(cacheableStatus)) {
    		return false;
    	}
    	boolean isEnable = false;
    	
    	StringTokenizer aliasToken = new StringTokenizer(cacheableStatus, ",");
    	while(aliasToken.hasMoreTokens()) {
        	String[] statusToken = StringUtil.split(aliasToken.nextToken().trim(), ":");
        	String t0 = statusToken[0]+"".replaceAll(" ", "");
        	String t1 = statusToken[1]+"".replaceAll(" ", "");
        	
    		if (cacheAlias.equals(t0)) {
    			if ("S".equals(t1.toUpperCase())) {
    				isEnable = true;
    			}
    			break;
    		}
    	}

    	return isEnable;
    }
}