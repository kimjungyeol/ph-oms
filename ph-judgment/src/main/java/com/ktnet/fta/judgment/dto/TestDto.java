package com.ktnet.fta.judgment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestDto {

    private String value = "111";

    private String testValue = "222";

    @Builder
    public TestDto(String value, String testValue) {
        this.value = value;
        this.testValue = testValue;
    }
}
