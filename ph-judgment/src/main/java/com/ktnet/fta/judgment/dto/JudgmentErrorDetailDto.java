package com.ktnet.fta.judgment.dto;

import lombok.ToString;

@ToString
public class JudgmentErrorDetailDto {

    private Long id;

    private Long groupId;

    private Long judgmentId;

    private String errorType;

    private String contents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getJudgmentId() {
        return judgmentId;
    }

    public void setJudgmentId(Long judgmentId) {
        this.judgmentId = judgmentId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}