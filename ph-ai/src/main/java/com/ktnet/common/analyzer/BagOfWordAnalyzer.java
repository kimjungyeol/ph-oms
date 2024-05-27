package com.ktnet.common.analyzer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktnet.common.analyzer.locale.BaseAnalyzer;
import com.ktnet.common.analyzer.locale.ENGNlpAnalyzer;
import com.ktnet.common.analyzer.locale.KORNlpAnalyzer;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.langdetect.Language;

@Slf4j
public class BagOfWordAnalyzer {
	
	static Logger logger = LoggerFactory.getLogger(BagOfWordAnalyzer.class);

	private static LanguageAnalyzer languageAnalyzer;

	private static ENGNlpAnalyzer engNlpAnalyzer;
	private static KORNlpAnalyzer korNlpAnalyzer;

	static {
	    try {
	        init();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    public static void init() throws IOException {
        if (languageAnalyzer == null) {
            languageAnalyzer = new LanguageAnalyzer("models/opennlp");
        }
        if (engNlpAnalyzer == null) {
            engNlpAnalyzer = new ENGNlpAnalyzer("models/opennlp");
        }
        if (korNlpAnalyzer == null) {
            korNlpAnalyzer = new KORNlpAnalyzer(DEFAULT_MODEL.LIGHT);;
        }
    }
    
    private static BaseAnalyzer getAnalyzer(final String word) {
        Language lang = languageAnalyzer.predict(word);
        logger.debug("> [" + word + "] lang is " + lang.getLang());
        
        // 한국어 형태소 분석기
        if ("kor".equals(lang.getLang())) {
          return BagOfWordAnalyzer.korNlpAnalyzer;
        }
        
        // 기본은 영어 형태소 분석기
        return BagOfWordAnalyzer.korNlpAnalyzer;
    }
    
    public synchronized static String analyze(final String word) {
        return analyze(word, null);
    }
    
    public synchronized static String analyze(final String word, final String partOfSpeechTag) {
        if (StringUtils.isBlank(word)) {
          return null;
        }
        BaseAnalyzer analyzer = getAnalyzer(word);
        String result = analyzer.analyze(word, partOfSpeechTag);
        // log.debug("> [" + word + "] analyze result is " + result);
        return result != null ? result.trim() : null;
    }
}
