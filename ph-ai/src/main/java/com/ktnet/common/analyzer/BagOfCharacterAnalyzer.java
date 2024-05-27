package com.ktnet.common.analyzer;

import org.apache.commons.lang3.StringUtils;

public class BagOfCharacterAnalyzer {

  private final static String REMOVE_CHAR = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

  public synchronized static String analyze(final String word) {
    if (StringUtils.isBlank(word)) {
      return null;
    }

    StringBuilder characters = new StringBuilder();

    // 팰드값 캐릭터 분석
    characters.setLength(0); // 초기화 시킨다음
    if (StringUtils.isEmpty(word)) {
      return characters.toString().trim();
    }
    String value = word.replaceAll(REMOVE_CHAR, "").replaceAll(" ", "").toUpperCase();
    for (int i = 0; i < value.length(); i++) {
      characters.append(value.charAt(i)).append(" ");
    }
    return characters.toString().trim();
  }

}
