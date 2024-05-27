package com.ktnet.common.analyzer.locale;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.util.common.model.Pair;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Getter
@Slf4j
public class KORNlpAnalyzer implements BaseAnalyzer {

    // NNG 일반 명사 명사 체언
    // NNP 고유 명사 명사 체언
    // NNB 의존 명사 명사 체언
    // NR 수사 수사 체언
    // NP 대명사 대명사 체언
    // VV 동사 동사 용언
    // VA 형용사 형용사 용언
    // SL 외래어
    public static final String PART_OF_SPEECH_TAG = "NNG, NNP, XR, MM, MAG, MAJ, VV, VA, SL";
    
    private Komoran komoran;
    
    @Builder
    public KORNlpAnalyzer(DEFAULT_MODEL model) throws IOException {
        // DEFAULT_MODEL.LIGHT
        this.komoran = new Komoran(model);
    }
    
    @Override
    public String analyze(String word) {
        return this.analyze(word, "");
    }
    
    @Override
    public String analyze(String word, String partOfSpeechTag) {
        StringBuilder bagOfWord = new StringBuilder();
        
        String tag = PART_OF_SPEECH_TAG;
        if (StringUtils.isNotBlank(partOfSpeechTag)) {
            tag = tag + ", " + partOfSpeechTag;
        }
        
        // 형태소 분석
        KomoranResult result = komoran.analyze(word.toUpperCase().replaceAll("_", " "));
        for (Pair<String, String> token : result.getList()) {
            // 지정 품사만 처리
            if (!tag.contains(token.getSecond())) {
              continue;
            }
            // 필드 형태소 분석 문자열에 추가
            bagOfWord.append(token.getFirst()).append(" ");
        }
        return bagOfWord.toString().trim();
    }
}
