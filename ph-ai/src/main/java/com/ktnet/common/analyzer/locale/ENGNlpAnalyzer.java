package com.ktnet.common.analyzer.locale;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@NoArgsConstructor
@Slf4j
public class ENGNlpAnalyzer implements BaseAnalyzer {
	
	static Logger logger = LoggerFactory.getLogger(ENGNlpAnalyzer.class);

    public static final String PART_OF_SPEECH_TAG = "NN, JJ, NNS";
    private static final String STOP_WORDS = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
    
    private String modelPath;
    
    private TokenizerModel tokenizerModel;
    private TokenizerME tokenizer;
    
    private POSModel posModel;
    private POSTaggerME posTagger;
    
    private DictionaryLemmatizer lemmatizer;
    
    public ENGNlpAnalyzer(String modelPath) throws IOException {
        this.modelPath = modelPath;
        
        this.tokenizerModel =
            new TokenizerModel((new ClassPathResource(modelPath + "/en-token.bin")).getInputStream());
        this.tokenizer = new TokenizerME(tokenizerModel);
        
        this.posModel = new POSModel(
            (new ClassPathResource(modelPath + "/en-pos-perceptron.bin")).getInputStream());
        this.posTagger = new POSTaggerME(posModel);
        
        this.lemmatizer = new DictionaryLemmatizer(
            (new ClassPathResource(modelPath + "/en-lemmatizer.dict")).getInputStream());
    }
    
    @Override
    public String analyze(String word) {
        return this.analyze(word, "");
    }

    @Override
    public String analyze(String word, String partOfSpeechTag) {
        String tag = PART_OF_SPEECH_TAG;
        if (StringUtils.isNotBlank(partOfSpeechTag)) {
          tag = tag + ", " + partOfSpeechTag;
        }
        
        if (word == null || "".equals(word)) {
          return null;
        }
        // 불용어 제거 및 token 화
        word = word.toLowerCase().replaceAll(STOP_WORDS, "").trim();
        
        // 단어길이가 짧은 경우 나는 오류를 방지하기 위해
        if (word.length() < 21) {
          while (true) {
            word += " " + word;
            if (word.length() > 21) {
              break;
            }
          }
        }
        String tokens[] = tokenizer.tokenize(word);
        
        logger.debug(">> tokens is : " + tokens.length);
        
        // 품사정보 얻기
        String tags[] = posTagger.tag(tokens);
        // 어간분리 (setmming)
        String[] lemmas = lemmatizer.lemmatize(tokens, tags);
        
        StringBuilder bagOfWord = new StringBuilder();
        for (int index = 0; index < lemmas.length; index++) {
            // log.debug("> " + tokens[index] + " -> [" + tags[index] + "]" + lemmas[index]);
            if (tag.indexOf(tags[index]) > -1) {
                bagOfWord.append(lemmas[index]).append(" ");
            }
        }
        return bagOfWord.toString().trim();
    }

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public TokenizerModel getTokenizerModel() {
		return tokenizerModel;
	}

	public void setTokenizerModel(TokenizerModel tokenizerModel) {
		this.tokenizerModel = tokenizerModel;
	}

	public TokenizerME getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(TokenizerME tokenizer) {
		this.tokenizer = tokenizer;
	}

	public POSModel getPosModel() {
		return posModel;
	}

	public void setPosModel(POSModel posModel) {
		this.posModel = posModel;
	}

	public POSTaggerME getPosTagger() {
		return posTagger;
	}

	public void setPosTagger(POSTaggerME posTagger) {
		this.posTagger = posTagger;
	}

	public DictionaryLemmatizer getLemmatizer() {
		return lemmatizer;
	}

	public void setLemmatizer(DictionaryLemmatizer lemmatizer) {
		this.lemmatizer = lemmatizer;
	}

	public static String getPartOfSpeechTag() {
		return PART_OF_SPEECH_TAG;
	}

	public static String getStopWords() {
		return STOP_WORDS;
	}
}
