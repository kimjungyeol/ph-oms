package com.ktnet.common.analyzer.locale;

public interface BaseAnalyzer {

  public String analyze(String word);

  public String analyze(String word, String partOfSpeechTag);

}
