package com.ktnet.fta.judgment.dto;

public class TestDto {

    private String value = "111";

    private String testValue = "222";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public TestDto() {
    }

    public TestDto(String value, String testValue) {
        this.value = value;
        this.testValue = testValue;
    }
}
