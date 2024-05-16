package com.ktnet.fta.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktnet.common.util.StringUtil;
import com.ktnet.fta.common.mapper.I18nMapper;

@Service("i18nService")
public class I18nService {

    @Autowired
    private I18nMapper i18nMapper;
    
    private final String DEFAULT_LANG = "EN";
    
    private String defaultLang(String lang) {
    	if (StringUtil.isEmpty(lang)) {
    		lang = DEFAULT_LANG;
    	}
    	return lang;
    }

    /**
     * Word inquiry
     */
    public Map<String, Object> searchWord(String code, String lang) {
        return i18nMapper.selectWord(code, defaultLang(lang));
    }

    /**
     * Messages inquiry
     */
    public Map<String, Object> searchMessage(String code, String lang) {
        return i18nMapper.selectMessage(code, defaultLang(lang));
    }
    
    /**
     * Default word list inquiry
     */
    public List<Map<String, Object>> searchDefaultWordList(String bascMsgFlag, String lang) {
        List<Map<String, Object>> msgList = i18nMapper.selectDefaultWordList(defaultLang(lang));
        return msgList;
    }
    
    /**
     * Default messages list inquiry
     */
    public List<Map<String, Object>> searchDefaultMessageList(String bascMsgFlag, String lang) {
    	List<Map<String, Object>> msgList = i18nMapper.selectDefaultMessageList(defaultLang(lang));
    	return msgList;
    }
    
    /**
     * Word inquiry
     * "@Cacheable" 사용시 key 값을 model, vo, map 사용시에는 "@Override" hashCode(), equals( Object obj )를 선언 해줘야한다
     *   >> Override 미선언시 Cache에서 처리하지 않고 계속 조회를 한다.
     */
    //@Cacheable( value = "i18nWordCache", condition = "T(com.ktnet.common.util.CommonUtil).isCacheable(#cacheableStatus, 'i18nWordCache')" )
//    public Map<String, String> searchWord( String code, String lang, String cacheableStatus ) {
//        Map<String, String> term = i18nMapper.selectWord( code, lang );
//        if( term == null ) {
//            term = new HashMap<String, String>();
//            term.put( "code", code );
//            term.put( "name", "" );
//        }
//
//        return term;
//    }

    /**
     * Messages inquiry
     * "@Cacheable" 사용시 key 값을 model, vo, map 사용시에는 "@Override" hashCode(), equals( Object obj )를 선언 해줘야한다
     *   >> Override 미선언시 Cache에서 처리하지 않고 계속 조회를 한다.
     */
    //@Cacheable( value = "i18nMsgCache", condition = "T(com.ktnet.common.util.CommonUtil).isCacheable(#cacheableStatus, 'i18nMsgCache')" )
//    public Map<String, String> searchMessage( String code, String lang, String cacheableStatus ) {
//        Map<String, String> msg = i18nMapper.selectMessage( code, lang );
//        return msg;
//    }

    

}