package com.ktnet.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil extends StringUtils {

    public static boolean isNotEmpty(String s) {
        if (s == null || "null".equals(s) || "".equals(s)) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }

        return true;
    }

    public static boolean isEmpty(String s) {
        if (s == null || "null".equals(s) || "".equals(s)) {
            return true;
        }
        if (s.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public static String getStringValue(Object s) {
        if (s == null) {
            return "";
        }

        String v = s+"";
        if (v.trim().length() == 0 || v.indexOf("null") > -1) {
            return "";
        }

        return v;
    }

    /**
     * get String of all Object.
     *
     * <pre>
     *  null =&gt; null;
     *  String =&gt; string.trim();
     *  Collection =&gt; delimited by &quot;;&quot; string
     *  Object -&gt; obj.toString();
     * </pre>
     *
     *
     * @param obj
     * @return
     */
    public static String getSafeString(Object obj) {
        return getSafeString(obj, null);
    }

    public static String getSafeString(Object obj, Object def) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return String.valueOf(obj).trim();
        } else if (obj instanceof Collections) {
            return StringUtils.collectionToDelimitedString((Collection<?>) obj, ";");
        } else {
            return obj.toString();
        }
    }
    public static String nvl(Object obj, Object def) {
        return StringUtil.getSafeString(ObjectUtils.isEmpty(obj) ? def : obj);
    }

    /**
     * ToStringBuilder 이용하여 객체 string 문자열 생성
     * @param obj
     * @return
     */
    public static String reflectionToString(Object obj) {
        return ToStringBuilder.reflectionToString(obj);
    }

    /**
     * CamelCase String to Underscore String
     */
    public static String camelToUnderscore(String s) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        if(s == null) {
            return "";
        }
        return s.replaceAll(regex, replacement).toUpperCase();
    }

    /**
     * substring a string by bytes
     * new String(str.getBytes(), 0, endBytes)
     * According to UTF-8, Hangul is counted as 3 bytes, and uppercase and lowercase letters of the alphabet, numbers, and spaces are counted as 1 byte.
     *
     * @param str
     * @param beginBytes
     * @param endBytes
     * @return
     */
    public static String substringByBytes(String str, int beginBytes, int endBytes) {
        if (str == null || str.length() == 0) {
            return "";
        }

        if (beginBytes < 0) {
            beginBytes = 0;
        }

        if (endBytes < 1) {
            return "";
        }

        int len = str.length();

        int beginIndex = -1;
        int endIndex = 0;

        int curBytes = 0;
        String ch = null;
        for (int i = 0; i < len; i++) {
            ch = str.substring(i, i + 1);
            curBytes += ch.getBytes().length;


            if (beginIndex == -1 && curBytes >= beginBytes) {
                beginIndex = i;
            }

            if (curBytes > endBytes) {
                break;
            } else {
                endIndex = i + 1;
            }
        }


        return str.substring(beginIndex, endIndex);
    }

    //Map To Json
    public static String convertMapToJson(Map<String, Object> map) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        return mapper.writeValueAsString(map);

    }

    //Json To Map
    public static Map<String, Object> convertJsonToMap(String jsonString) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        Map<String, Object> dataMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});

        return dataMap;
    }

    //Json To List
    public static List<Map<String, Object>> convertJsonToList(String jsonString) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        List<Map<String, Object>> dataList = mapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>(){});

        return dataList;
    }
}